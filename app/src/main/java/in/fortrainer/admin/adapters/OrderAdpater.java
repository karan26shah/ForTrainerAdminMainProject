package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Order;
import in.fortrainer.admin.viewholders.LoadingViewHolder;
import in.fortrainer.admin.viewholders.OrderViewHolder;

//import com.vivek.sampleadminapp.ViewHolders.UserViewHolder;
//import com.vivek.sampleadminapp.models.Order;

import java.util.List;


public class OrderAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> orders;
    private Context context = null;
    String itemType;
    OnPageEndReachedLstener onPageEndReachedLstener;
    LayoutInflater inflater;

    public OrderAdpater(List<Order> orders, Context context, String itemType) {
        this.context = context;
        this.orders = orders;
        this.itemType = itemType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        if (itemType.equals("LOADING")) {
            rootView = inflater.inflate(R.layout.layout_loading_item, viewGroup, false);
            viewHolder = new LoadingViewHolder(rootView);
        } else if (itemType.equals("ORDER")) {
            rootView = inflater.inflate(R.layout.vh_order, viewGroup, false);
            viewHolder = new OrderViewHolder(rootView);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(onPageEndReachedLstener != null && position > orders.size() - 3){
            onPageEndReachedLstener.onPageEndReached();
        }
        if (itemType.equals("LOADING")) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.bindData(context, orders.get(position));
        } else if (itemType.equals("ORDER")) {
            OrderViewHolder userViewHolder = (OrderViewHolder) holder;
            userViewHolder.bindData(context, orders.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public OnPageEndReachedLstener getOnPageEndReachedLstener(){
        return onPageEndReachedLstener;
    }

    public void SetOnPageEndReachedLstener(OnPageEndReachedLstener onPageEndReachedLstener){
        this.onPageEndReachedLstener = onPageEndReachedLstener;
    }

    public interface OnPageEndReachedLstener{
        public void onPageEndReached();
    }
}
