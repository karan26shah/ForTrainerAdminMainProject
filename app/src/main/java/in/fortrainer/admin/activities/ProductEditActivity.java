package in.fortrainer.admin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.AppProduct;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class ProductEditActivity extends AppCompatActivity {

    EditText pd_name;
    EditText pd_price;
    Button button;
    AppProduct appProduct;

    public void init() {

        button = (Button) findViewById(R.id.bt_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdateproduct();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);
        readIntent();
        bindViews();
        setProductValues();
        init();
    }
    public void doUpdateproduct() {
        Call<JsonObject> productListCall = RetrofitHelper.getRetrofitService(context).Updateproduct(appProduct.getId(),pd_name.getText().toString(),pd_price.getText().toString());
        productListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(ProductEditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    launchProductActivity();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProductEditActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchProductActivity() {
        Intent intent = new Intent(ProductEditActivity.this, ProductActivity.class);
        startActivity(intent);
        finish();

    }


    private void readIntent() {
        if (getIntent().getStringExtra("PRODUCT_DETAILS") == null) {
            Toast.makeText(this, "cant get product", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            appProduct = new Gson().fromJson(getIntent().getStringExtra("PRODUCT_DETAILS"), AppProduct.class);
        }
    }
    private void bindViews() {
        pd_name=findViewById(R.id.pd_name);
        pd_price=findViewById(R.id.pd_price);
        button=findViewById(R.id.bt_submit);
    }
    private void setProductValues() {
        pd_name.setText(appProduct.getName());
        pd_price.setText(String.valueOf(appProduct.getPrice()));
    }
}
