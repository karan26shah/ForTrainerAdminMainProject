package in.fortrainer.admin.viewholders;

import android.content.Context;
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

import java.text.CollationElementIterator;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.AppUserDetailsActivity;
import in.fortrainer.admin.activities.AppUsersListActivity;
import in.fortrainer.admin.activities.UserDetailActivity;
import in.fortrainer.admin.models.AppUser;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by foram on 29/3/18.
 */

public class AppUserListViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG ="" ;
    public LinearLayout ll_User_Holder;
    CardView cv_Holder;
    TextView name;
    TextView mobilenumber;
    TextView email;
    ImageView iv;
     Target target;
     LinearLayout linearLayout;
    public CollationElementIterator textView;

    public AppUserListViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    private void bindViews(View itemView) {
        cv_Holder = itemView.findViewById(R.id.cv1);
        name = itemView.findViewById(R.id.tv_name1);
        ll_User_Holder = itemView.findViewById(R.id.ll_appuser);
           iv=(ImageView)itemView.findViewById(R.id.iv);

    }
    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        AppUser appUser = (AppUser) commonRecyclerItem.getItem();
        name.setText(appUser.getName());

        if (appUser.getImage() == null || appUser.getImage().getMediumImageUrl() == null) {
            Log.d(TAG, "onViewCreated: image found null");
        } else {
            Log.d(TAG, "onViewCreated: image is not null...trying to load it/");
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.d(TAG, "onBitmapLoaded: bitmap loaded");
                    iv.setImageBitmap(bitmap);
                    iv.setVisibility(View.VISIBLE);
                }


                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: BItmap failed");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(appUser.getImage().getMediumImageUrl()).resize(600,300).into(target);
            //  imageView.setTag(target);
        }
        ll_User_Holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //launchEventDetailsScreen();
                if(context!= null){
                    AppUsersListActivity.onUserClicked(context,appUser);
                }

            }
        });
    }

    private void launchEventDetailsScreen(Context context, AppUser appUser){



    }
}
