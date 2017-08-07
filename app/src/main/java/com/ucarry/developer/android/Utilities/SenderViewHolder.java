package com.ucarry.developer.android.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.yourapp.developer.karrierbay.R;

/**
 * Created by skadavath on 5/15/17.
 */

public class SenderViewHolder extends RecyclerView.ViewHolder {


    public TextView sender_id , sender_from_loc , sender_name ,sender_from_val , sender_to_val , sender_address , custom_header , tvAmount , tvItem;
    public View mView;
    public ImageView sender_image;

    public SenderViewHolder(View view) {
        super(view);
        mView = view;

        //sender_from_loc = (TextView) view.findViewById(R.id.sender_fromLoc);
        sender_name = (TextView) view.findViewById(R.id.sender_name_list_id);
        sender_image = (ImageView) view.findViewById(R.id.sender_image_list_id);
        sender_from_val = (TextView) view.findViewById(R.id.sender_from_list_id);
        sender_to_val = (TextView) view.findViewById(R.id.sender_to_value_list_id);
        sender_address = (TextView) view.findViewById(R.id.sender_address_list_id);
        tvAmount = (TextView) view.findViewById(R.id.tvRate);
        tvItem = (TextView) view.findViewById(R.id.tvItem);


    }
}
