package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.PostActivity;
import in.fortrainer.admin.activities.ProductActivity;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.viewholders.CardAckViewHolder;
import in.fortrainer.admin.viewholders.PostViewHolder;
import in.fortrainer.admin.viewholders.ProductViewHolder;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.CARD_ACK;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.POSTS;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.PRODUCTS;

/**
 * Created by Vivek on 3/17/2018.
 */

public class ProductAdpater extends RecyclerView.Adapter {
    Context context;
    LayoutInflater inflater;
    List<CommonRecyclerItem> recyclerItems;

    public ProductAdpater(ProductActivity context, List<CommonRecyclerItem> recyclerItems) {
        this.recyclerItems = recyclerItems;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        RecyclerView.ViewHolder viewHolder = null;
        if (PRODUCTS.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_product, parent, false);
            viewHolder = new ProductViewHolder(rootView);
        } else if (CARD_ACK.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_card_ack, parent, false);
            viewHolder = new CardAckViewHolder(rootView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (PRODUCTS.matches(viewType)) {
            ((ProductViewHolder)holder).bindData(context,recyclerItems.get(position));
        } else if (CARD_ACK.matches(viewType)) {
            ((CardAckViewHolder) holder).bindData(context, recyclerItems.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (recyclerItems.get(position).getItemType()){
            case PRODUCTS:
                return PRODUCTS.getId();
            //break;
            case CARD_ACK:
                return CARD_ACK.getId();
            //break;
        }
        return PRODUCTS.getId();
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }
}
