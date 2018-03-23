package in.fortrainer.admin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import in.fortrainer.admin.R;

import static in.fortrainer.admin.models.Admin.eraseCurrentUserData;
import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class SettingActivity extends AppCompatActivity {
    int appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
       /* if(getIntent().getIntExtra("APP_ID",0)!= 0){
            appId = getIntent().getIntExtra("APP_ID",0);
        }
        else{
            Toast.makeText(SettingActivity.this,"FAIL",Toast.LENGTH_SHORT).show();
        }
        appId = getIntent().getIntExtra("APP_ID",0);*/
        View button = findViewById(R.id.bt_logout);
        button.setOnClickListener( view -> {
            eraseCurrentUserData(context);
            Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
            intent.putExtra("APP_ID",appId);
            startActivity(intent);
        });
    }
}
