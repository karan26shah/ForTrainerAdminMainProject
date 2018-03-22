package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class BannerDetailsActivity extends AppCompatActivity {
    TextView Banner_id;
    TextView Banner_title;
    TextView link;
    public Button btEdit;
    Button btremove;
    ImageView imageView;
    Button btRemove;
    Target target;
    Banner banners;


    public static String TAG = "c";


    public void init(){
        btEdit= findViewById(R.id.bt_edit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(BannerDetailsActivity.this,BannerEditActivity.class);
                intent.putExtra("BANNER_DETAILS", new Gson().toJson(banners, Banner.class));
                startActivity(intent);
            }
        });
    }

    public static void onBannerClicked(Context context, Banner banners){
        Intent intent = new Intent(context, BannerDetailsActivity.class);
        intent.putExtra("BANNER_DETAILS", new Gson().toJson(banners, Banner.class));
        context.startActivity(intent);
    }

    public void bindViews() {
        //cv1 = itemView.findViewById(R.id.cv1);
        Banner_id = findViewById(R.id.id1);
        Banner_title = findViewById(R.id.title);
        link = findViewById(R.id.linkurl);
        imageView = findViewById(R.id.imageview);
    }

    public void editButtonClick(){

        btEdit= (Button)findViewById(R.id.bt_edit);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(BannerDetailsActivity.this,BannerEditActivity.class);
                intent.putExtra("BANNER_DETAILS", new Gson().toJson(banners, Banner.class));
                startActivity(intent);

            }
        });
    }

    public void removeButtonClick(){

        btremove= (Button)findViewById(R.id.bt_remove);

        btremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<JsonObject> removebannercall = RetrofitHelper.getRetrofitService(context).deleteBanner(banners.getId());
                removebannercall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> removebannercall, Response<JsonObject> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(BannerDetailsActivity.this, "Event Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(BannerDetailsActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent =  new Intent(BannerDetailsActivity.this,BannerListActivity.class);
                intent.putExtra("BANNER_DETAILS", new Gson().toJson(banners, Banner.class));
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannerdetails);
        bindViews();
        readIntent();
        init();
        editButtonClick();
        removeButtonClick();

        if(banners != null) {
            setBannerDetails();
        }else{
            Toast.makeText(this, "failed to get banner details", Toast.LENGTH_SHORT).show();

        }
    }
    private void setBannerDetails() {
        Banner_id.setText(String.valueOf(banners.getId()));
        Banner_title.setText(banners.getTitle());
        //description.setText((CharSequence) bannerList.getDescription());
        link.setText((CharSequence) banners.getLinkUrl());
        if (banners.getImage() == null || banners.getImage().getMediumImageUrl() == null) {
            Log.d(TAG, "onViewCreated: image found null");
        } else {
            Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                }


                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: BItmap failed");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(banners.getImage().getMediumImageUrl()).resize(600,300).into(target);
            //  imageView.setTag(target);
        }
   }
    private void readIntent(){
        if (getIntent().getStringExtra("BANNER_DETAILS") == null) {
            Toast.makeText(this, "cant get banners", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            banners = new Gson().fromJson(getIntent().getStringExtra("BANNER_DETAILS"), Banner.class);
        }
    }
}
