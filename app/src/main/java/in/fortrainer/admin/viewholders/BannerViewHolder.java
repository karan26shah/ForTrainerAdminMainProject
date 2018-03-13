package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Banner;


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

    public void bindData(Context context, Banner bannerList){

        id.setText(String.valueOf(bannerList.getId()));
        title.setText(bannerList.getTitle());
        //description.setText((CharSequence) bannerList.getDescription());
        linkurl.setText((CharSequence) bannerList.getLinkUrl());

    }
}
