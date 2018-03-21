package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.CommonRecyclerItem;


public class BannerViewHolder extends RecyclerView.ViewHolder{
    TextView id;
    TextView title;
    TextView description;
    TextView linkurl;
    ImageView imageView;
    Banner banner;
    Target target;
    public static String TAG = "c";


    public BannerViewHolder(View itemView)
    {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View rootview){
        id = (TextView)rootview.findViewById(R.id.id1);
        title = (TextView)rootview.findViewById(R.id.title);
        //description = (TextView)rootview.findViewById(R.id.description);
        linkurl = (TextView)rootview.findViewById(R.id.linkurl);
        imageView=(ImageView)rootview.findViewById(R.id.imageview);

    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){
        Banner banners = (Banner)commonRecyclerItem.getItem();
        id.setText(String.valueOf(banners.getId()));
        title.setText(banners.getTitle());
        //description.setText((CharSequence) bannerList.getDescription());
        linkurl.setText((CharSequence) banners.getLinkUrl());
        if (banners.getImage() == null || banners.getImage().getMediumImageUrl() == null) {
            Log.d(TAG, "onViewCreated: image found null");
        } else {
            Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                }


                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: BItmap failed");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(banners.getImage().getMediumImageUrl()).resize(600,300).into(target);
          //  imageView.setTag(target);
        }
    }
}
