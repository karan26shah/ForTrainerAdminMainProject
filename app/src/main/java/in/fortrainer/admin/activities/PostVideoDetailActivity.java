package in.fortrainer.admin.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Admin;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Video;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class PostVideoDetailActivity extends AppCompatActivity {
    Video video;
    Button button;
    EditText et_title,et_des;
    TextView textView;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video_detail);
        readIntent();
        bindViews();
        setDetails();
        init();

    }

    private void setDetails() {
        et_title.setText(video.getTitle());
        textView.setText(video.getAuthorUrl());
        if (video.getThumbnailUrl() == null) {
            Log.d(TAG, "onViewCreated: image found null");
        } else {
            Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                    imageView.setImageBitmap(bitmap);
                }
                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: BItmap failed");
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };
            Picasso.with(context).load(video.getThumbnailUrl()).resize(600,300).into(target);
        }
    }

    private void init() {
        button = findViewById(R.id.bt_post);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPost();
            }
        });

    }
    private void uploadPost() {
        Call<JsonObject> uploadPostCall = RetrofitHelper.getRetrofitService(context).CreatePost(et_title.getText().toString(),et_des.getText().toString(),video.getProviderUrl(),textView.getText().toString(),video.getThumbnailUrl(),video.getYoutubeVideoId());
        uploadPostCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostVideoDetailActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    launchActivity();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(PostVideoDetailActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void launchActivity() {
        Intent intent = new Intent(PostVideoDetailActivity.this, PostActivity.class);
        startActivity(intent);
        finish();

    }
    private void bindViews() {
        button = findViewById(R.id.bt_post);
        et_title = findViewById(R.id.et_title);
        et_des = findViewById(R.id.et_des);
        textView = findViewById(R.id.tv_url);
        imageView = findViewById(R.id.iv_pv);
    }

    private void readIntent() {
        if (getIntent().getStringExtra("POST_VIDEO") == null) {
            Toast.makeText(this, "can't get video", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            video = new Gson().fromJson(getIntent().getStringExtra("POST_VIDEO"), Video.class);
        }
    }
}

