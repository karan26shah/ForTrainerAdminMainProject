package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import in.fortrainer.admin.R;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by HBB20 on 8/3/15.
 */
public class LoadingViewHolder extends RecyclerView.ViewHolder {
    LinearLayout llProgressHolder;

    public LoadingViewHolder(View parentView) {
        super(parentView);
        llProgressHolder = (LinearLayout) parentView.findViewById(R.id.ll_iw_progress_holder);
    }

    public void bindCRITem(Context context, CommonRecyclerItem commonRecyclerItem) {
        llProgressHolder.setVisibility(View.VISIBLE);
    }
}
