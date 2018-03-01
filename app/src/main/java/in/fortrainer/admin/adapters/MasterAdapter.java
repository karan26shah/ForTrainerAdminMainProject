package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.viewholders.LoadingViewHolder;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.LOADING;

/**
 * Created by hbb20 on 10/11/16.
 */

public class MasterAdapter extends RecyclerView.Adapter {

    List<CommonRecyclerItem> recyclerItems;
    Context context;
    LayoutInflater layoutInflater;
    OnPageEndReachedListener onPageEndReachedListener;
    public MasterAdapter(Context context, List<CommonRecyclerItem> recyclerItems) {
        this.context = context;
        this.recyclerItems = recyclerItems;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        if (LOADING.matches(viewType)) {
            rootView = layoutInflater.inflate(R.layout.vh_loading, parent, false);
            viewHolder = new LoadingViewHolder(rootView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        //note: use onMaterBindViewHolder() instead
        onMaterBindViewHolder(holder, position);
    }


    protected View generateRootView(int layoutResID, ViewGroup parent) {
        return layoutInflater.inflate(layoutResID, parent, false);
    }

    /**
     * THis function handles some default item types.
     * If the type is handled then returns true
     * else returns false
     *
     * @param holder
     * @param position
     * @return
     */
    public boolean onMaterBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (onPageEndReachedListener != null && position > recyclerItems.size() - 3) {
            onPageEndReachedListener.onPageEndReached();
        }
        if (LOADING.matches(viewType)) {
            ((LoadingViewHolder) holder).bindCRITem(context, recyclerItems.get(position));
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return recyclerItems.get(position).getItemType().getId();
    }

    public List<CommonRecyclerItem> getRecyclerItems() {
        return recyclerItems;
    }

    public void setRecyclerItems(List<CommonRecyclerItem> recyclerItems) {
        this.recyclerItems = recyclerItems;
    }

    public OnPageEndReachedListener getOnPageEndReachedListener() {
        return onPageEndReachedListener;
    }

    public void setOnPageEndReachedListener(OnPageEndReachedListener onPageEndReachedListener) {
        this.onPageEndReachedListener = onPageEndReachedListener;
    }

    public interface OnPageEndReachedListener {
        public void onPageEndReached();
    }
}

/*
*
*
* @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder masterViewHolder = super.onCreateViewHolder(parent, viewType);
        if (masterViewHolder != null) {
            return masterViewHolder;
        } else {
            RecyclerView.ViewHolder viewHolder = null;
            View rootView;
            if (.matches(viewType)) {
                rootView = layoutInflater.inflate(R.layout., parent, false);
                viewHolder = new (rootView);
            }
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!super.onMaterBindViewHolder(holder, position)) {
            int viewType = getItemViewType(position);
            if (.matches(viewType)) {
                (() holder).bindCRItem(context, recyclerItems.get(position));
            }
        }
    }
*/