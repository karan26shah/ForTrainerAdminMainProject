package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.OrderActivity;
import in.fortrainer.admin.models.Order;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.viewholders.CardAckViewHolder;
import in.fortrainer.admin.viewholders.LoadingViewHolder;
import in.fortrainer.admin.viewholders.OrderViewHolder;
import in.fortrainer.admin.viewholders.PostViewHolder;

//import com.vivek.sampleadminapp.ViewHolders.UserViewHolder;
//import com.vivek.sampleadminapp.models.Order;

import java.util.List;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.CARD_ACK;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.ORDERS;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.POSTS;


public class OrderAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context = null;
    LayoutInflater inflater;
    List<CommonRecyclerItem> recyclerItems;

    public OrderAdpater(OrderActivity context, List<CommonRecyclerItem> recyclerItems) {
        this.context = context;
        this.recyclerItems = recyclerItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        if (ORDERS.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_order, parent, false);
            viewHolder = new OrderViewHolder(rootView);
        } else if (CARD_ACK.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_card_ack, parent, false);
            viewHolder = new CardAckViewHolder(rootView);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (ORDERS.matches(viewType)) {
            ((OrderViewHolder)holder).bindData(context,recyclerItems.get(position));
        } else if (CARD_ACK.matches(viewType)) {
            ((CardAckViewHolder) holder).bindData(context, recyclerItems.get(position));
        }
    }
    @Override
    public int getItemViewType(int position) {
        switch (recyclerItems.get(position).getItemType()){
            case ORDERS:
                return ORDERS.getId();
            //break;
            case CARD_ACK:
                return CARD_ACK.getId();
            //break;
        }
        return ORDERS.getId();
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }
}
