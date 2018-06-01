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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.OrderAdpater;
import in.fortrainer.admin.adapters.PostAdpater;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Order;
import in.fortrainer.admin.utilities.AdminHelper;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class OrderActivity extends AppCompatActivity {


    public List<Order> orders;
    OrderAdpater orderAdpater;
    public int loadedpage;
    public static int PER_PAGE = 10;
    //private int totalEntries;
    int appId;
    CommonRecyclerScreen crs;
    Button button;
    LinearLayout linearLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        button = findViewById(R.id.bt_nointernet);
        linearLayout = findViewById(R.id.nointernet);
        setScreen();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.back) {
            Intent intent = new Intent(OrderActivity.this,HomeActivity.class);
            startActivity(intent);
            }
        return super.onOptionsItemSelected(item);
    }
*/

    private void setScreen() {
        crs = CommonRecyclerScreen.setupWithActivity(this);
        orderAdpater = new OrderAdpater(this, crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(orderAdpater);
        getOrders();
    }

    public void getOrders() {
        if (AdminHelper.isDataAdapterOn(context)) {
            crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
            Call<JsonObject> orderslistCall = RetrofitHelper.getRetrofitService(context).getOrderslist(loadedpage + 1, PER_PAGE);
            orderslistCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        orders = new Gson().fromJson(jsonObject.getAsJsonArray("app_orders"), new TypeToken<List<Order>>() {
                        }.getType());
                        //totalEntries = response.body().get("total_entries").getAsInt();
                        if (orders.size() == 0) {
                            CommonRecyclerItem commonRecyclerItem = new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK, "No data yet", this);
                            crs.recyclerItems.add(commonRecyclerItem);
                        } else {
                            crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.ORDERS, orders, this));
                        }
                        orderAdpater.notifyDataSetChanged();
                        crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);
                    } else {
                        Toast.makeText(OrderActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(OrderActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(OrderActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.GONE);
                    getOrders();

                }

            });

        }
    }
}

