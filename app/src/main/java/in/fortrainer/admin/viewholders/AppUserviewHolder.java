package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.CollationElementIterator;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.AppUser;
import in.fortrainer.admin.models.Order;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by foram on 22/3/18.
 */

public class AppUserviewHolder extends RecyclerView.ViewHolder {

    public LinearLayout ll_User_Holder;
    CardView cv_Holder;
    TextView name;
    TextView mobilenumber;
    TextView email;
    public CollationElementIterator textView;

    public AppUserviewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    private void bindViews(View itemView) {
        cv_Holder = itemView.findViewById(R.id.cv_Holder);
        name = itemView.findViewById(R.id.tv_name);
        email = itemView.findViewById(R.id.tv_email);
        mobilenumber = itemView.findViewById(R.id.tv_mobile);
        ll_User_Holder = itemView.findViewById(R.id.ll_User_Holder);

    }
    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){

        AppUser appUser = (AppUser) commonRecyclerItem.getItem();
        name.setText(appUser.getName());
        email.setText(appUser.getEmail());
        mobilenumber.setText(appUser.getMobilenumber());

    }
}
