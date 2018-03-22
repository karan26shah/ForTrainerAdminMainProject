package in.fortrainer.admin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.EventAdapter;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class EventActivity extends AppCompatActivity {

    List<Event> appEvents;
    EventAdapter eventAdapter;
    int appId;
    CommonRecyclerScreen crs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        if(getIntent().getIntExtra("APP_ID",0)!= 0){
            appId = getIntent().getIntExtra("APP_ID",0);
        }
        else{
            Log.d("EventActivity","failed");
            finish();
        }
        setScreen();
    }
    private void setScreen(){
        crs = CommonRecyclerScreen.setupWithActivity(this);
        eventAdapter = new EventAdapter(this,crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(eventAdapter);
        getEvents();
    }

    private void getEvents() {
        crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
        Call<JsonObject> eventListCall = RetrofitHelper.getRetrofitService(context).getEventlist();
        eventListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    appEvents = new Gson().fromJson(jsonObject.getAsJsonArray("app_events"), new TypeToken<List<Event>>() {
                    }.getType());

                    if (appEvents.size() == 0) {
                        CommonRecyclerItem commonRecyclerItem = new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK, "No data yet", this);
                        crs.recyclerItems.add(commonRecyclerItem);
                    } else {
                        crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.EVENTS, appEvents, this));
                    }
                    eventAdapter.notifyDataSetChanged();
                    crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);

                } else {
                    Toast.makeText(EventActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EventActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
