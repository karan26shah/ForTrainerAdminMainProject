package in.fortrainer.admin.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.EventAdapter;
import in.fortrainer.admin.adapters.HomeAdpater;
import in.fortrainer.admin.adapters.PostAdpater;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.AdminHelper;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class HomeActivity extends AppCompatActivity {
    RecyclerView appList;
    HomeAdpater homeAdpater;
    List<App> apps = new ArrayList();
    CommonRecyclerScreen crs;
    Button button;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //ActionBar ab = getSupportActionBar();
       // ab.setDisplayHomeAsUpEnabled(true);
        button = findViewById(R.id.bt_nointernet);
        linearLayout = findViewById(R.id.nointernet);
        setScreen();

    }
    private void setScreen(){
        crs = CommonRecyclerScreen.setupWithActivity(this);
        homeAdpater = new HomeAdpater(this,crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(homeAdpater);
        getApps();
    }

    private void getApps() {
        if (AdminHelper.isDataAdapterOn(context)) {
            crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
            Call<JsonObject> appListCall = RetrofitHelper.getRetrofitService(context).getApplist();
            appListCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        apps = new Gson().fromJson(jsonObject.getAsJsonArray("apps"), new TypeToken<List<App>>() {
                        }.getType());
                        if (apps.size() == 0) {
                            CommonRecyclerItem commonRecyclerItem = new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK, "No Apps yet", this);
                            crs.recyclerItems.add(commonRecyclerItem);
                        } else {
                            crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.APPS, apps, this));
                        }
                        homeAdpater.notifyDataSetChanged();
                        crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);
                    } else {
                        Toast.makeText(HomeActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            {
                Toast.makeText(HomeActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                linearLayout.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayout.setVisibility(View.GONE);
                        getApps();

                    }

                });

            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
