package com.ucarry.developer.android.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.yourapp.developer.karrierbay.R;

/**
 * Created by skadavath on 7/24/17.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    public TextView title,time,knowMore;
    public View mView;

    public NotificationViewHolder(View view) {

        super(view);

        mView = view;
        title = (TextView) mView.findViewById(R.id.notif_title);
        time = (TextView) mView.findViewById(R.id.notif_time);
        knowMore = (TextView) mView.findViewById(R.id.know_more);

    }

}
