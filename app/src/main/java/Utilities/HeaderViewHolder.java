package Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yourapp.developer.karrierbay.R;

/**
 * Created by skadavath on 5/16/17.
 */

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView custom_header;

        public HeaderViewHolder(View view) {

            super(view);

            custom_header = (TextView) view.findViewById(R.id.custom_header_id);
        }
}
