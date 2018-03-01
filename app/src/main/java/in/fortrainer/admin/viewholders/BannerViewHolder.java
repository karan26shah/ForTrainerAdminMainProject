package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by HBB20 on 8/3/15.
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {

    public BannerViewHolder(View itemView) {
        super(itemView);
    }

    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem) {

        Banner banner = (Banner) commonRecyclerItem.getItem();


    }
}
