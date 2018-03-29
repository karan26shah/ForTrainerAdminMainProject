package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import in.fortrainer.admin.activities.MainActivity;
import in.fortrainer.admin.activities.PostDetailActivity;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.utilities.AppStorageManager;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.models.Image;

import static android.content.ContentValues.TAG;

/**
 * Created by Vivek on 3/15/2018.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    CardView cv1;
    //TextView Post_id;
    TextView Post_title;
    TextView Post_sd;
    LinearLayout linearLayout;
    ImageView imageView;


    public PostViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }
    private void bindViews (View itemView){
        cv1 = itemView.findViewById(R.id.cv1);
       // Post_id = itemView.findViewById(R.id.Post_id);
        Post_title = itemView.findViewById(R.id.Post_title);
        Post_sd = itemView.findViewById(R.id.Post_sd);
        imageView = itemView.findViewById(R.id.app_iv);
        linearLayout = itemView.findViewById(R.id.ll_post);
    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        AppPost appPost = (AppPost) commonRecyclerItem.getItem();
       // Post_id.setText(String.valueOf(appPost.getId())
        Post_title.setText( appPost.getTitle());
        Post_sd.setText(appPost.getDescription());
        if (appPost.getSharedImage() == null || appPost.getSharedImage().getMediumImageUrl() == null) {
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
                    imageView.setImageBitmap(bitmap);
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
            Picasso.with(context).load(appPost.getSharedImage().getMediumImageUrl()).resize(600,300).into(target);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context!= null){
                    PostDetailActivity.onPostClicked(context,appPost);
                }
            }
        });
    }
}
