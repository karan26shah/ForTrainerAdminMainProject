package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.viewholders.BannerViewHolder;
import in.fortrainer.admin.viewholders.CardAckViewHolder;
import in.fortrainer.admin.viewholders.PostViewHolder;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.BANNER;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.CARD_ACK;
import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.POSTS;

/**
 * Created by Karan on 21-02-2018.
 */

public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    //List<Banner> recyclerItem;
    List<CommonRecyclerItem> recyclerItems;

    public BannerAdapter(Context context, List<CommonRecyclerItem> recyclerItems)
    {
        this.recyclerItems = recyclerItems;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        if (BANNER.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_banner, parent, false);
            viewHolder = new BannerViewHolder(rootView);
        } else if (CARD_ACK.matches(viewType)) {
            rootView = inflater.inflate(R.layout.vh_card_ack, parent, false);
            viewHolder = new CardAckViewHolder(rootView);
        }
       // rootView = inflater.inflate(R.layout.vh_banner,parent,false);
        //viewHolder = new BannerViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (BANNER.matches(viewType)) {
            ((BannerViewHolder)holder).bindData(context,recyclerItems.get(position));
        } else if (CARD_ACK.matches(viewType)) {
            ((CardAckViewHolder) holder).bindData(context, recyclerItems.get(position));
        }
       // BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        //bannerViewHolder.bindData(context, recyclerItem.get(position));
    }
    @Override
    public int getItemViewType(int position) {
        switch (recyclerItems.get(position).getItemType()){
            case BANNER:
                return BANNER.getId();
            //break;
            case CARD_ACK:
                return CARD_ACK.getId();
            //break;
        }
        return BANNER.getId();
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }

}


