package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.AppProduct;

public class ProductDetailActivity extends AppCompatActivity {
    TextView Product_id;
    TextView Product_name;
    TextView Product_sd;
    TextView Product_ld;
    TextView Product_price;
    public FloatingActionButton btEdit;
    AppProduct appProduct;

    public void init(){
        btEdit= findViewById(R.id.bt_edit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ProductDetailActivity.this,ProductEditActivity.class);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        bindViews();
        readIntent();
        init();
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
