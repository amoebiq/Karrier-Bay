package com.ucarry.developer.android.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Model.PickupOrderMapping;
import com.ucarry.developer.android.Model.ReceiverOrderMapping;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Utilities.Utility;
import com.ucarry.developer.android.activity.MainActivity;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.FragmentPickupDeliveryScheduleBinding;

public class SenderTripScheduleFragment extends Fragment {


    SenderOrder sender;
    private static String TAG = "SenderTripSchedule";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPickupDeliveryScheduleBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pickup_delivery_schedule, container, false);
        View view = binding.getRoot();
        sender = ((MainActivity) getActivity()).sender;
        setHasOptionsMenu(true);
        binding.setSender(sender);
        PickupOrderMapping pickup = sender.getPickupOrderMapping();
        binding.setPickup(pickup);
        ReceiverOrderMapping delivery = sender.getReceiverOrderMapping();
        binding.setDelivery(delivery);
        return view;

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.d(TAG,"On prepare options menu");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG,"Clearing menu");
        MenuItem item = menu.findItem(R.id.action_home);
        item.setVisible(false);
        inflater.inflate(R.menu.menu_home,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home_ico:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);

                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>SENDER TRIP SCHEDULE</font>"));

        view.findViewById(R.id.btn_sender_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPageValidationSuccess()) {
                    ((MainActivity) getActivity()).fragment(new TripSummaryFragment(),Constants.TRIPSUMMARYFRAGMENT);
                }else{
                    Toast.makeText(getActivity(), "Please provide all fields value", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private boolean isPageValidationSuccess() {
        ReceiverOrderMapping receiverOrderMapping = sender.getReceiverOrderMapping();
        PickupOrderMapping pickupOrderMapping = sender.getPickupOrderMapping();
        String validateCommonStrings[] = {receiverOrderMapping.getName(), receiverOrderMapping.getPhone_1(),
                receiverOrderMapping.getAddress_line_1(), receiverOrderMapping.getAddress_line_2()
                , pickupOrderMapping.getName(), pickupOrderMapping.getPhone_1(), pickupOrderMapping.getAddress_line_1(),
                pickupOrderMapping.getAddress_line_2()
        };

        if (Utility.isNull(validateCommonStrings)) {
            return false;
        }


        return true;
    }

}

