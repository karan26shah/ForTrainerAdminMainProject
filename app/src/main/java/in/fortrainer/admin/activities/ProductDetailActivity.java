package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.AppProduct;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class ProductDetailActivity extends AppCompatActivity {
    TextView Product_id;
    TextView Product_name;
    TextView Product_sd;
    TextView Product_ld;
    TextView Product_price;
    ImageView imageView;
    //public FloatingActionButton btEdit;
    Button button;
    AppProduct appProduct;

    public void init(){
        button= findViewById(R.id.bt_edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ProductDetailActivity.this,ProductEditActivity.class);
                intent.putExtra("PRODUCT_DETAILS", new Gson().toJson(appProduct, AppProduct.class));
                startActivity(intent);
            }
        });
    }

    public void removeButtonClick(){
        button= (Button)findViewById(R.id.bt_remove);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<JsonObject> removeProductCall = RetrofitHelper.getRetrofitService(context).deleteProduct(appProduct.getId());
                removeProductCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(ProductDetailActivity.this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ProductDetailActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent =  new Intent(ProductDetailActivity.this,ProductActivity.class);
                intent.putExtra("PRODUCT_DETAILS", new Gson().toJson(appProduct, AppProduct.class));
                startActivity(intent);

            }
        });
    }



    public static void onProductClicked(Context context, AppProduct appProduct){
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("PRODUCT_DETAILS", new Gson().toJson(appProduct, AppProduct.class));
        context.startActivity(intent);
    }

    public void bindViews() {
        //cv1 = itemView.findViewById(R.id.cv1);
        Product_id = findViewById(R.id.Product_id);
        Product_name = findViewById(R.id.Product_name);
        Product_sd = findViewById(R.id.Product_sd);
        Product_ld = findViewById(R.id.Product_ld);
        Product_price = findViewById(R.id.Product_price);
        imageView = findViewById(R.id.app_iv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        bindViews();
        readIntent();
        init();
        removeButtonClick();
        if(appProduct != null) {
            setProductDetails();
        }else{
            Toast.makeText(this, "failed to get product details", Toast.LENGTH_SHORT).show();
        }
    }
    private void setProductDetails() {
        Product_id.setText(String.valueOf(appProduct.getId()));
        Product_name.setText(appProduct.getName());
        Product_sd.setText(appProduct.getShortDescription());
        Product_ld.setText(appProduct.getLongDescription());
        Product_price.setText(String.valueOf(appProduct.getPrice()));
        if (appProduct.getImage().getMediumImageUrl() == null || appProduct.getImage().getMediumImageUrl() == null) {
            Log.d(TAG, "onViewCreated: image found null");
            // tvLink.setText(banner.getTitle());
            //progressBar.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
            //progressBar.setVisibility(View.VISIBLE);
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                    imageView.setImageBitmap(bitmap);
                    //tvLink.setText(banner.getTitle());
                    //tvLink.setVisibility(View.GONE);
                    //progressBar.setVisibility(View.GONE);
                    //imgReference.setVisibility(View.INVISIBLE);
                }


                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: BItmap failed");
                    //tvLink.setText(banner.getTitle());
                    //progressBar.setVisibility(View.GONE);
                    //imgReference.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(appProduct.getImage().getMediumImageUrl()).resize(600,300).into(target);
        }
    }
    private void readIntent(){
        if (getIntent().getStringExtra("PRODUCT_DETAILS") == null) {
            Toast.makeText(this, "cant get product", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            appProduct = new Gson().fromJson(getIntent().getStringExtra("PRODUCT_DETAILS"), AppProduct.class);
        }
    }
}
