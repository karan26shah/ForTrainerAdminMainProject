package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.AppProduct;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by Vivek on 3/17/2018.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    CardView cv1;
    TextView Product_id;
    TextView Product_name;
    TextView Product_sd;
    TextView Product_ld;
    TextView Product_price;


    public ProductViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    private void bindViews(View itemView) {
        cv1 = itemView.findViewById(R.id.cv1);
        Product_id = itemView.findViewById(R.id.Product_id);
        Product_name = itemView.findViewById(R.id.Product_name);
        Product_sd = itemView.findViewById(R.id.Product_sd);
        Product_ld = itemView.findViewById(R.id.Product_ld);
        Product_price = itemView.findViewById(R.id.Product_price);
    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        AppProduct appProduct = (AppProduct) commonRecyclerItem.getItem();
        Product_id.setText(String.valueOf(appProduct.getId()));
        Product_name.setText(appProduct.getName());
        Product_sd.setText(appProduct.getShortDescription());
        Product_ld.setText(appProduct.getLongDescription());
        Product_price.setText(String.valueOf(appProduct.getPrice()));
    }
}

