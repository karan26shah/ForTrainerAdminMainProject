package in.fortrainer.admin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class EventEditActivity extends AppCompatActivity {

    Event event;
    /**
     * Android Views
     **/
    EditText etName;
    EditText etVenue;
    Button btCalendar;
    Button btCalendar1;
    CalendarView calendar;
    CalendarView calendar1;
    EditText stTime;
    EditText edTime;
    EditText etPrice;
    Button submit;

    /**
     * Android Views
     **/


    public void init() {

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEventList();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        readIntent();
        bindViews();
        setEventValues();
        init();
        btCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.VISIBLE);
                calendar = (CalendarView) findViewById(R.id.calendar);
                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                        String date = year + "/" + month + 1 + "/" + dayOfMonth;
                        Log.d("EVENT_EDIT", "onSelectedDayChange: yyyy/mm/dd:" + date);
                        stTime.setText(date);
                    }
                });
            }
        });

        btCalendar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar1.setVisibility(View.VISIBLE);
                calendar1 = (CalendarView) findViewById(R.id.calendar1);
                calendar1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                        String date = year + "/" + month + "/" + dayOfMonth;
                        Log.d("EVENT_EDIT", "onSelectedDayChange: yyyy/mm/dd:" + date);
                        edTime.setText(date);

                    }
                });
            }
        });
    }


    private void updateEventList() {
        Call<JsonObject> eventListCall = RetrofitHelper.getRetrofitService(context).updateEventDetails(event.getId(),etName.getText().toString(), etVenue.getText().toString(), stTime.getText().toString(), edTime.getText().toString(),etPrice.getText().toString());
        eventListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(EventEditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    launchEventListActivity();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EventEditActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchEventListActivity(){
        Intent intent = new Intent(EventEditActivity.this, EventActivity.class);
        startActivity(intent);
        finish();
    }

    private void readIntent() {

        if (getIntent().getStringExtra("EVENT_DETAILS") == null) {
            Toast.makeText(this, "cant get event", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            event = new Gson().fromJson(getIntent().getStringExtra("EVENT_DETAILS"), Event.class);
        }
    }

    private void setEventValues() {

        etName.setText(event.getName());
        etVenue.setText(event.getAddress().getAddressLine1());
        if (event.getIsPaid()) {
            etPrice.setText(event.getPrice());
        } else {
            etPrice.setText(event.getPrice());
        }
        stTime.setText(event.getStartDatetime());
        edTime.setText(event.getEndDatetime());
    }


    /**
     * Binds XML views
     * Call this function after setContentView() in onCreate().
     **/
    private void bindViews() {
        etName = findViewById(R.id.et_name);
        etVenue = findViewById(R.id.et_venue);
        btCalendar = findViewById(R.id.bt_calendar);
        calendar = findViewById(R.id.calendar);
        btCalendar1 = findViewById(R.id.bt_calendar1);
        calendar1 = findViewById(R.id.calendar1);
        stTime = findViewById(R.id.et_sttime);
        edTime = findViewById(R.id.et_edtime);
        etPrice = findViewById(R.id.et_price);
    }
}
