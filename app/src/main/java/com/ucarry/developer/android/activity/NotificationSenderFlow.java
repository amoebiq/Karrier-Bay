package com.ucarry.developer.android.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucarry.developer.android.Model.AcceptOrderResponse;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.CircleTransform;
import com.ucarry.developer.android.Utilities.Utility;
import com.yourapp.developer.karrierbay.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSenderFlow extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ORDER_ID = "order_id";
    public static final String USER_NAME = "name";
    public static final String ADDRESS = "address";
    public static final String IMAGE = "image_url";
    public static final String FROM_ADDRESS = "from_address";
    public static final String TO_ADDRESS = "to_address";
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub_category";
    public static final String ITEM_WEIGHT = "item_weight";
    public static final String ITEM_VALUE = "item_value";
    public static final String RATE = "rate";
    public static final String USER_OBJ = "user_obj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notitification_sender_flow);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Details</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final View rootView = getWindow().getDecorView().getRootView();
        final Intent intent = this.getIntent();
        String image_url = intent.getStringExtra(IMAGE);
        ImageView iv = (ImageView) rootView.findViewById(R.id.sender_detail_image);


        Picasso.Builder picBuilder = new Picasso.Builder(rootView.getContext());
        picBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        picBuilder.build().load(Utility.getAwsUrl(image_url)).placeholder(R.drawable.carrier).transform(new CircleTransform()).error(R.drawable.carrier).into(iv);

//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),ProfileVIewActivity.class);
//                User user = (User) getArguments().getSerializable(USER_OBJ);
//                intent.putExtra(USER_OBJ,user);
//                intent.putExtra("IS_SENDER",true);
//                startActivity(intent);
//            }
//        });

        ((TextView) rootView.findViewById(R.id.sender_detail_name)).setText(intent.getStringExtra(USER_NAME));
        ((TextView) rootView.findViewById(R.id.sender_detail_address)).setText(intent.getStringExtra(ADDRESS));
        ((TextView) rootView.findViewById(R.id.sender_detail_from)).setText(intent.getStringExtra(FROM_ADDRESS));
        ((TextView) rootView.findViewById(R.id.sender_detail_to)).setText(intent.getStringExtra(TO_ADDRESS));
        ((TextView) rootView.findViewById(R.id.sender_detail_item_rate)).setText(intent.getStringExtra(RATE));
        ((TextView) rootView.findViewById(R.id.sender_detail_category)).setText(intent.getStringExtra(CATEGORY));
        ((TextView) rootView.findViewById(R.id.sender_detail_sub_category)).setText(intent.getStringExtra(SUB_CATEGORY));

        Button acceptButton = (Button) rootView.findViewById(R.id.sender_trip_accept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("DETAIL","CLICKED:::"+intent.getStringExtra(ORDER_ID));
                //Toast.makeText(view.getContext(),"Clicked",Toast.LENGTH_SHORT);

                ApiInterface apiInterface = ApiClient.getClientWithHeader(rootView.getContext()).create(ApiInterface.class);


                Call<AcceptOrderResponse> call = apiInterface.acceptOrder(intent.getStringExtra(ORDER_ID));

                final ProgressDialog pd = new ProgressDialog(rootView.getContext());
                pd.setMessage(Constants.LOADING_MESSAGE);
                pd.setIndeterminate(true);
                pd.show();

                call.enqueue(new Callback<AcceptOrderResponse>() {
                    @Override
                    public void onResponse(Call<AcceptOrderResponse> call, Response<AcceptOrderResponse> response) {

                        pd.dismiss();
                        Log.d("ACCEPT_RESPONSE",""+response.code());
                        if(response.code()==200)
                            Log.d("ACCEPT_RESPONSE",""+response.code()+" accepted");
                        Toast.makeText(rootView.getContext(),"Accepted",Toast.LENGTH_LONG);
                        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                        builder.setMessage(Constants.ACCEPT_MESSAGE)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent intent = new Intent(NotificationSenderFlow.this, MainActivity.class);
                                        startActivity(intent);

                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();


                    }

                    @Override
                    public void onFailure(Call<AcceptOrderResponse> call, Throwable t) {

                        Log.d("ACCEPT_ERROR",t.getLocalizedMessage());
                        if(pd.isShowing())
                            pd.dismiss();
                    }
                });


            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
