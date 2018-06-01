package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppUser;
import in.fortrainer.admin.models.Banner;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class AppUserDetailsActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    AppUser appUser;
    TextView user_id;
    TextView user_name;
    TextView user_mobile_number;
    TextView user_emal;
    Button button;
    ImageView imageView;
    Target target;


    public static String TAG = "c";



    public static void onUserClicked(Context context, AppUser appUser) {
        Intent intent = new Intent(context, AppUserDetailsActivity.class);
        intent.putExtra("APP_USERS_DETAILS", new Gson().toJson(appUser, AppUser.class));
        context.startActivity(intent);
    }

    public void bindViews() {
        user_id = findViewById(R.id.id1);
        user_name = findViewById(R.id.name);
        user_emal = findViewById(R.id.email);
        user_mobile_number = findViewById(R.id.mobile_number);
        imageView = findViewById(R.id.imageview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_users_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        bindViews();
        readIntent();

        if (appUser!= null) {
            setAppUsersDetails();
        } else {
            Toast.makeText(this, "failed to get users details", Toast.LENGTH_SHORT).show();

        }
    }

    private void setAppUsersDetails() {
        user_id.setText(String.valueOf(appUser.getId()));
        user_name.setText(appUser.getFullName());
        user_emal.setText(appUser.getEmail());
        user_mobile_number.setText(appUser.getMobileNumber());

        if (appUser.getProfileImageUrl() == null) {
            Log.d(TAG, "onViewCreated: image found null");
        } else {
            Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                    imageView.setImageBitmap(bitmap);
                    //imageView.setVisibility(View.VISIBLE);
                }


                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: BItmap failed");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(appUser.getProfileImageUrl()).resize(600, 300).into(target);
            //  imageView.setTag(target);
        }
    }

    private void readIntent() {
        if (getIntent().getStringExtra("APP_USERS_DETAILS") == null) {
            Toast.makeText(this, "cant get banners", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            appUser = new Gson().fromJson(getIntent().getStringExtra("APP_USERS_DETAILS"), AppUser.class);
        }
    }
}