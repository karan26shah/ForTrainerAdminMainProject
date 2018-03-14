package in.fortrainer.admin.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.EventAdapter;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class EventActivity extends AppCompatActivity {
    RecyclerView eventlist;
    private TextView responseText;
    List<Event> appEvents;
    EventAdapter eventAdapter;
    int appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getIntExtra("APP_ID",0)!= 0){
            appId = getIntent().getIntExtra("APP_ID",0);
        }
        else{
            Toast.makeText(EventActivity.this,"FAIL",Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_event);
        eventlist = (RecyclerView) findViewById(R.id.eventlist);
        getProducts();
    }

    private void getProducts() {


        Call<JsonObject> eventListCall = RetrofitHelper.getRetrofitService(context).getEventlist(appId);
        eventListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();

                    appEvents = new Gson().fromJson(jsonObject.getAsJsonArray("app_events"), new TypeToken<List<Event>>() {
                    }.getType());
                    setProductsToAdapter();
                    // Show the products on the screen

                    String name = appEvents.get(0).getName();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EventActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setProductsToAdapter()
    {
        // Add products to a adapter
        // Add adapter to the recycler view
        // jsonlist.setHasFixedSize(true);
        eventlist.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(this,appEvents);
        eventlist.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();


    }
}
