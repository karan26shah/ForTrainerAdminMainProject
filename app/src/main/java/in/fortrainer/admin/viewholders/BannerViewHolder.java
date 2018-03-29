package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.BannerDetailActivity;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.CommonRecyclerItem;


public class BannerViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    ImageView imageView;
    Target target;
    public static String TAG = "c";
    LinearLayout linearLayout;


    public BannerViewHolder(View itemView)
    {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View rootview){

        title = rootview.findViewById(R.id.title);
        imageView= rootview.findViewById(R.id.imageview);
        linearLayout= rootview.findViewById(R.id.bd_l1);
    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){
        Banner banners = (Banner)commonRecyclerItem.getItem();
        title.setText(banners.getTitle());
        if (banners.getSharedImage() == null || banners.getSharedImage().getMediumImageUrl() == null) {
            Log.d(TAG, "onViewCreated: image found null");
        } else {
            Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                    imageView.setImageBitmap(bitmap);
                   /* imageView.setVisibility(View.VISIBLE);*/
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: BItmap failed");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(banners.getSharedImage().getMediumImageUrl()).resize(600,300).into(target);
            //  imageView.setTag(target);
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //launchEventDetailsScreen();
                if(context!= null){
                    BannerDetailActivity.onBannerClicked(context,banners);
                }

            }
        });
    }


}
