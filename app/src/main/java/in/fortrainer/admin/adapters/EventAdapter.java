package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.R;
import in.fortrainer.admin.viewholders.EventViewHolder;

import java.util.List;

import in.fortrainer.admin.activities.EventActivity;
import in.fortrainer.admin.models.Event;

/**
 * Created by specter on 2/20/18.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    LayoutInflater inflater;
    List<Event> appEvents;

    public EventAdapter(EventActivity context, List<Event> appEvents) {
        this.appEvents = appEvents;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        rootView = inflater.inflate(R.layout.event_item, parent, false);
        viewHolder = new EventViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventViewHolder eventViewHolder = (EventViewHolder) holder;
        eventViewHolder.bindData(context, appEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return appEvents.size();
    }

}
