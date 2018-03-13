package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.viewholders.BannerViewHolder;

/**
 * Created by Karan on 21-02-2018.
 */

public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<Banner> recyclerItem;

    public BannerAdapter(Context context, List<Banner> recyclerItems)
    {
        this.recyclerItem = recyclerItems;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        rootView = inflater.inflate(R.layout.vh_banner,parent,false);
        viewHolder = new BannerViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        bannerViewHolder.bindData(context, recyclerItem.get(position));
    }

    @Override
    public int getItemCount() {
        return recyclerItem.size();
    }

}


