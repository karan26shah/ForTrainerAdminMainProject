package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Order;

import java.text.CollationElementIterator;

public class OrderViewHolder extends RecyclerView.ViewHolder {

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
    public CollationElementIterator textView;


    public OrderViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
        }

    private void bindViews(View itemView) {
        cv = itemView.findViewById(R.id.cv);
        cv1 = itemView.findViewById(R.id.cv1);
        orderCode = itemView.findViewById(R.id.order_code);
        orderId = itemView.findViewById(R.id.order_id);
        orderTotal = itemView.findViewById(R.id.order_total);
        orderProcessed = itemView.findViewById(R.id.order_processed);
        orderSucceeded = itemView.findViewById(R.id.order_succeeded);
        llfirstHolder = itemView.findViewById(R.id.ll_firstHolder);
        userName = itemView.findViewById(R.id.user_name);
        userId = itemView.findViewById(R.id.user_id);

    }

    public void bindData(Context context, Order order){

    orderId.setText(String.valueOf(order.getId()));
    orderTotal.setText(String.valueOf(order.orderTotal));
    orderCode.setText(order.code);
    orderProcessed.setText(String.valueOf(order.processed));
    orderSucceeded.setText(String.valueOf(order.succeeded));
    userName.setText(order.getAppUser().getName());
    userId.setText(String.valueOf(order.getAppUser().getId()));
    }
}
