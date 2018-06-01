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

import in.fortrainer.admin.activities.EventDetailActivity;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.R;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

import static android.content.ContentValues.TAG;


/**
 * Created by specter on 2/22/18.
 */

public class EventViewHolder extends RecyclerView.ViewHolder{

    TextView id;
    TextView name;
    TextView venue;
    //TextView amt;
    //TextView sttime;
    //TextView edtime;
    ImageView imageView;

    LinearLayout llEventHolder;

    public EventViewHolder(View itemView)
    {
        super(itemView);
        bindViews(itemView);

    }
    public void bindViews(View rootView){

        id = rootView.findViewById(R.id.id);
        name = rootView.findViewById(R.id.name);
        venue = rootView.findViewById(R.id.venue);
        //amt = rootView.findViewById(R.id.amt);
        //sttime = rootView.findViewById(R.id.starttime);
        //edtime = rootView.findViewById(R.id.endtime);
        llEventHolder = rootView.findViewById(R.id.ll_event_holder);
        imageView = rootView.findViewById(R.id.app_iv);

    }
    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        Event event = (Event) commonRecyclerItem.getItem();
//       id.setText(String.valueOf(event.getId()));
        name.setText(event.getName());
        venue.setText(event.getAddress().getAddressLine1());
        /*if(event.getIsPaid()){
            amt.setText(event.getPrice());
        }else{
            amt.setText(event.getPrice());
        }
        sttime.setText(event.getStartDatetime());
        edtime.setText(event.getEndDatetime());*/
        if (event.getImage() == null || event.getImage().getMediumImageUrl() == null) {
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
            Picasso.with(context).load(event.getImage().getMediumImageUrl()).resize(700,300).into(target);
        }



        llEventHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //launchEventDetailsScreen();
                if(context!= null){
                    EventDetailActivity.onEventClicked(context,event);
                }
            }
        });
    }
}
