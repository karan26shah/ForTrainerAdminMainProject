package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

public class AppUsersListActivity extends AppCompatActivity {
    public List<AppUser> AppUsers;
    AppUserAdapter appUserAdapter;
    public int loadedpage;
    public static int PER_PAGE = 10;
    //private int totalEntries;
    int appId;
    CommonRecyclerScreen crs;

    public static void onUserClicked(Context context, AppUser appUser) {
        Intent intent = new Intent(context,AppUserDetailsActivity.class);
        intent.putExtra("APP_ID",new Gson().toJson(appUser,AppUser.class));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_user1);
        if(getIntent().getIntExtra("APP_ID",0)!= 0){
            appId = getIntent().getIntExtra("APP_ID",0);
        }
        else{
            Toast.makeText(AppUsersListActivity.this,"FAIL",Toast.LENGTH_SHORT).show();
        }
        setScreen();
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
        Call<JsonObject> AppUserslistCall = RetrofitHelper.getRetrofitService(context).getAppUserslist(loadedpage+1,PER_PAGE);
        AppUserslistCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    AppUsers = new Gson().fromJson(jsonObject.getAsJsonArray("users"), new TypeToken<List<AppUser>>() {
                    }.getType());
                    //totalEntries = response.body().get("total_entries").getAsInt();
                    if (AppUsers.size() == 0)
                    {
                        CommonRecyclerItem commonRecyclerItem =new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK,"No data yet",this);
                        crs.recyclerItems.add(commonRecyclerItem);
                    }else {
                        crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.APP_USER_LIST, AppUsers,this));
                    }
                    appUserAdapter.notifyDataSetChanged();
                    crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);
                }else{
                    Toast.makeText(AppUsersListActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AppUsersListActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


