package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppPost;

public class PostDetailActivity extends AppCompatActivity {
    AppPost appPost;
    TextView Post_id;
    TextView Post_title;
    TextView Post_sd;
    public FloatingActionButton btEdit;

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


    public static void onPostClicked(Context context, AppPost appPost){
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra("POST_DETAILS", new Gson().toJson(appPost, AppPost.class));
        context.startActivity(intent);
    }

    public void bindViews(){
        Post_id = findViewById(R.id.Post_id);
        Post_title = findViewById(R.id.Post_title);
        Post_sd = findViewById(R.id.Post_sd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        bindViews();
        readIntent();
        init();
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