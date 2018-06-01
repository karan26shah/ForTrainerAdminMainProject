package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.R;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.viewholders.CardAckViewHolder;
import in.fortrainer.admin.viewholders.EventViewHolder;

import java.util.List;

import in.fortrainer.admin.activities.EventActivity;
import in.fortrainer.admin.models.Event;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.CARD_ACK;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.EVENTS;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.POSTS;

/**
 * Created by specter on 2/20/18.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    LayoutInflater inflater;
    List<CommonRecyclerItem> recyclerItems;

    public EventAdapter(EventActivity context, List<CommonRecyclerItem> recyclerItems) {
        this.recyclerItems = recyclerItems;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        RecyclerView.ViewHolder viewHolder = null;
        if (EVENTS.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_event, parent, false);
            viewHolder = new EventViewHolder(rootView);
        } else if (CARD_ACK.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_card_ack, parent, false);
            viewHolder = new CardAckViewHolder(rootView);
        }
        // return viewHolder;
        // rootView = inflater.inflate(R.layout.vh_event, parent, false);
        //viewHolder = new EventViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (EVENTS.matches(viewType)) {
            ((EventViewHolder) holder).bindData(context, recyclerItems.get(position));
        } else if (CARD_ACK.matches(viewType)) {
            ((CardAckViewHolder) holder).bindData(context, recyclerItems.get(position));
        }
       // EventViewHolder eventViewHolder = (EventViewHolder) holder;
        //eventViewHolder.bindData(context, appEvents.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        switch (recyclerItems.get(position).getItemType()){
            case EVENTS:
                return EVENTS.getId();
            //break;
            case CARD_ACK:
                return CARD_ACK.getId();
            //break;
        }
        return EVENTS.getId();
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }

}
