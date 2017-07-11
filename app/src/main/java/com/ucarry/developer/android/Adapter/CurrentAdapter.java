package com.ucarry.developer.android.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.ucarry.developer.android.Utilities.SessionManager;
import com.ucarry.developer.android.Utilities.Utility;
import com.ucarry.developer.android.activity.MyBayActivity;
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
    private View mView;
    private final String TAG = "CurrentAdapter";
    private Boolean isCarrier=true;

    private SessionManager sessionManager;

    public CurrentAdapter(List<SenderOrder> historyList) {
        this.historyList = historyList;

    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       // ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_current_list, viewGroup, false);
        System.out.println("Here in my adapter");
        sessionManager = new SessionManager(viewGroup.getContext());
        return new CurrentBayViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_current_list, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        CurrentBayViewHolder cVH = (CurrentBayViewHolder) holder;

        Log.d("VALLL:::",historyList.get(position).getStatus()+"");

        cVH.fromLoc.setText(historyList.get(position).getFrom_loc());
        cVH.toLoc.setText(historyList.get(position).getTo_loc());


        final String uid = sessionManager.getvalStr(SessionManager.KEY_EMAIL);

        final SenderOrder seO = historyList.get(position);


        if(historyList.get(position).getOrder_items()!=null) {


            if(historyList.get(position).getOrder_items().size()>0) {

                SenderOrderItemAttributes so = historyList.get(position).getOrder_items().get(0);


                //cVH.item.setText(so.getItem_type());
                cVH.currItem.setText(so.getItem_type());
                cVH.startTime.setText(Utility.convertToProperDateFromServer(so.getStart_time()));
                cVH.endTime.setText(Utility.convertToProperDateFromServer(so.getEnd_time()));

            }


        } else if(historyList.get(position).getCarrier_schedule_detail()!=null) {

            //cVH.item.setText(historyList.get(position).getCarrier_schedule_detail().getReady_to_carry());


            cVH.currItem.setText(historyList.get(position).getCarrier_schedule_detail().getReady_to_carry());
            cVH.startTime.setText(Utility.convertToProperDateFromServer(historyList.get(position).getCarrier_schedule_detail().getStart_time()));
            cVH.endTime.setText(Utility.convertToProperDateFromServer(historyList.get(position).getCarrier_schedule_detail().getEnd_time()));
        }
        else {


        }

        if(historyList.get(position).getSender_id()!=null ) {

            Log.d(TAG+"DD",seO.getSender_id());
            Log.d(TAG+"DD",uid);
           // Log.d(TAG+"XX",seO.getSender_id()+"");
            if(!seO.getSender_id().equals(uid)) {

                isCarrier = true;
                cVH.carrierImageView.setVisibility(View.VISIBLE);
                cVH.senderImageView.setVisibility(View.GONE);
                cVH.currAmount.setText(seO.getTotal_amount());
            }
                else {
                isCarrier = false;
                cVH.senderImageView.setVisibility(View.VISIBLE);
                cVH.carrierImageView.setVisibility(View.GONE);
                cVH.currAmount.setText(historyList.get(position).getGrandTotal());

            }

        }
        else {
            isCarrier = true;
            Log.d(TAG,historyList.get(position).getUser().getUid());
            cVH.carrierImageView.setVisibility(View.VISIBLE);
            cVH.senderImageView.setVisibility(View.GONE);
            cVH.currAmountIco.setVisibility(View.GONE);
            cVH.currAmount.setVisibility(View.GONE);

        }



        cVH.createdView.setBackgroundResource(R.drawable.solid_circle);
        if(historyList.get(position).getStatus()!=null) {

            if(historyList.get(position).getStatus().equalsIgnoreCase("active")) {


                cVH.createdView.setBackgroundResource(R.drawable.solid_red_circle);
                cVH.pickedView.setBackgroundResource(R.drawable.solid_circle);
                cVH.transitView.setBackgroundResource(R.drawable.solid_circle);
                cVH.transitView.setBackgroundResource(R.drawable.solid_circle);

            }

            else  if(historyList.get(position).getStatus().equalsIgnoreCase("scheduled")) {

                cVH.createdView.setBackgroundResource(R.drawable.solid_circle);
                cVH.transitView.setBackgroundResource(R.drawable.solid_circle);
                cVH.pickedView.setBackgroundResource(R.drawable.solid_circle);
                cVH.scheduledView.setBackgroundResource(R.drawable.solid_red_circle);

            }

            else  if(historyList.get(position).getStatus().equalsIgnoreCase("pickedup")) {

                cVH.createdView.setBackgroundResource(R.drawable.solid_circle);
                cVH.transitView.setBackgroundResource(R.drawable.solid_circle);
                cVH.pickedView.setBackgroundResource(R.drawable.solid_red_circle);

            }

            else if(historyList.get(position).getStatus().equalsIgnoreCase("intransit")) {

                cVH.createdView.setBackgroundResource(R.drawable.solid_circle);
                cVH.pickedView.setBackgroundResource(R.drawable.solid_circle);
                cVH.scheduledView.setBackgroundResource(R.drawable.solid_circle);
                cVH.transitView.setBackgroundResource(R.drawable.solid_red_circle);

            }


        }


        cVH.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG,"In OnClick");
                Log.d(TAG,"ISCarrier ::: "+isCarrier);
                Context context = view.getContext();
                Intent intent = new Intent(context, MyBayActivity.class);
                if(isCarrier) {
                    intent.putExtra(MyBayActivity.IS_CARRIER,true);
                    intent.putExtra(MyBayActivity.CARRIER_OBJ,historyList.get(position));
                }
                else {
                    intent.putExtra(MyBayActivity.IS_CARRIER,false);
                    intent.putExtra(MyBayActivity.SENDER_OBJ,historyList.get(position));
                }

                intent.putExtra(MyBayActivity.USER_OBJ,historyList.get(position).getUser());


                context.startActivity(intent);


            }
        });

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