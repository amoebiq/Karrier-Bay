package com.ucarry.developer.android.Utilities;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.yourapp.developer.karrierbay.R;

import com.ucarry.developer.android.Fragment.TripDetailsFragment;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.activity.MainActivity;

/**
 * Created by vel on 28/1/17.
 */

public class BindingUtils {
    @BindingAdapter({"bind:selection"})
    public static void setSelection(Spinner spinner, int position) {
        Toast.makeText(spinner.getContext(), "Selected", Toast.LENGTH_LONG).show();
        spinner.setSelection(position);
    }


    public BindingUtils() {

        Log.d("","In constructor");

    }

    public void onNotifyClick(View v, SenderOrder senderOrder) {

        Log.d("Clicked","Clicked");
        final MainActivity mainActivity = (MainActivity) v.findViewById(R.id.listviewsenders).getContext();

        Context ctx = v.getRootView().getContext();
        MainActivity ac = (MainActivity)ctx;
        mainActivity.sender=senderOrder;
         ac.fragment(new TripDetailsFragment(), Constants.DETAILSFRAGMENT);






    }

}