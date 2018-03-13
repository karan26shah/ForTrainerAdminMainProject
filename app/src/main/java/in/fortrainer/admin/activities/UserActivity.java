package in.fortrainer.admin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.fortrainer.admin.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        setAdapter();
        setClickListener();
        loadBanners();

    }

    private void loadBanners() {

    }

    private void setClickListener() {

    }

    private void setAdapter() {

    }
}
