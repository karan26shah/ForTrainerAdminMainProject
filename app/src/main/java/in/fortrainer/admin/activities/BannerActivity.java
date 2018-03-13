package in.fortrainer.admin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.fortrainer.admin.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import in.fortrainer.admin.adapters.BannerAdapter;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;


public class BannerActivity extends AppCompatActivity{
    List<Banner> banners;
    RecyclerView bannerlist;
    BannerAdapter bannerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        bannerlist = (RecyclerView)findViewById(R.id.bannerList);
        listBannerList();
    }


    private void listBannerList()
    {
        Call<List<Banner>> call = RetrofitHelper.getRetrofitService(context).listBanner();
        call.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                banners= response.body();

                Toast.makeText(BannerActivity.this, "successful", Toast.LENGTH_LONG).show();
                setBannerListToAdapter();
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                Log.d("error: ", "failed ");
                t.printStackTrace();
            }
        });
    }

    private void setBannerListToAdapter(){
        bannerlist.setLayoutManager(new LinearLayoutManager(this));
        bannerAdapter = new BannerAdapter(this,banners);
        bannerlist.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
    }

}
