package in.fortrainer.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;

public class MainActivity extends AppCompatActivity {

    CommonRecyclerScreen crs;
    Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.bt_order);
        button.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this,OrderActivity.class);
            startActivity(intent);
        });
        button = findViewById(R.id.bt_banner);
        button.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this,BannerActivity.class);
            startActivity(intent);
        });
        button = findViewById(R.id.bt_event);
        button.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this,EventActivity.class);
            startActivity(intent);
        });




        setAdapter();
        setClickListener();
        loadBanners();
    }

    private void setAdapter() {

    }

    private void setClickListener() {

    }

    private void loadBanners() {

    }


}
