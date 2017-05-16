package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.yourapp.developer.karrierbay.R;

import java.util.ArrayList;
import java.util.List;

import Model.CarrierSchedules;
import Model.SenderOrder;
import Model.SenderOrderItemAttributes;
import Utilities.CarrierViewHolder;
import Utilities.CircleTransform;
import Utilities.HeaderViewHolder;
import Utilities.SenderViewHolder;
import activity.CarrierDetailActivity;
import activity.SenderListActivityDetailActivity;
import activity.SenderListActivityDetailFragment;

/**
 * Created by skadavath on 5/16/17.
 */

public class CarrierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CarrierSchedules> carrierSchedules;

    private View mView;

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_CELL = 1;

    public CarrierAdapter(List<CarrierSchedules> list) {

        this.carrierSchedules = list;

    }

    @Override
    public int getItemViewType(int position) {
        return (position==0) ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("ON_CREATE",""+viewType);
        View view;
        switch (viewType) {

            case (TYPE_HEADER):
                Log.d("CARRIER_LIST","Type Header");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_header_text_view, parent, false);
                return new HeaderViewHolder(view);

            case (TYPE_CELL):
                Log.d("CARRIER_LIST","Type Cell");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrier_list_content, parent, false);
                return new CarrierViewHolder(view);

        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        // holder.sender_from_loc.setText(senderOrderList.get(position).getFrom_loc());

        Log.d("ON_CREATE",""+position);

        switch(getItemViewType(position)) {

            case TYPE_CELL:
                CarrierViewHolder sVH = (CarrierViewHolder)holder;
                sVH.carrier_name.setText(carrierSchedules.get(position).getUser().getName());
                sVH.carrier_from_val.setText(carrierSchedules.get(position).getFrom_loc());
                sVH.carrier_to_val.setText(carrierSchedules.get(position).getTo_loc());
                sVH.carrier_address.setText(carrierSchedules.get(position).getUser().getAddress());



                //  Log.d("SENDER_DETAIL",senderOrderList.get(position).getSender_order_item_attributes()[0].getItem_type());

                Picasso.with(sVH.mView.getContext())

                        .load(carrierSchedules.get(position).getUser().getImage())
                        .placeholder(R.drawable.carrier)
                        .error(R.drawable.myimage).transform(new CircleTransform())
                        .into(sVH.carrier_image);


                sVH.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Context context = view.getContext();
                        Intent intent = new Intent(context, CarrierDetailActivity.class);
                        intent.putExtra(SenderListActivityDetailFragment.ARG_ITEM_ID, "1");
                        //getDetails(intent,senderOrderList.get(position));
                        context.startActivity(intent);

                    }
                });

                break;

            case TYPE_HEADER:
                HeaderViewHolder hVH = (HeaderViewHolder)holder;
                hVH.custom_header.setText(carrierSchedules.size()+" CARRIERS FOUND");
                break;

        }
    }

    @Override
    public int getItemCount() {

        Log.d("GET_ITEM_COUNT","GET_ITEM_COUNT::: "+carrierSchedules.size());
        return carrierSchedules.size();
    }



}
