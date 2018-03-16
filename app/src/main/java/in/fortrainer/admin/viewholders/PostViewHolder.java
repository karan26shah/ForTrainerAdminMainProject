package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.fortrainer.admin.R;
import in.fortrainer.admin.activities.MainActivity;
import in.fortrainer.admin.models.App;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.utilities.AppStorageManager;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by Vivek on 3/15/2018.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    CardView cv1;
    TextView Post_id;
    TextView Post_title;
    TextView Post_sd;

    public PostViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }
    private void bindViews (View itemView){
        cv1 = itemView.findViewById(R.id.cv1);
        Post_id = itemView.findViewById(R.id.Post_id);
        Post_title = itemView.findViewById(R.id.Post_title);
        Post_sd = itemView.findViewById(R.id.Post_sd);
    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        AppPost appPost = (AppPost) commonRecyclerItem.getItem();
        Post_id.setText(String.valueOf(appPost.getId()));
        Post_title.setText(appPost.getTitle());
        Post_sd.setText(appPost.getDescription());

    }
}
