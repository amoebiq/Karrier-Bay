package Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourapp.developer.karrierbay.R;

/**
 * Created by skadavath on 5/16/17.
 */

public class CarrierViewHolder extends RecyclerView.ViewHolder {


    public TextView carrier_id , carrier_from_loc , carrier_name ,carrier_from_val , carrier_to_val , carrier_address , custom_header;
    public View mView;
    public ImageView carrier_image;

    public CarrierViewHolder(View view) {
        super(view);
        mView = view;

        //carrier_from_loc = (TextView) view.findViewById(R.id.carrier_fromLoc);
        carrier_name = (TextView) view.findViewById(R.id.carrier_name_list_id);
        carrier_image = (ImageView) view.findViewById(R.id.carrier_image_list_id);
        carrier_from_val = (TextView) view.findViewById(R.id.carrier_from_list_id);
        carrier_to_val = (TextView) view.findViewById(R.id.carrier_to_value_list_id);
        carrier_address = (TextView) view.findViewById(R.id.carrier_address_list_id);


    }
}
