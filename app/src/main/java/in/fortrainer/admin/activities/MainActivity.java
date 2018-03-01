package in.fortrainer.admin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;

public class MainActivity extends AppCompatActivity {

    CommonRecyclerScreen crs;
    List<Banner> bannerList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
