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
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class PostDetailActivity extends AppCompatActivity {
    AppPost appPost;
    TextView Post_id;
    TextView Post_title;
    TextView Post_sd;
    public Button btEdit;
    Button btremove;
    ImageView imageView;
    public static String TAG = "c";
    Target target;


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
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(PostDetailActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent =  new Intent(PostDetailActivity.this,PostActivity.class);
                intent.putExtra("POST_DETAILS", new Gson().toJson(appPost, AppPost.class));
                startActivity(intent);

            }
        });
    }


    public static void onPostClicked(Context context, AppPost appPost){
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra("POST_DETAILS", new Gson().toJson(appPost, AppPost.class));
        context.startActivity(intent);
    }

    public void bindViews(){
        Post_id = findViewById(R.id.Post_id);
        Post_title = findViewById(R.id.Post_title);
        Post_sd = findViewById(R.id.Post_sd);
        imageView = findViewById(R.id.imageview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        bindViews();
        readIntent();
        init();
  //      editButtonClick();
        removeButtonClick();
        if(appPost != null) {
            setPostDetails();
        }else{
            Toast.makeText(this, "failed to get post details", Toast.LENGTH_SHORT).show();

        }
    }
    private void setPostDetails() {
        Post_id.setText(String.valueOf(appPost.getId()));
        Post_title.setText(appPost.getTitle());
        Post_sd.setText(appPost.getDescription());
        if (appPost.getImage() == null || appPost.getImage().getMediumImageUrl() == null) {
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
            Picasso.with(context).load(appPost.getImage().getMediumImageUrl()).resize(600,300).into(target);

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
