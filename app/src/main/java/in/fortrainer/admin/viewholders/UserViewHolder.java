package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.models.User;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by foram on 13/3/18.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView mobile;
    TextView email;

    LinearLayout llUserHolder;
    User userList;

    public UserViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);

    }

    public void bindViews(View rootView) {


        name = (TextView) rootView.findViewById(R.id.tv_name);
        mobile = (TextView) rootView.findViewById(R.id.tv_mobile);
        email = (TextView) rootView.findViewById(R.id.tv_email);

        llUserHolder = (LinearLayout) rootView.findViewById(R.id.ll_User_Holder);

    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem) {

        userList = (User)commonRecyclerItem.getItem();
        name.setText(String.valueOf(userList.getName()));
        mobile.setText(userList.getMobileNumber());
        email.setText(userList.getEmail());
    }
}


