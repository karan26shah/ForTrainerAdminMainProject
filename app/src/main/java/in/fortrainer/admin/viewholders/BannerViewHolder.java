package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.utilities.CommonRecyclerItem;


public class BannerViewHolder extends RecyclerView.ViewHolder{
    TextView id;
    TextView title;
    TextView description;
    TextView linkurl;

    public BannerViewHolder(View itemView)
    {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View rootview){
        id = (TextView)rootview.findViewById(R.id.id1);
        title = (TextView)rootview.findViewById(R.id.title);
        //description = (TextView)rootview.findViewById(R.id.description);
        linkurl = (TextView)rootview.findViewById(R.id.linkurl);
    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){
        Banner banners = (Banner)commonRecyclerItem.getItem();
        id.setText(String.valueOf(banners.getId()));
        title.setText(banners.getTitle());
        //description.setText((CharSequence) bannerList.getDescription());
        linkurl.setText((CharSequence) banners.getLinkUrl());

    }
}
