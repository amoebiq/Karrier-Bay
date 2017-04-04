package Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.FragmentSenderBinding;
import com.yourapp.developer.karrierbay.databinding.FragmentTripSummaryBinding;

import org.json.JSONException;
import org.json.JSONObject;

import Model.CarrierScheduleDetailAttributes;
import Model.Constants;
import Model.ItemAttributes;
import Model.PickupOrderMapping;
import Model.ReceiverOrderMapping;
import Model.SenderOrder;
import Model.SenderOrderItemAttributes;
import Model.SenderOrderRequest;
import Model.SenderOrderResponse;
import Model.User;
import RetroGit.ApiClient;
import RetroGit.ApiInterface;
import Utilities.Utility;
import activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripSummaryFragment extends Fragment {


    SenderOrder sender;

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
              ((MainActivity) getActivity()).fragment(new CarrierListFragment(), Constants.LISTFRAGMENT);
                if (sender.isSender) {
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
                            ((MainActivity) getActivity()).fragment(new CarrierListFragment(), Constants.LISTFRAGMENT);
                        } else {
                            Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<SenderOrderResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }


}
