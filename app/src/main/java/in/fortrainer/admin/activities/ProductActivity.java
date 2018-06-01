package in.fortrainer.admin.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import in.fortrainer.admin.utilities.AdminHelper;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class ProductActivity extends AppCompatActivity {


    List<AppProduct> appProducts;
    CommonRecyclerScreen crs;
    ProductAdpater productAdpater;
    Button button;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        button = findViewById(R.id.bt_nointernet);
        linearLayout = findViewById(R.id.nointernet);
        setScreen();
    }

    private void setScreen() {
        crs = CommonRecyclerScreen.setupWithActivity(this);
        productAdpater = new ProductAdpater(this, crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(productAdpater);
        getProducts();
    }

    private void getProducts() {
        if (AdminHelper.isDataAdapterOn(context)) {
            crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
            Call<JsonObject> productListCall = RetrofitHelper.getRetrofitService(context).getProductslist();
            productListCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        appProducts = new Gson().fromJson(jsonObject.getAsJsonArray("app_products"), new TypeToken<List<AppProduct>>() {
                        }.getType());

                        if (appProducts.size() == 0) {
                            CommonRecyclerItem commonRecyclerItem = new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK, "No Products yet", this);
                            crs.recyclerItems.add(commonRecyclerItem);
                        } else {
                            crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.POSTS, appProducts, this));
                        }
                        productAdpater.notifyDataSetChanged();
                        crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);

                    } else {
                        Toast.makeText(ProductActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(ProductActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else
        {
            Toast.makeText(ProductActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.GONE);
                    getProducts();

                }

            });

        }
    }
}

