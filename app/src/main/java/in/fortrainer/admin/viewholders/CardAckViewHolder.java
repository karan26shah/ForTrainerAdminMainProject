package in.fortrainer.admin.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.CommonRecyclerItem;

/**
 * Created by Vivek on 3/15/2018.
 */

public class CardAckViewHolder extends RecyclerView.ViewHolder {

    TextView cardAck;

    public CardAckViewHolder(View itemView) {
        super(itemView);
        bindView(itemView);

    }

    private void bindView(View itemView) {
        cardAck = itemView.findViewById(R.id.card_ack);
    }

    public void bindData(Context context, CommonRecyclerItem commonRecyclerItem){
        String msg = (String) commonRecyclerItem.getItem();
        cardAck.setText(msg);

    }
}
