package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.PostDetailActivity;
import in.fortrainer.admin.activities.ProductDetailActivity;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.AppProduct;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

import static android.content.ContentValues.TAG;

/**
 * Created by Vivek on 3/17/2018.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    CardView cv1;
    //TextView Product_id;
    TextView Product_name;
    ImageView Product_iv;
   // TextView Product_sd;
   // TextView Product_ld;
    //TextView Product_price;
    LinearLayout linearLayout;



    public ProductViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    private void bindViews(View itemView) {
        cv1 = itemView.findViewById(R.id.cv1);
       // Product_id = itemView.findViewById(R.id.Product_id);
        Product_name = itemView.findViewById(R.id.Product_name);
        Product_iv = itemView.findViewById(R.id.app_iv);
      //  Product_sd = itemView.findViewById(R.id.Product_sd);
        //Product_ld = itemView.findViewById(R.id.Product_ld);
        //Product_price = itemView.findViewById(R.id.Product_price);
        linearLayout = itemView.findViewById(R.id.pd_ll);
    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        AppProduct appProduct = (AppProduct) commonRecyclerItem.getItem();
       // Product_id.setText(String.valueOf(appProduct.getId()));
        Product_name.setText(appProduct.getName());
        //Product_sd.setText(appProduct.getShortDescription());
        //Product_ld.setText(appProduct.getLongDescription());
        //Product_price.setText(String.valueOf(appProduct.getPrice()));
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
                    Product_iv.setImageBitmap(bitmap);
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
            Picasso.with(context).load(appProduct.getImage().getMediumImageUrl()).resize(700,300).into(target);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context!= null){
                    ProductDetailActivity.onProductClicked(context,appProduct);
                }
            }
        });
    }
}

