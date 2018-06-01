package in.fortrainer.admin.activities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.fortrainer.admin.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import in.fortrainer.admin.adapters.BannerAdapter;
import in.fortrainer.admin.adapters.PostAdpater;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.AdminHelper;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;


public class BannerActivity extends AppCompatActivity {
    List<Banner> banners;
    //RecyclerView bannerlist;
    BannerAdapter bannerAdapter;
    CommonRecyclerScreen crs;
    int appId;
    Button button;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        button = (Button) findViewById(R.id.bt_nointernet);
        linearLayout = (LinearLayout) findViewById(R.id.nointernet);
        if (getIntent().getIntExtra("APP_ID", 0) != 0) {
            appId = getIntent().getIntExtra("APP_ID", 0);
        } else {
            Log.d("BannerActivity", "failed");
            finish();
        }
        setScreen();

    }

    private void setScreen() {
        crs = CommonRecyclerScreen.setupWithActivity(this);
        bannerAdapter = new BannerAdapter(this, crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(bannerAdapter);
        listBannerList();
    }

    private void listBannerList() {
        if (AdminHelper.isDataAdapterOn(context)) {
            crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
            Call<List<Banner>> call = RetrofitHelper.getRetrofitService(context).listBanner();
            call.enqueue(new Callback<List<Banner>>() {
                @Override
                public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                    banners = response.body();
                    if (banners.size() == 0) {
                        CommonRecyclerItem commonRecyclerItem = new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK, "No posts yet", this);
                        crs.recyclerItems.add(commonRecyclerItem);
                    } else {
                        crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.BANNER, banners, this));
                    }
                    bannerAdapter.notifyDataSetChanged();
                    crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);
                }

                @Override
                public void onFailure(Call<List<Banner>> call, Throwable t) {
                    Log.d("error: ", "failed ");
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(BannerActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.GONE);
                    listBannerList();

                }

            });

        }
    }
}
