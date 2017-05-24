package com.ucarry.developer.android.Fragment;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.TripDetailsBinding;

import org.json.JSONObject;

import com.ucarry.developer.android.Model.AcceptResponse;
import com.ucarry.developer.android.Model.CarrierScheduleDetailAttributes;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Model.SenderOrderItemAttributes;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.Utility;
import com.ucarry.developer.android.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailsFragment extends Fragment {


    SenderOrder sender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TripDetailsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.trip_details, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        sender = ((MainActivity) getActivity()).sender;
        SenderOrderItemAttributes[] sender_order_item_attributes = sender.getSender_order_item_attributes();
        binding.setSender(sender);
        CarrierScheduleDetailAttributes carrierattribute = sender.getCarrierScheduleDetailAttributes();
        binding.setCarrierattribute(carrierattribute);
        User user = sender.getUser();
        binding.setUser(user);
        SenderOrderItemAttributes senderorderitem = sender_order_item_attributes[0];
        binding.setSenderorderitem(senderorderitem);
        Utility.hideKeyboard(getActivity());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Toolbar tb = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        tb.setNavigationIcon(getResources().getDrawable(R.drawable.back ));
//
//        tb.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Context ctx = view.getContext();
//                MainActivity com.ucarry.developer.android.activity = (MainActivity)ctx;
//                com.ucarry.developer.android.activity.fragment(new SenderListFragment(),"SenderListFragment");
//            }
//        });

        android.support.v7.app.ActionBar bar = ((MainActivity) getActivity()).getSupportActionBar();
        if (!sender.isSender) {
            bar.setTitle(Html.fromHtml("<font color='#ffffff'>SENDER Details</font>"));
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeButtonEnabled(true);


        } else {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>CARRIER Details</font>"));

        }
        ((MainActivity) getActivity()).apiService = ApiClient.getClientWithHeader(getActivity()).create(ApiInterface.class);

        view.findViewById(R.id.btn_sender_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sender.getOrder_id() == null) {
                    Toast.makeText(getActivity(), "Order id is null", Toast.LENGTH_LONG).show();
                    return;
                }
                Call<AcceptResponse> call;

                call = ((MainActivity) getActivity()).apiService.acceptOrders(sender.getOrder_id());
                call.enqueue(new Callback<AcceptResponse>() {
                    @Override
                    public void onResponse(Call<AcceptResponse> call, Response<AcceptResponse> response) {
                        if (response.code() == 200) {
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.quote_popup);
                            TextView textHeade = (TextView) dialog.findViewById(R.id.textView1);
                            textHeade.setText("THANK YOU!!!");
                            TextView text = (TextView) dialog.findViewById(R.id.textView2);
                            text.setText(
                                    "Thank you for preferring our service. Your request for courier has been plces successfully. " +
                                            "Our executive will reach you soon for the packing and pickup.");

                            Button dialogButton = (Button) dialog.findViewById(R.id.btn_continue);
                            ImageView ivPop = (ImageView) dialog.findViewById(R.id.ivPop);

                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getActivity().onBackPressed();
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                            // if button is clicked, close the custom dialog
                            ivPop.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });


                        } else {
                            try {
                                JSONObject j = new JSONObject(response.errorBody().string());
                                Toast.makeText(getActivity(), j.get("error").toString(), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<AcceptResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
                    }
                });
            }

        });

    }
}
