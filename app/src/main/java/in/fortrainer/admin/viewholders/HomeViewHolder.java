package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.Order;

/**
 * Created by Vivek on 3/13/2018.
 */

public class HomeViewHolder extends RecyclerView.ViewHolder {

    CardView cv1;
    TextView App_id;
    TextView App_name;
    TextView App_sd;


    public HomeViewHolder(View itemView) {
        super(itemView);
    }

    private void bindViews(View itemView){
        cv1 = itemView.findViewById(R.id.cv1);
        App_id = itemView.findViewById(R.id.App_id);
        App_name = itemView.findViewById(R.id.App_name);
        App_sd = itemView.findViewById(R.id.App_sd);
    }
    public void bindData(Context context, App apps){
        App_id.setText(String.valueOf(apps.getId()));
        App_name.setText(apps.getName());
        App_sd.setText(apps.getShortDescription());
    }
}
