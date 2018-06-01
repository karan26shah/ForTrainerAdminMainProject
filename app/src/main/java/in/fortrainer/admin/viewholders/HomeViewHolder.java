package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import in.fortrainer.admin.activities.HomeActivity;
import in.fortrainer.admin.activities.MainActivity;
import in.fortrainer.admin.activities.OrderActivity;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Order;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

import static android.content.ContentValues.TAG;

/**
 * Created by Vivek on 3/13/2018.
 */

public class HomeViewHolder extends RecyclerView.ViewHolder {
    LinearLayout ll_app;
    CardView cv1;
    //TextView App_id;
    TextView App_name;
    TextView App_sd;
    ImageView App_iv;


    public HomeViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    private void bindViews(View itemView){
        cv1 = itemView.findViewById(R.id.cv1);
      //  App_id = itemView.findViewById(R.id.App_id);
        App_name = itemView.findViewById(R.id.App_name);
        App_sd = itemView.findViewById(R.id.App_sd);
        ll_app = itemView.findViewById(R.id.ll_app);
        App_iv = itemView.findViewById(R.id.app_iv);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        App apps = (App) commonRecyclerItem.getItem();
        //App_id.setText(String.valueOf(apps.getAppId()));
        App_name.setText(apps.getName());
        App_sd.setText(apps.getShortDescription());
        if (apps.getAndroidAppIconImageUrl() == null || apps.getAndroidAppIconImageUrl() == null) {
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
                    App_iv.setImageBitmap(bitmap);
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
            Picasso.with(context).load(apps.getAndroidAppIconImageUrl()).resize(430,240).into(target);
        }

        ll_app.setOnClickListener( view -> {
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("APP_ID",apps.getAppId());
            context.startActivity(intent);
        });
    }
}
