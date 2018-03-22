package in.fortrainer.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class BannerEditActivity extends AppCompatActivity {

    EditText bdtitle;
    EditText bdlinkurl;
    Button submit;
    Banner banners;

    public void init() {

        submit = (Button) findViewById(R.id.bt_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBannerList();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_edit);
        readIntent();
        bindViews();
        init();
        setBannerValues();
    }

    private void updateBannerList() {
        Call<JsonObject> BannerListCall = RetrofitHelper.getRetrofitService(context).updateBannerDetails(banners.getId(),bdtitle.getText().toString(), bdlinkurl.getText().toString());
        BannerListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(BannerEditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    launchBannerListActivity();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(BannerEditActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchBannerListActivity(){
        Intent intent = new Intent(BannerEditActivity.this,BannerListActivity.class);
        startActivity(intent);
        finish();
    }
    private void readIntent() {
        if (getIntent().getStringExtra("BANNER_DETAILS") == null) {
            Toast.makeText(this, "cant get banners", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            banners = new Gson().fromJson(getIntent().getStringExtra("BANNER_DETAILS"), Banner.class);
        }
    }
    private void bindViews() {
        bdtitle=findViewById(R.id.bd_title);
        bdlinkurl=findViewById(R.id.bd_linkurl);
        submit=findViewById(R.id.bt_submit);
    }
    private void setBannerValues() {
        bdtitle.setText(banners.getTitle());
        bdlinkurl.setText(String.valueOf(banners.getLinkUrl()));
    }
}
