package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;
public class EventDetailActivity extends AppCompatActivity {

    Event event;

    TextView id;
    TextView name;
    TextView venue;
    TextView amt;
    TextView starttime;
    TextView endtime;
    ImageView imageView;


    public Button btEdit;
    public Button btremove;

    public void editButtonClick(){

        btEdit= (Button)findViewById(R.id.bt_edit);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(EventDetailActivity.this,EventEditActivity.class);
                intent.putExtra("EVENT_DETAILS", new Gson().toJson(event, Event.class));
                startActivity(intent);

            }
        });
    }

    public void removeButtonClick(){

        btremove= (Button)findViewById(R.id.bt_remove);

        btremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<JsonObject> removeEventCall = RetrofitHelper.getRetrofitService(context).deleteEvent(event.getId());
                removeEventCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(EventDetailActivity.this, "Event Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(EventDetailActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent =  new Intent(EventDetailActivity.this,EventActivity.class);
                intent.putExtra("EVENT_DETAILS", new Gson().toJson(event, Event.class));
                startActivity(intent);

            }
        });
    }
    public static void onEventClicked(Context context, Event event){
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra("EVENT_DETAILS", new Gson().toJson(event, Event.class));
        context.startActivity(intent);
    }

    public void bindViews(){

        id = (TextView)findViewById(R.id.id);
        name =(TextView)findViewById(R.id.name);
        venue = (TextView)findViewById(R.id.venue);
        amt =(TextView)findViewById(R.id.amt);
        starttime = (TextView)findViewById(R.id.starttime);
        endtime = (TextView)findViewById(R.id.endtime);
        imageView = findViewById(R.id.app_iv);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        readIntent();
        bindViews();
        editButtonClick();
        removeButtonClick();

        if(event != null) {
            setEventDetails();
        }else{
            Toast.makeText(this, "failed to get event details", Toast.LENGTH_SHORT).show();

        }
    }


    private void setEventDetails(){
        id.setText(String.valueOf(event.getId()));
        name.setText(event.getName());
        venue.setText(event.getAddress().getAddressLine1());
        if(event.getIsPaid()){
            amt.setText(event.getPrice());
        }else{
            amt.setText(event.getPrice());
        }
        starttime.setText(event.getStartDatetime());
        endtime.setText(event.getEndDatetime());
        if (event.getImage() == null || event.getImage().getMediumImageUrl() == null) {
            Log.d(TAG, "onViewCreated: image found null");
            // tvLink.setText(banner.getTitle());
            //progressBar.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
            //progressBar.setVisibility(View.VISIBLE);
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                    imageView.setImageBitmap(bitmap);
                    //tvLink.setText(banner.getTitle());
                    //tvLink.setVisibility(View.GONE);
                    //progressBar.setVisibility(View.GONE);
                    //imgReference.setVisibility(View.INVISIBLE);
                }


                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: BItmap failed");
                    //tvLink.setText(banner.getTitle());
                    //progressBar.setVisibility(View.GONE);
                    //imgReference.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(event.getImage().getMediumImageUrl()).resize(600,300).into(target);
        }
    }

    private void readIntent(){

        if (getIntent().getStringExtra("EVENT_DETAILS") == null) {
            Toast.makeText(this, "cant get event", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            event = new Gson().fromJson(getIntent().getStringExtra("EVENT_DETAILS"), Event.class);
        }
    }

}
