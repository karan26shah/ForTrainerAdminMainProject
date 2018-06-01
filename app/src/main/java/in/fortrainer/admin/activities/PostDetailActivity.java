package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class PostDetailActivity extends AppCompatActivity {
    AppPost appPost;
    //TextView Post_id;
    TextView Post_title;
    TextView Post_sd;
    Button btEdit;
    Button btremove;
    ImageView imageView;

    public void init(){

        btEdit= findViewById(R.id.bt_edit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(PostDetailActivity.this,PostEditActivity.class);
                intent.putExtra("POST_DETAILS", new Gson().toJson(appPost, AppPost.class));
                startActivity(intent);
            }
        });
    }
    public void removeButtonClick(){

        btremove= (Button)findViewById(R.id.bt_remove);

        btremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<JsonObject> removepostcall = RetrofitHelper.getRetrofitService(context).deletePost(appPost.getId());
                removepostcall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> removepostcall, Response<JsonObject> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(PostDetailActivity.this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent =  new Intent(PostDetailActivity.this,PostActivity.class);
                            //intent.putExtra("POST_DETAILS", new Gson().toJson(appPost, AppPost.class));
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(PostDetailActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public static void onPostClicked(Context context, AppPost appPost){
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra("POST_DETAILS", new Gson().toJson(appPost, AppPost.class));
        context.startActivity(intent);
    }

    public void bindViews(){
        //Post_id = findViewById(R.id.Post_id);
        Post_title = findViewById(R.id.Post_title);
        Post_sd = findViewById(R.id.Post_sd);
        imageView = findViewById(R.id.imageview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        bindViews();
        readIntent();
        init();
        removeButtonClick();
        if(appPost != null) {
            setPostDetails();
        }else{
            Toast.makeText(this, "failed to get post details", Toast.LENGTH_SHORT).show();

        }
    }
    private void setPostDetails() {
        //Post_id.setText(String.valueOf(appPost.getId()));
        Post_title.setText(appPost.getTitle());
        Post_sd.setText(appPost.getDescription());
        if (appPost.postType.code.equals("IMAGE")) {
            if (appPost.getSharedImage() == null || appPost.getSharedImage().getMediumImageUrl() == null) {
                Log.d(TAG, "onViewCreated: image found null");

                //progressBar.setVisibility(View.GONE);
            } else {
                Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
                //progressBar.setVisibility(View.VISIBLE);
                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                        imageView.setImageBitmap(bitmap);
                        //progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d(TAG, "onBitmapFailed: BItmap failed");
                        //progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(context).load(appPost.getSharedImage().getMediumImageUrl()).resize(600, 300).into(target);

            }
        } else {
            if (appPost.postType.code.equals("VIDEO")) {
               // imageUrlToLoad = (String) appPost.getYoutubeImageUrl();
            }

            if (appPost.getYoutubeImageUrl() != null) {
                Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
                //progressBar.setVisibility(View.VISIBLE);
                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d(TAG, "onBitmapFailed: Bitmap failed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(context).load(appPost.getYoutubeImageUrl()).resize(600, 300).into(target);
            }
        }
    }


    private void readIntent(){
        if (getIntent().getStringExtra("POST_DETAILS") == null) {
            Toast.makeText(this, "cant get post", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            appPost = new Gson().fromJson(getIntent().getStringExtra("POST_DETAILS"), AppPost.class);
        }
    }
}
