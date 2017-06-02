package com.ucarry.developer.android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.yourapp.developer.karrierbay.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.ucarry.developer.android.Model.CarrierSchedules;
import com.ucarry.developer.android.Utilities.CarrierViewHolder;
import com.ucarry.developer.android.Utilities.CircleTransform;
import com.ucarry.developer.android.Utilities.HeaderViewHolder;
import com.ucarry.developer.android.Utilities.Utility;
import com.ucarry.developer.android.activity.CarrierDetailActivity;
import com.ucarry.developer.android.activity.CarrierDetailFragment;
import com.ucarry.developer.android.activity.SenderListActivityDetailFragment;

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
        this.carrierSchedules.add(0,new CarrierSchedules());

    }

    @Override
    public int getItemViewType(int position) {
        return (position==0) ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("ON_CREATE",""+viewType);
        View view;
        switch (getItemViewType(viewType)) {

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

//                Log.d("IMAGE_CARRIER",carrierSchedules.get(position).getUser().getImage());
//                Picasso.with(sVH.mView.getContext())
//
//                        .load(carrierSchedules.get(position).getUser().getImage())
//                        .placeholder(R.drawable.carrier)
//                        .error(R.drawable.myimage).transform(new CircleTransform())
//                        .into(sVH.carrier_image);


                Picasso.Builder picBuilder = new Picasso.Builder(sVH.mView.getContext());
                picBuilder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                });
                picBuilder.build().load(Utility.getAwsUrl(carrierSchedules.get(position).getUser().getImage())).placeholder(R.drawable.carrier).transform(new CircleTransform()).error(R.drawable.carrier).into(sVH.carrier_image);

                sVH.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Context context = view.getContext();
                        Intent intent = new Intent(context, CarrierDetailActivity.class);
                        intent.putExtra(SenderListActivityDetailFragment.ARG_ITEM_ID, "1");
                        getDetails(intent,carrierSchedules.get(position));
                        context.startActivity(intent);

                    }
                });

                break;

            case TYPE_HEADER:
                HeaderViewHolder hVH = (HeaderViewHolder)holder;
                hVH.custom_header.setText(carrierSchedules.size()-1+" CARRIERS FOUND");
                break;

        }
    }

    @Override
    public int getItemCount() {

        Log.d("GET_ITEM_COUNT","GET_ITEM_COUNT::: "+carrierSchedules.size());
        return carrierSchedules.size();
    }


    public Intent getDetails(Intent intent , CarrierSchedules schedules) {


        Log.d("CARRIERS_EXTRA_INENT",schedules.getUser().getImage());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.SSSZ'");



        intent.putExtra(CarrierDetailFragment.USER_NAME, schedules.getUser().getName());
        intent.putExtra(CarrierDetailFragment.ADDRESS,schedules.getUser().getAddress());
        intent.putExtra(CarrierDetailFragment.IMAGE,schedules.getUser().getImage());
        intent.putExtra(CarrierDetailFragment.FROM_ADDRESS,schedules.getFrom_loc());
        intent.putExtra(CarrierDetailFragment.TO_ADDRESS,schedules.getTo_loc());

        intent.putExtra(CarrierDetailFragment.DATE_FROM,schedules.getCarrierScheduleDetail().getStart_time());
        intent.putExtra(CarrierDetailFragment.DATE_TO,schedules.getCarrierScheduleDetail().getEnd_time());
        intent.putExtra(CarrierDetailFragment.READY_TO_CARRY,schedules.getCarrierScheduleDetail().getReady_to_carry());
        intent.putExtra(CarrierDetailFragment.CAPACITY,schedules.getCarrierScheduleDetail().getCapacity());

        return intent;
    }


}
