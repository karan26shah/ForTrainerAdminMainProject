package in.fortrainer.admin.activities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.EventAdapter;
import in.fortrainer.admin.adapters.PostAdpater;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.AdminHelper;
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
    CommonRecyclerScreen crs;
    Button button;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        button = findViewById(R.id.bt_nointernet);
        linearLayout = findViewById(R.id.nointernet);
        setScreen();
    }

    private void setScreen() {
        crs = CommonRecyclerScreen.setupWithActivity(this);
        eventAdapter = new EventAdapter(this, crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(eventAdapter);
        getEvents();
    }

    private void getEvents() {
        if (AdminHelper.isDataAdapterOn(context)) {
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
                            CommonRecyclerItem commonRecyclerItem = new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK, "No Events yet", this);
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
        else
        {
            Toast.makeText(EventActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.GONE);
                    getEvents();

                }

            });

        }

    }
}
