package com.ucarry.developer.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ucarry.developer.android.Model.AcceptOrderResponse;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Model.GenericResponse;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.Utility;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.dummy.DummyContent;

import com.ucarry.developer.android.Utilities.CircleTransform;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a single Carrier detail screen.
 * This fragment is either contained in a {@link CarrierListActivity}
 * in two-pane mode (on tablets) or a {@link CarrierDetailActivity}
 * on handsets.
 */
public class CarrierDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String USER_NAME = "name";
    public static final String ADDRESS = "address";
    public static final String IMAGE = "image_url";
    public static final String FROM_ADDRESS = "from_address";
    public static final String TO_ADDRESS = "to_address";
    public static final String CATEGORY = "category";
    public static final String DATE_FROM = "from_date";
    public static final String ITEM_WEIGHT = "item_weight";
    public static final String DATE_TO = "to_date";
    public static final String READY_TO_CARRY = "ready_to_carry";
    public static final String CAPACITY = "capacity";
    public static final String STOP_OVERS = "stop_overs";
    public static final String SCHEDULE_ID = "schedule_id";
    public static final String USER_OBJ = "user_obj";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarrierDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.carrier_detail, container, false);

        // Show the dummy content as text in a TextView.

        String image_url = getArguments().getString(IMAGE);
        ImageView iv = (ImageView) rootView.findViewById(R.id.carrier_detail_image);
//        Picasso.with(rootView.getContext())
//
//                .load(image_url)
//                .placeholder(R.drawable.carrier)
//                .error(R.drawable.myimage).transform(new CircleTransform())
//                .into(iv);
//

        Picasso.Builder picBuilder = new Picasso.Builder(rootView.getContext());
        picBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        picBuilder.build().load(Utility.getAwsUrl(image_url)).placeholder(R.drawable.carrier).transform(new CircleTransform()).error(R.drawable.carrier).into(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProfileVIewActivity.class);
                User user = (User) getArguments().getSerializable(USER_OBJ);
                intent.putExtra(USER_OBJ,user);
                intent.putExtra("IS_SENDER",false);
                startActivity(intent);
            }
        });
        ((TextView) rootView.findViewById(R.id.carrier_detail_name)).setText(getArguments().getString(USER_NAME));
        ((TextView) rootView.findViewById(R.id.carrier_detail_address)).setText(getArguments().getString(ADDRESS));
        ((TextView) rootView.findViewById(R.id.carrier_detail_from)).setText(getArguments().getString(FROM_ADDRESS));
        ((TextView) rootView.findViewById(R.id.carrier_detail_to)).setText(getArguments().getString(TO_ADDRESS));
        ((TextView) rootView.findViewById(R.id.carrier_detail_from_date)).setText(Utility.convertToProperDateFromServer(getArguments().getString(DATE_FROM)));
        ((TextView) rootView.findViewById(R.id.carrier_detail_to_date)).setText(Utility.convertToProperDateFromServer(getArguments().getString(DATE_TO)));
        ((TextView) rootView.findViewById(R.id.carrier_detail_ready_to_carry)).setText(getArguments().getString(READY_TO_CARRY));
        ((TextView) rootView.findViewById(R.id.carrier_detail_capacity)).setText(getArguments().getString(CAPACITY));

        Button notifyButton = (Button) rootView.findViewById(R.id.btn_carrier_next);
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("DETAIL","CLICKED:::"+getArguments().getString(SCHEDULE_ID));
                //Toast.makeText(view.getContext(),"Clicked",Toast.LENGTH_SHORT);

                ApiInterface apiInterface = ApiClient.getClientWithHeader(view.getContext()).create(ApiInterface.class);


                Call<GenericResponse> call = apiInterface.notifyCarrier(getArguments().getString(SCHEDULE_ID));

                final ProgressDialog pd = new ProgressDialog(getContext());
                pd.setMessage(Constants.LOADING_MESSAGE);
                pd.setIndeterminate(true);
                pd.show();

                call.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

                        pd.dismiss();
                        if(response.code()==200) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage(Constants.NOTIFY_MESSAGE)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);

                                        }
                                    });

                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {

                        if(pd.isShowing())
                            pd.dismiss();



                    }
                });


            }
        });
        return rootView;
    }
}
