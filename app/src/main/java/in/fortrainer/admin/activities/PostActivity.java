package in.fortrainer.admin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.PostAdpater;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class PostActivity extends AppCompatActivity {

    int appId;
    List<AppPost> appPosts;
    PostAdpater postAdpater;
    CommonRecyclerScreen crs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        if(getIntent().getIntExtra("APP_ID",0)!= 0){
            appId = getIntent().getIntExtra("APP_ID",0);
        }
        else{
            Log.d("PostActivity","failed");
            finish();
        }
        //set screen
        setScreen();

    }

    private void setScreen(){
        crs = CommonRecyclerScreen.setupWithActivity(this);
        postAdpater = new PostAdpater(this,crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(postAdpater);
        getPosts();
    }

    private void getPosts() {
        crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
        Call<JsonObject> eventListCall = RetrofitHelper.getRetrofitService(context).getPostlist();
        eventListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonObject jsonObject = response.body();
                    appPosts = new Gson().fromJson(jsonObject.getAsJsonArray("app_posts"), new TypeToken<List<AppPost>>() {
                    }.getType());

                    if (appPosts.size() == 0)
                    {
                        CommonRecyclerItem commonRecyclerItem =new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK,"No posts yet",this);
                        crs.recyclerItems.add(commonRecyclerItem);
                    }else {
                        crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.POSTS, appPosts,this));
                    }
                     postAdpater.notifyDataSetChanged();
                    crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);

                }else{
                    Toast.makeText(PostActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(PostActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
         }
        );}

}
