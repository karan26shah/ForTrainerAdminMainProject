package in.fortrainer.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Order;

public class OrderDetailActivity extends AppCompatActivity {

    public LinearLayout llfirstHolder;
    CardView cv;
    CardView cv1;
    TextView orderId;
    TextView orderCode;
    TextView orderTotal;
    TextView orderProcessed;
    TextView orderSucceeded;
    TextView userName;
    TextView userId;
    Order order;

    public static void onOrderClicked(Context context, Order order){
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("ORDER_DETAILS", new Gson().toJson(order, Order.class));
        context.startActivity(intent);
    }

    public void bindViews(){
        cv = findViewById(R.id.cv);
        cv1 = findViewById(R.id.cv1);
        orderCode = findViewById(R.id.order_code);
        orderId = findViewById(R.id.order_id);
        orderTotal = findViewById(R.id.order_total);
        orderProcessed = findViewById(R.id.order_processed);
        orderSucceeded = findViewById(R.id.order_succeeded);
        llfirstHolder = findViewById(R.id.ll_firstHolder);
        userName = findViewById(R.id.user_name);
        userId = findViewById(R.id.user_id);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        bindViews();
        readIntent();
        if(order != null) {
            setOrderDetails();
        }else{
            Toast.makeText(this, "failed to get order details", Toast.LENGTH_SHORT).show();

        }

    }
    private void setOrderDetails() {
        orderId.setText(String.valueOf(order.getId()));
        orderTotal.setText(String.valueOf(order.orderTotal));
        orderCode.setText(order.code);
        orderProcessed.setText(String.valueOf(order.processed));
        orderSucceeded.setText(String.valueOf(order.succeeded));
        userName.setText(order.getAppUser().getFullName());
        userId.setText(String.valueOf(order.getAppUser().getId()));
    }

    private void readIntent(){
        if (getIntent().getStringExtra("ORDER_DETAILS") == null) {
            Toast.makeText(this, "cant get order", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            order = new Gson().fromJson(getIntent().getStringExtra("ORDER_DETAILS"), Order.class);
        }
    }
}
