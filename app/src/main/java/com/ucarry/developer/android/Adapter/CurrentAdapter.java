package com.ucarry.developer.android.Adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucarry.developer.android.Model.SenderOrderItemAttributes;
import com.ucarry.developer.android.Model.SenderOrderResponse;
import com.ucarry.developer.android.Utilities.CurrentBayViewHolder;
import com.ucarry.developer.android.Utilities.Utility;
import com.yourapp.developer.karrierbay.BR;
import com.yourapp.developer.karrierbay.R;

import java.util.List;

import com.ucarry.developer.android.Model.CarrierScheduleDetailAttributes;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Utilities.CustomViewHolder;

/**
 * Created by carl on 12/1/15.
 */

   public class CurrentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SenderOrder> historyList;
    private CarrierScheduleDetailAttributes carrierScheduleDetailAttributes;

    public CurrentAdapter(List<SenderOrder> historyList) {
        this.historyList = historyList;

    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       // ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_current_list, viewGroup, false);
        System.out.println("Here in my adapter");
        return new CurrentBayViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_current_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        CurrentBayViewHolder cVH = (CurrentBayViewHolder) holder;

        Log.d("VALLL:::",historyList.get(position).getStatus()+"");

        cVH.fromLoc.setText(historyList.get(position).getFrom_loc());
        cVH.toLoc.setText(historyList.get(position).getTo_loc());


        if(historyList.get(position).getOrder_items()!=null) {


            if(historyList.get(position).getOrder_items().size()>0) {

                SenderOrderItemAttributes so = historyList.get(position).getOrder_items().get(0);


                //cVH.item.setText(so.getItem_type());
                cVH.currItem.setText(so.getItem_type());
                cVH.startTime.setText(so.getStart_time());
                cVH.endTime.setText(so.getEnd_time());

            }


        } else if(historyList.get(position).getCarrier_schedule_detail()!=null) {

            //cVH.item.setText(historyList.get(position).getCarrier_schedule_detail().getReady_to_carry());


            cVH.currItem.setText(historyList.get(position).getCarrier_schedule_detail().getReady_to_carry());
            cVH.startTime.setText(Utility.convertToProperDateFromServer(historyList.get(position).getCarrier_schedule_detail().getStart_time()));
            cVH.endTime.setText(Utility.convertToProperDateFromServer(historyList.get(position).getCarrier_schedule_detail().getEnd_time()));
        }
        else {


        }

        if(historyList.get(position).getSender_id()!=null) {

            cVH.carrierImageView.setVisibility(View.VISIBLE);
            cVH.senderImageView.setVisibility(View.GONE);
            cVH.currAmount.setText(historyList.get(position).getGrandTotal());


        }
        else {

            cVH.senderImageView.setVisibility(View.VISIBLE);
            cVH.carrierImageView.setVisibility(View.GONE);
            cVH.currAmountIco.setVisibility(View.GONE);
            cVH.currAmount.setVisibility(View.GONE);

        }



        cVH.createdView.setBackgroundResource(R.drawable.solid_circle);
        if(historyList.get(position).getStatus()!=null) {

            if(historyList.get(position).getStatus().equalsIgnoreCase("active")) {


                cVH.createdView.setBackgroundResource(R.drawable.solid_red_circle);
                cVH.pickedView.setBackgroundResource(R.drawable.solid_circle);
                cVH.transitView.setBackgroundResource(R.drawable.solid_circle);

            }

            else  if(historyList.get(position).getStatus().equalsIgnoreCase("picked")) {

                cVH.createdView.setBackgroundResource(R.drawable.solid_circle);
                cVH.transitView.setBackgroundResource(R.drawable.solid_circle);
                cVH.pickedView.setBackgroundResource(R.drawable.solid_red_circle);

            }

            else if(historyList.get(position).getStatus().equalsIgnoreCase("scheduled")) {

                cVH.createdView.setBackgroundResource(R.drawable.solid_circle);
                cVH.pickedView.setBackgroundResource(R.drawable.solid_circle);
                cVH.transitView.setBackgroundResource(R.drawable.solid_red_circle);

            }


        }

    }

//    @Override
//    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
//        ViewDataBinding viewDataBinding = customViewHolder.getViewDataBinding();
//        viewDataBinding.setVariable(BR.sender, historyList.get(i));
//        viewDataBinding.setVariable(BR.carrier, historyList.get(i).getCarrier_schedule_detail());
//        viewDataBinding.setVariable(BR.user,historyList.get(i).getUser());
//    }
//


    @Override
    public int getItemCount() {


        Log.d("RECYCLE VIEW","GET ITEM COUNT"+historyList.size());
        return (null != historyList ? historyList.size() : 0);
    }


}