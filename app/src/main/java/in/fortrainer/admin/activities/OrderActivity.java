package in.fortrainer.admin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import in.fortrainer.admin.adapters.OrderAdpater;
import in.fortrainer.admin.models.Order;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class OrderActivity extends AppCompatActivity implements OrderAdpater.OnPageEndReachedLstener {

    public RecyclerView mRecyclerView;
    public List<Order> orders = null;
    OrderAdpater orderAdpater = null;
    public int loadedpage;
    public static int PER_PAGE = 10;
    private int totalEntries;
    List<Order> receivedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        getOrders();
    }

    public void getOrders() {
        Call<JsonObject> orderslistCall = RetrofitHelper.getRetrofitService(context).getOrderslist(loadedpage+1,PER_PAGE);
        orderslistCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    totalEntries = response.body().get("total_entries").getAsInt();
                    JsonArray jsonArray = response.body().get("app_orders").getAsJsonArray();
                    Type listType = new TypeToken<ArrayList<Order>>(){}.getType();
                    receivedOrder = new Gson().fromJson(jsonArray,listType);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                    orderAdpater = new OrderAdpater(receivedOrder,OrderActivity.this,"ORDER");
                    orderAdpater.SetOnPageEndReachedLstener(OrderActivity.this);
                    mRecyclerView.setAdapter(orderAdpater);
                    orderAdpater.notifyItemRangeInserted(receivedOrder.size() - receivedOrder.size(),receivedOrder.size());
                    loadedpage++;
                    if(receivedOrder.size() < totalEntries ){
                        addLoadingMoreAtEnd();

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addLoadingMoreAtEnd() {
        if(receivedOrder != null && receivedOrder.size()==0){
            orderAdpater = new OrderAdpater(receivedOrder,OrderActivity.this,"LOADING");
            mRecyclerView.setAdapter(orderAdpater);
            orderAdpater.notifyItemInserted(receivedOrder.size()-1);

            if(orderAdpater!=null){
                orderAdpater.notifyItemInserted(receivedOrder.size()-1);
            }
        }
    }

    private void removeLoadingFromEnd() {
        if(orderAdpater != null) {
            receivedOrder.remove(receivedOrder.size() - 1);
            orderAdpater.notifyItemRemoved(receivedOrder.size());
        }
    }


    @Override
    public void onPageEndReached() {
        getOrders();

    }
}
