package Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.BindingAdapter;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yourapp.developer.karrierbay.R;

import java.util.List;

import Fragment.SenderTripScheduleFragment;
import Fragment.TripDetailsFragment;
import Model.Constants;
import Model.SenderOrder;
import Model.User;
import activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vel on 28/1/17.
 */

public class BindingUtils {
    @BindingAdapter({"bind:selection"})
    public static void setSelection(Spinner spinner, int position) {
        Toast.makeText(spinner.getContext(), "Selected", Toast.LENGTH_LONG).show();
        spinner.setSelection(position);
    }

    public void onNotifyClick(View v, SenderOrder senderOrder) {

         final MainActivity mainActivity = (MainActivity) v.getRootView().getContext();
        mainActivity.sender=senderOrder;
         mainActivity.fragment(new TripDetailsFragment(), Constants.DETAILSFRAGMENT);

    }

}