package in.fortrainer.admin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.PostAdpater;
import in.fortrainer.admin.adapters.ProductAdpater;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.AppProduct;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class ProductActivity extends AppCompatActivity {

    int appId;
    List<AppProduct> appProducts;
    CommonRecyclerScreen crs;
    ProductAdpater productAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        if(getIntent().getIntExtra("APP_ID",0)!= 0){
            appId = getIntent().getIntExtra("APP_ID",0);
        }
        else{
            Toast.makeText(ProductActivity.this,"FAIL",Toast.LENGTH_SHORT).show();
        }
        //set screen
        setScreen();
    }
    private void setScreen(){
        crs = CommonRecyclerScreen.setupWithActivity(this);
        productAdpater = new ProductAdpater(this,crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(productAdpater);
        getProducts();
    }

    private void getProducts() {
        crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
        Call<JsonObject> productListCall = RetrofitHelper.getRetrofitService(context).getProductslist();
        productListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonObject jsonObject = response.body();
                    appProducts = new Gson().fromJson(jsonObject.getAsJsonArray("app_products"), new TypeToken<List<AppProduct>>() {
                    }.getType());

                    if (appProducts.size() == 0)
                    {
                        CommonRecyclerItem commonRecyclerItem =new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK,"No posts yet",this);
                        crs.recyclerItems.add(commonRecyclerItem);
                    }else {
                        crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.POSTS, appProducts,this));
                    }
                    productAdpater.notifyDataSetChanged();
                    crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);

                }else{
                    Toast.makeText(ProductActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        }
        );}
}
