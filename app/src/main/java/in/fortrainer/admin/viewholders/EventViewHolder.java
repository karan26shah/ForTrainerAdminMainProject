package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.fortrainer.admin.activities.EventDetailActivity;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.R;
import in.fortrainer.admin.utilities.CommonRecyclerItem;


/**
 * Created by specter on 2/22/18.
 */

public class EventViewHolder extends RecyclerView.ViewHolder{

    TextView id;
    TextView name;
    TextView venue;
    TextView amt;
    TextView sttime;
    TextView edtime;

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
        amt = rootView.findViewById(R.id.amt);
        sttime = rootView.findViewById(R.id.starttime);
        edtime = rootView.findViewById(R.id.endtime);
        llEventHolder = rootView.findViewById(R.id.ll_event_holder);

    }
    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        Event event = (Event) commonRecyclerItem.getItem();
//       id.setText(String.valueOf(event.getId()));
        name.setText(event.getName());
        venue.setText(event.getAddress().getAddressLine1());
        if(event.getIsPaid()){
            amt.setText(event.getPrice());
        }else{
            amt.setText(event.getPrice());
        }
        sttime.setText(event.getStartDatetime());
        edtime.setText(event.getEndDatetime());
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

    private void launchEventDetailsScreen(Context context, Event event){

    }


}
