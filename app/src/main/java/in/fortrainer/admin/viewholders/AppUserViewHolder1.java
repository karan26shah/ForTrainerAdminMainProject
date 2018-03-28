package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.CollationElementIterator;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppUser;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by foram on 28/3/18.
 */

public class AppUserViewHolder1 extends RecyclerView.ViewHolder {

    public LinearLayout ll_appuser;
    CardView cv1;
    TextView name1;
    ImageView iv1;
    public CollationElementIterator textView;

    public AppUserViewHolder1(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    private void bindViews(View itemView) {
        cv1 = itemView.findViewById(R.id.cv1);
        name1 = itemView.findViewById(R.id.tv_name1);
        iv1=itemView.findViewById(R.id.iv);

        ll_appuser = itemView.findViewById(R.id.ll_appuser);

    }
    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        AppUser appUser1 = (AppUser) commonRecyclerItem.getItem();
        name1.setText(appUser1.getName());

        //imageview




    }
}
