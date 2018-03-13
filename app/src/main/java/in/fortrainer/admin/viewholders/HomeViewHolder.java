package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.HomeActivity;
import in.fortrainer.admin.activities.MainActivity;
import in.fortrainer.admin.activities.OrderActivity;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.Order;

/**
 * Created by Vivek on 3/13/2018.
 */

public class HomeViewHolder extends RecyclerView.ViewHolder {
    LinearLayout ll_app;
    CardView cv1;
    TextView App_id;
    TextView App_name;
    TextView App_sd;


    public HomeViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    private void bindViews(View itemView){
        cv1 = itemView.findViewById(R.id.cv1);
        App_id = itemView.findViewById(R.id.App_id);
        App_name = itemView.findViewById(R.id.App_name);
        App_sd = itemView.findViewById(R.id.App_sd);
        ll_app = itemView.findViewById(R.id.ll_app);
    }
    public void bindData(Context context, App apps){
        App_id.setText(String.valueOf(apps.getAppId()));
        App_name.setText(apps.getName());
        App_sd.setText(apps.getShortDescription());
        ll_app.setOnClickListener( view -> {
            Intent intent = new Intent(context,MainActivity.class);
            context.startActivity(intent);
        });
    }
}
