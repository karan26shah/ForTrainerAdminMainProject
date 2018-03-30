package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.AppUserAdapter;
import in.fortrainer.admin.models.AppUser;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

/**
 * Created by foram on 29/3/18.
 */

public class AppUserDetailsActivity extends AppCompatActivity {
    public List<AppUser> AppUsers;
    AppUserAdapter appUserAdapter;
    public int loadedpage;
    public static int PER_PAGE = 10;
    //private int totalEntries;
    int appId;
    CommonRecyclerScreen crs;
    AppUser appUser;


    public static void onUserClicked(Context context, AppUser appUser) {
        Intent intent = new Intent(context,AppUserDetailsActivity.class);
        intent.putExtra("APP_ID",new Gson().toJson(appUser,AppUser.class));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_user1);
      readIntent();
        setScreen();
    }

    private void readIntent() {

        appUser = new Gson().fromJson(getIntent().getStringExtra("APP_ID"),AppUser.class);

    }

    private void setScreen(){
        crs = CommonRecyclerScreen.setupWithActivity(this);
        appUserAdapter = new AppUserAdapter(this,crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(appUserAdapter);
        getAppUsers();
    }

    public void getAppUsers() {
        crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
        Call<AppUser> AppUserslistCall = RetrofitHelper.getRetrofitService(context).getAppUsersDetails(appUser.getUserId());
        AppUserslistCall.enqueue(new Callback<AppUser>() {
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                if (response.isSuccessful()) {

                    appUser = response.body();
                    //totalEntries = response.body().get("total_entries").getAsInt();
                   // if (appUser.is == 0)
                    //{
                   //     CommonRecyclerItem commonRecyclerItem =new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK,"No data yet",this);
                 //       crs.recyclerItems.add(commonRecyclerItem);
                    //}else {
                        crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.APP_USER, appUser,this));
                   // }
                    appUserAdapter.notifyDataSetChanged();
                    crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);
                }else{
                    Toast.makeText(AppUserDetailsActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {
                Toast.makeText(AppUserDetailsActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
