package in.fortrainer.admin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.AppProduct;

public class ProductEditActivity extends AppCompatActivity {

    EditText pd_name;
    EditText pd_price;
    Button button;
    AppProduct appProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);
        readIntent();
        bindViews();
        setProductValues();
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
