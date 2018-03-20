package in.fortrainer.admin.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.HomeActivity;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.viewholders.CardAckViewHolder;
import in.fortrainer.admin.viewholders.EventViewHolder;
import in.fortrainer.admin.viewholders.HomeViewHolder;
import in.fortrainer.admin.viewholders.PostViewHolder;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.APPS;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.CARD_ACK;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.POSTS;

/**
 * Created by Vivek on 3/13/2018.
 */

public class HomeAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<App> apps;
    List<CommonRecyclerItem> recyclerItems;

    public HomeAdpater(HomeActivity context,List<CommonRecyclerItem> recyclerItems){
        this.context = context;
        this.recyclerItems = recyclerItems;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        if (APPS.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_app, parent, false);
            viewHolder = new HomeViewHolder(rootView);
        } else if (CARD_ACK.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_card_ack, parent, false);
            viewHolder = new CardAckViewHolder(rootView);
        }
        //rootView = inflater.inflate(R.layout.vh_app, parent, false);
        //viewHolder = new HomeViewHolder(rootView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (APPS.matches(viewType)) {
            ((HomeViewHolder)holder).bindData(context,recyclerItems.get(position));
        } else if (CARD_ACK.matches(viewType)) {
            ((CardAckViewHolder) holder).bindData(context, recyclerItems.get(position));
        }
        //HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        //homeViewHolder.bindData(context, apps.get(position));

    }
    @Override
    public int getItemViewType(int position) {
        switch (recyclerItems.get(position).getItemType()){
            case APPS:
                return APPS.getId();
            //break;
            case CARD_ACK:
                return CARD_ACK.getId();
            //break;
        }
        return APPS.getId();
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }
}
