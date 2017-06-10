package com.ucarry.developer.android.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.ucarry.developer.android.activity.CarrierListActivity;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.FragmentTripSummaryBinding;

import com.ucarry.developer.android.Model.CarrierScheduleDetailAttributes;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Model.ItemAttributes;
import com.ucarry.developer.android.Model.PickupOrderMapping;
import com.ucarry.developer.android.Model.ReceiverOrderMapping;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Model.SenderOrderItemAttributes;
import com.ucarry.developer.android.Model.SenderOrderRequest;
import com.ucarry.developer.android.Model.SenderOrderResponse;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.Utility;
import com.ucarry.developer.android.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripSummaryFragment extends Fragment {


    SenderOrder sender;
    private static String TAG = "TRIP_SUMMARY_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_sender, container, false);
        FragmentTripSummaryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_trip_summary, container, false);
        View view = binding.getRoot();
        sender = ((MainActivity) getActivity()).sender;
        SenderOrderItemAttributes[] sender_order_item_attributes = sender.getSender_order_item_attributes();
        ItemAttributes item = sender_order_item_attributes[0].getItem_attributes();

        binding.setSender(sender);
        binding.setItem(item);
        PickupOrderMapping pickup = sender.getPickupOrderMapping();
        binding.setPickup(pickup);
        ReceiverOrderMapping delivery = sender.getReceiverOrderMapping();
        binding.setDelivery(delivery);
        CarrierScheduleDetailAttributes carrierattribute = sender.getCarrierScheduleDetailAttributes();
        binding.setCarrierattribute(carrierattribute);

        SenderOrderItemAttributes senderorderitem = sender_order_item_attributes[0];
        senderorderitem.setQuantity(1);
        binding.setSenderorderitem(senderorderitem);
        Utility.hideKeyboard(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (sender.isSender) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>SENDER WALL</font>"));
        } else {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>CARRIER WALL</font>"));

        }
        ((MainActivity) getActivity()).apiService = ApiClient.getClientWithHeader(getActivity()).create(ApiInterface.class);

        view.findViewById(R.id.btn_sender_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Call<SenderOrderResponse> call = null;
                SenderOrderRequest senderOrderRequest = new SenderOrderRequest();

                senderOrderRequest.setSenderOrder(sender);
             // ((MainActivity) getActivity()).fragment(new CarrierListFragment(), Constants.LISTFRAGMENT);
                if (sender.isSender) {
                    Log.d(TAG,"CREATING SENDER ORDER REQUEST");
                    call = ((MainActivity) getActivity()).apiService.postSenderOrder("sender", "order", senderOrderRequest);
                    //  call = ((MainActivity) getActivity()).apiService.postSenderOrder("carrier", "schedule", senderOrderRequest);


                } else {
                    senderOrderRequest.setCarrierOrder(senderOrderRequest.getSenderOrder());
                    senderOrderRequest.getCarrierOrder().setPickupOrderMapping(null);
                    senderOrderRequest.getCarrierOrder().setReceiverOrderMapping(null);
                    senderOrderRequest.getCarrierOrder().setSender_order_item_attributes(null);
                    senderOrderRequest.setSenderOrder(null);
                    call = ((MainActivity) getActivity()).apiService.postSenderOrder("carrier", "schedule", senderOrderRequest);

                }

                call.enqueue(new Callback<SenderOrderResponse>() {
                    @Override
                    public void onResponse(Call<SenderOrderResponse> call, Response<SenderOrderResponse> response) {

                        if (response.code() == 201) {
                            Log.d("LoginResponse", response.message());
                            // Log.d("Error",response.body().getErrors().toString());
                            //((MainActivity) getActivity()).fragment(new CarrierListFragment(), Constants.LISTFRAGMENT);
                            Intent intent = new Intent(getActivity(), CarrierListActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d("LoginResponseXXX", response.message());
                            Toast.makeText(getActivity(), "Incorrect RequesZZZt", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<SenderOrderResponse> call, Throwable t) {
                        Log.d("ERROR",t.getLocalizedMessage()+"");
                        Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }


}
