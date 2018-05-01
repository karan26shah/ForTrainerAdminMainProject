package in.fortrainer.admin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Video;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class PostVideoActivity extends AppCompatActivity {
Button button;
EditText editText;
Video video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);
        bindViews();
        init();
    }

    private void bindViews() {
        button = findViewById(R.id.bt_submit);
        editText = findViewById(R.id.et_video);
    }

    private void init() {
        button = findViewById(R.id.bt_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText() != null) {
                    getVideourl();
                }else {
                    Toast.makeText(PostVideoActivity.this, "Enter proper url", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getVideourl() {
        Call<JsonObject> videourlCall = RetrofitHelper.getRetrofitService(context).getYouTubeVideoUrl(editText.getText().toString());
        videourlCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();


                    video = new Gson().fromJson(jsonObject, new TypeToken<Video>() {
                    }.getType());
                    launchPostVideoDetailActivity();
                } else {
                    Toast.makeText(PostVideoActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(PostVideoActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void launchPostVideoDetailActivity() {
        Intent intent = new Intent(PostVideoActivity.this, PostVideoDetailActivity.class);
        intent.putExtra("POST_VIDEO", new Gson().toJson(video, Video.class));
        startActivity(intent);
        finish();
    }

}
