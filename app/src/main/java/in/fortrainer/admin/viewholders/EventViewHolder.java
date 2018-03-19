package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    TextView time;
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
        time = rootView.findViewById(R.id.time);
        llEventHolder = rootView.findViewById(R.id.ll_event_holder);

    }
    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){
        Event event = (Event) commonRecyclerItem.getItem();
        id.setText(String.valueOf(event.getId()));
        name.setText(event.getName());
        venue.setText(event.getAddressLine1());
        if(event.getIsPaid()){
            amt.setText("true");
        }else{
            amt.setText("false");
        }
        time.setText(event.getStartDatetime());
        llEventHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
