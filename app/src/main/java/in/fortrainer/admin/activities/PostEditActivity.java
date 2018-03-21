package in.fortrainer.admin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppPost;

public class PostEditActivity extends AppCompatActivity {

    EditText edTitle;
    EditText edsd;
    Button button;
    AppPost appPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_edit);
        readIntent();
        bindViews();
        setPostValues();
    }

    private void readIntent() {
        if (getIntent().getStringExtra("POST_DETAILS") == null) {
        Toast.makeText(this, "cant get post", Toast.LENGTH_SHORT).show();
        finish();
    }else{
        appPost = new Gson().fromJson(getIntent().getStringExtra("POST_DETAILS"), AppPost.class);
    }
    }
    private void bindViews() {
        edTitle = findViewById(R.id.ed_title);
        edsd = findViewById(R.id.ed_sd);
        button = findViewById(R.id.bt_submit);
    }
    private void setPostValues() {
        edTitle.setText(appPost.getTitle());
        edsd.setText(appPost.getDescription());

    }


}
