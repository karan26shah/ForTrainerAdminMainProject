package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.viewholders.BannerViewHolder;

/**
 * Created by root on 16/1/17.
 */
public class MainAdapter{

}

/*
public class MainAdapter extends MasterAdapter {
    public MainAdapter(Context context, List<CommonRecyclerItem> recyclerItems) {
        super(context, recyclerItems);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder masterViewHolder = super.onCreateViewHolder(parent, viewType);
        if (masterViewHolder != null) {
            return masterViewHolder;
        } else {
            RecyclerView.ViewHolder viewHolder = null;
            if (CommonRecyclerItem.ItemType.BANNER.matches(viewType)) {
                viewHolder = new BannerViewHolder(generateRootView(R.layout.vh_banner, parent));
            }
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!super.onMaterBindViewHolder(holder, position)) {
            int viewType = getItemViewType(position);

            if (CommonRecyclerItem.ItemType.BANNER.matches(viewType)) {
                ((BannerViewHolder) holder).bindCRItem(context, recyclerItems.get(position));
            }

        }
    }
}
*/