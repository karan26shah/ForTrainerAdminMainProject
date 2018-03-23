package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppUser;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.viewholders.AppUserviewHolder;
import in.fortrainer.admin.viewholders.CardAckViewHolder;
import in.fortrainer.admin.viewholders.OrderViewHolder;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.CARD_ACK;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.APP_USER;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.ORDERS;

/**
 * Created by foram on 22/3/18.
 */

public class AppUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context=null;
    LayoutInflater inflater;
    List<CommonRecyclerItem> recyclerItems;

    public AppUserAdapter(Context context, List<CommonRecyclerItem> recyclerItems)
    {
        this.recyclerItems = recyclerItems;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        if (APP_USER.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_user, parent, false);
            viewHolder = new AppUserviewHolder(rootView);
        } else if (CARD_ACK.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_card_ack, parent, false);
            viewHolder = new CardAckViewHolder(rootView);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (APP_USER.matches(viewType)) {
            ((AppUserviewHolder)holder).bindData(context,recyclerItems.get(position));
        } else if (CARD_ACK.matches(viewType)) {
            ((CardAckViewHolder) holder).bindData(context, recyclerItems.get(position));
        }
    }
    @Override
    public int getItemViewType(int position) {
        switch (recyclerItems.get(position).getItemType()){
            case APP_USER:
                return APP_USER.getId();
            case CARD_ACK:
                return CARD_ACK.getId();
        }
        return APP_USER.getId();
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }

}




