package in.fortrainer.admin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.MainAdapter;
import in.fortrainer.admin.adapters.OrderAdpater;
import in.fortrainer.admin.models.Order;
import in.fortrainer.admin.models.User;
import in.fortrainer.admin.utilities.AdminHelper;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class UserActivity extends AppCompatActivity implements MainAdapter.OnPageEndReachedListener {

    CommonRecyclerScreen crs;
    public List<User> users;
    MainAdapter userAdapter;
    public int loadedpage;
    public static int PER_PAGE = 10;
    private int totalEntries;
    List<User> receivedUser;
    public static int userCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user1);

        setAdapter();
        setClickListener();
        getUsers();
    }

    private void setClickListener() {

    }

    private void setAdapter() {
        crs = CommonRecyclerScreen.setupWithActivity(this);
        userAdapter = new MainAdapter(context, crs.getRecyclerItems());
        userAdapter.setOnPageEndReachedListener(this);
        crs.resetPagination(userAdapter);
        crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
    }

    public void getUsers() {
        if (AdminHelper.isDataAdapterOn(this) && !crs.lockOnLoad && !crs.areAllEntriesFetched()) {
            crs.lockOnLoad();
            Call<JsonObject> userslistCall = RetrofitHelper.getRetrofitService(context).getuserslist(loadedpage + 1, PER_PAGE);
            userslistCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    crs.releaseLoadLock();
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        totalEntries = response.body().get("total_entries").getAsInt();
                        JsonArray jsonArray = response.body().get("users").getAsJsonArray();
                        Type listType = new TypeToken<ArrayList<User>>() {
                        }.getType();
                        receivedUser = new Gson().fromJson(jsonArray, listType);
                        userCount = userCount++;
                        crs.removeLoadingFromEnd();
                        crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.USER, receivedUser));
                        userAdapter.notifyItemRangeInserted(crs.recyclerItems.size() - receivedUser.size(), receivedUser.size());
                        crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);
                        crs.increaseLoadedPageValue();

                        if (userCount == totalEntries || receivedUser.isEmpty()) {
                            crs.allEntriesFetched = true;
                            userCount = 0;
                        } else {
                            crs.addLoadingMoreAtEnd();
                        }
                    } else {
                        crs.releaseLoadLock();
                        crs.setScreen(CommonRecyclerScreen.ScreenMode.RETRY);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    crs.releaseLoadLock();
                    crs.setScreen(CommonRecyclerScreen.ScreenMode.RETRY);
                    Toast.makeText(UserActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            crs.releaseLoadLock();
         //   crs.setScreen(CommonRecyclerScreen.ScreenMode.RETRY);
        }
    }

    @Override
    public void onPageEndReached() {
        getUsers();

    }

}
