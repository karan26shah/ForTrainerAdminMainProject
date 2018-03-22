package in.fortrainer.admin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.BannerAdapter;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;


public class BannerListActivity extends AppCompatActivity{
    List<Banner> banners;
    //RecyclerView bannerlist;
    BannerAdapter bannerAdapter;
    CommonRecyclerScreen crs;
    int appId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        if(getIntent().getIntExtra("APP_ID",0)!= 0){
            appId = getIntent().getIntExtra("APP_ID",0);
        }
        else{
            Log.d("BannerActivity","failed");
                    finish();
        }
        setScreen();

    }
    private void setScreen(){
        crs = CommonRecyclerScreen.setupWithActivity(this);
        bannerAdapter = new BannerAdapter(this,crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(bannerAdapter);
        listBannerList();
    }

    private void listBannerList()
    {
        crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
        Call<List<Banner>> call = RetrofitHelper.getRetrofitService(context).listBanner();
        call.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                banners = response.body();
                if (banners.size() == 0) {
                    CommonRecyclerItem commonRecyclerItem = new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK, "No banners yet", this);
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
    }
}
