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

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class PostEditActivity extends AppCompatActivity {

    EditText edTitle;
    EditText edsd;
    Button button;
    AppPost appPost;
    int appId;

    public void init() {

        button = (Button) findViewById(R.id.bt_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdatepost();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_edit);
      /*  if(getIntent().getIntExtra("APP_ID",0)!= 0){
            appId = getIntent().getIntExtra("APP_ID",0);
        }
        else{
            Toast.makeText(PostEditActivity.this,"FAIL",Toast.LENGTH_SHORT).show();
        }
        appId = getIntent().getIntExtra("APP_ID",0);*/
        readIntent();
        bindViews();
        setPostValues();
        init();
    }

    public void doUpdatepost() {
        Call<JsonObject> productListCall = RetrofitHelper.getRetrofitService(context).Updatepost(appPost.getId(),edTitle.getText().toString(),edsd.getText().toString());
        productListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(PostEditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    launchPostActivity();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(PostEditActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchPostActivity() {
        Intent intent = new Intent(PostEditActivity.this, PostActivity.class);
        startActivity(intent);
        finish();

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
