package com.ucarry.developer.android.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourapp.developer.karrierbay.R;

/**
 * Created by skadavath on 6/8/17.
 */

public class CurrentBayViewHolder extends RecyclerView.ViewHolder {

    public ImageView senderImageView , carrierImageView , currAmountIco;
    public TextView fromLoc , toLoc , item , currAmount , currItem , startTime , endTime;
    public View mView;
    public View createdView , pickedView , transitView,scheduledView;

    public CurrentBayViewHolder(View view) {


        super(view);
        mView = view;

        fromLoc = (TextView)view.findViewById(R.id.history_from);
        toLoc = (TextView) view.findViewById(R.id.history_to);
        //item = (TextView) view.findViewById(R.id.item);
        senderImageView = (ImageView) view.findViewById(R.id.sender_image_ico);
        carrierImageView = (ImageView) view.findViewById(R.id.carrier_image_ico);
        createdView = (View) view.findViewById(R.id.created_order_view);

        pickedView = (View) view.findViewById(R.id.picked_order_view);

        scheduledView = (View) view.findViewById(R.id.scheduled_order_view);

        transitView = (View) view.findViewById(R.id.transit_order_view);

        currAmount = (TextView) view.findViewById(R.id.current_amount);

        currItem = (TextView) view.findViewById(R.id.current_item);

        currAmountIco = (ImageView) view.findViewById(R.id.current_amount_ico);

        startTime = (TextView) view.findViewById(R.id.history_start_time);

        endTime = (TextView) view.findViewById(R.id.history_end_time);


    }
}
