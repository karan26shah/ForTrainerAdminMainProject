package in.fortrainer.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.HomeActivity;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.viewholders.EventViewHolder;
import in.fortrainer.admin.viewholders.HomeViewHolder;

/**
 * Created by Vivek on 3/13/2018.
 */

public class HomeAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<App> apps;

    public HomeAdpater(HomeActivity context,List<App> apps){
        this.context = context;
        this.apps = apps;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View rootView;
        rootView = inflater.inflate(R.layout.vh_app, parent, false);
        viewHolder = new HomeViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        homeViewHolder.bindData(context, apps.get(position));

    }

    @Override
    public int getItemCount() {
        return apps.size();
    }
}
