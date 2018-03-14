package in.fortrainer.admin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class HomeActivity extends AppCompatActivity {
    RecyclerView appList;
    HomeAdpater homeAdpater;
    List<App> apps = new ArrayList();
    LinearLayout llApp;
    LinearLayout llApp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appList = findViewById(R.id.appList);
        //llApp = findViewById(R.id.ll_app);
        //llApp1 = findViewById(R.id.LL_appListHolder);

        getApps();
    }

    private void getApps() {
        Call<JsonObject> appListCall = RetrofitHelper.getRetrofitService(context).getApplist();
        appListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();

                    apps = new Gson().fromJson(jsonObject.getAsJsonArray("apps"), new TypeToken<List<App>>() {
                    }.getType());
                    setProductsToAdapter();
              //      if (apps.size() == 0)
                    {
                      //  llApp.setVisibility(View.VISIBLE);
                    //    llApp1.setVisibility(View.GONE);

                    }
                    //else{
                       // llApp1.setVisibility(View.VISIBLE);

                    }

                    // Show the products on the screen

                 //   String name = apps.get(0).getName();
                }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setProductsToAdapter() {
        appList.setLayoutManager(new LinearLayoutManager(this));
        homeAdpater = new HomeAdpater(this,apps);
        appList.setAdapter(homeAdpater);
        homeAdpater.notifyDataSetChanged();

    }
}
