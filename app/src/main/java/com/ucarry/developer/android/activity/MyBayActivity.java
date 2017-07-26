package com.ucarry.developer.android.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.ucarry.developer.android.Fragment.MyBayFragment;
import com.ucarry.developer.android.Model.CarrierScheduleDetail;
import com.ucarry.developer.android.Model.CarrierScheduleDetailAttributes;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Model.OrderCompletion;
import com.ucarry.developer.android.Model.PickupOrderMapping;
import com.ucarry.developer.android.Model.ReceiverOrderMapping;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Model.UpdateOrderRequest;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.SessionManager;
import com.ucarry.developer.android.Utilities.Utility;
import com.yourapp.developer.karrierbay.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBayActivity extends AppCompatActivity {

    private static final int CONTENT_VIEW_ID = 10101010;
    public static final String IS_CARRIER = "is_carrier";
    public static final String CARRIER_OBJ = "carrier_obj";
    public static final String SENDER_OBJ = "sender_obj";
    public static final String USER_OBJ = "user_obj";
    private static final String TAG = "MyBayActivity";
    private LinearLayout llPickupParent;

    private LinearLayout llSecondParentPickup;
    private LinearLayout myBaySenderNameLL;
    private TextView pickupName;
    private TextView pickupPhone;
    private TextView pickupAdd1;
    private TextView pickupAdd2;

    private LinearLayout llRecieverParent;
    private LinearLayout subItemLL;
    private LinearLayout amountLL;

    private LinearLayout llSecondParentReciever;
    private TextView recieverName;
    private TextView recieverPhone;
    private TextView recieverAdd1;
    private TextView recieverAdd2;
    private TextView myBaySenderNameEt;

    private Button cancelOrderOrScheduleButton;
    private Button changeStatusButton;

    private TextView myBayDetailHeaderText;

    private TextView requestedTv;
    private TextView requestedEt;

    private TextView requestedSubItemTv;
    private TextView requestedSubImteEt;

    private TextView displayAmountTv;
    private TextView displayAmountEt;

    private LinearLayout capacityLL;


    SenderOrder senderOrder = null;

    boolean isCarrier;

    boolean canCancel = false;

    String toCancel = null;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bay);
        hidePickUpAndReciever();


        TextView fromLoc = (TextView) findViewById(R.id.my_bay_detail_header_floc);
        TextView toLoc = (TextView) findViewById(R.id.my_bay_detail_header_toplace);
        TextView fromTime = (TextView) findViewById(R.id.my_bay_detail_header_ftime);
        TextView toTime = (TextView) findViewById(R.id.my_bay_detail_header_tdate);

        capacityLL = (LinearLayout) findViewById(R.id.capacity_ll);
        capacityLL.setVisibility(View.GONE);


        myBayDetailHeaderText = (TextView) findViewById(R.id.my_bay_detail_header);
        myBaySenderNameLL = (LinearLayout) findViewById(R.id.my_bay_sender_name_ll);
        myBaySenderNameEt = (TextView) findViewById(R.id.my_bay_sender_name);

        pickupName = (TextView) findViewById(R.id.pickup_name_et);
        pickupPhone = (TextView) findViewById(R.id.pickup_phone_et);
        pickupAdd1 = (TextView) findViewById(R.id.pickup_address1_et);
        pickupAdd2 = (TextView) findViewById(R.id.pickup_address2_et);

        recieverName = (TextView) findViewById(R.id.reciever_name_et);
        recieverPhone = (TextView) findViewById(R.id.reciever_phone_et);
        recieverAdd1 = (TextView) findViewById(R.id.reciever_address1_et);
        recieverAdd2 = (TextView) findViewById(R.id.reciever_address2_et);

        changeStatusButton = (Button) findViewById(R.id.changestatus);
        changeStatusButton.setVisibility(View.GONE);

        requestedEt = (TextView) findViewById(R.id.requestedEt);
        requestedTv = (TextView) findViewById(R.id.requestedTv);

        displayAmountEt = (TextView) findViewById(R.id.amountToDisplayEt);
        displayAmountTv = (TextView) findViewById(R.id.amountToDisplayTv);


        requestedSubImteEt = (TextView) findViewById(R.id.requestedSubItemTv);
        requestedSubItemTv = (TextView) findViewById(R.id.requestedSubItemEt);


        String uid = new SessionManager(MyBayActivity.this).getvalStr(SessionManager.KEY_EMAIL);


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_bay_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Details</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isCarrier = getIntent().getBooleanExtra(IS_CARRIER, false);

        Log.d(TAG, isCarrier + "");
        User user = (User) getIntent().getSerializableExtra(USER_OBJ);
        if (user != null) {

            //Log.d(TAG,user.getName());


        }

        try {

            if (isCarrier) {


                senderOrder = (SenderOrder) getIntent().getSerializableExtra(CARRIER_OBJ);

                if (senderOrder.getSender_id() != null) {


                    myBayDetailHeaderText.setText("Carry Details");
                    myBaySenderNameEt.setText(user.getName());
                    if(!senderOrder.getStatus().equals("completed")) {
                        changeStatusButton.setVisibility(View.VISIBLE);
                        prepareStatusChange();
                        invalidateOptionsMenu();
                    }
                    requestedTv.setText("Item to Carry");
                    requestedEt.setText(senderOrder.getOrder_items().get(0).getItem_type());

                    subItemLL.setVisibility(View.VISIBLE);

                    requestedSubItemTv.setText(senderOrder.getOrder_items().get(0).getItem_subtype());
                    requestedSubImteEt.setText("Sub Item");

                    amountLL.setVisibility(View.VISIBLE);


                    displayAmountTv.setText("You Get");
                    displayAmountEt.setText(senderOrder.getTotal_amount());


                    senderOrder = (SenderOrder) getIntent().getSerializableExtra(CARRIER_OBJ);
                    changeStatusButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showStatusChangeDialogue(view, senderOrder.getOrderId().toString());
                        }
                    });
                    fromTime.setText(Utility.convertToProperDateFromServer(senderOrder.getOrder_items().get(0).getStart_time()));
                    toTime.setText(Utility.convertToProperDateFromServer(senderOrder.getOrder_items().get(0).getEnd_time()));
                    showPickUpAndReciever();
                    ReceiverOrderMapping receiverOrderMapping = senderOrder.getReceiverOrderMapping();
                    PickupOrderMapping pickupOrderMapping = senderOrder.getPickupOrderMapping();
                    setPickupDetails(pickupOrderMapping);
                    setRecieverDetails(receiverOrderMapping);


                } else {
                    Log.d(TAG, "its a carrier...");
                    canCancel = true;
                    myBayDetailHeaderText.setText("Self Carry Request");

                    myBaySenderNameEt.setText("SELF");
                    CarrierScheduleDetailAttributes carrierScheduleDetail = senderOrder.getCarrier_schedule_detail();
                    requestedEt.setText(carrierScheduleDetail.getReady_to_carry());
                    if (carrierScheduleDetail == null) {
                        Log.d(TAG, "Carrier but null:::" + senderOrder.getId());
                    }

                    toCancel = senderOrder.getScheduleId();
                    fromTime.setText(Utility.convertToProperDateFromServer(Utility.convertToProperDateFromServer(senderOrder.getCarrier_schedule_detail().getStart_time())));
                    toTime.setText(Utility.convertToProperDateFromServer(Utility.convertToProperDateFromServer(senderOrder.getCarrier_schedule_detail().getEnd_time())));
                    requestedEt.setText(carrierScheduleDetail.getReady_to_carry());
                    amountLL.setVisibility(View.GONE);

                    Log.d(TAG,"To Cancel :::");
                    Log.d(TAG,senderOrder.getScheduleId());


                }
            } else {

                canCancel = true;
                amountLL.setVisibility(View.VISIBLE);

                myBaySenderNameEt.setText("SELF");
                myBayDetailHeaderText.setText("Self Sending Request");
                senderOrder = (SenderOrder) getIntent().getSerializableExtra(SENDER_OBJ);
                fromTime.setText(Utility.convertToProperDateFromServer(senderOrder.getOrder_items().get(0).getStart_time()));
                toTime.setText(Utility.convertToProperDateFromServer(senderOrder.getOrder_items().get(0).getEnd_time()));

                requestedTv.setText("Want to Send");
                requestedEt.setText(senderOrder.getOrder_items().get(0).getItem_type());
                requestedSubItemTv.setText(senderOrder.getOrder_items().get(0).getItem_subtype());
                requestedSubImteEt.setText("Sub Category");

                displayAmountTv.setText("You Pay");
                displayAmountEt.setText(senderOrder.getGrandTotal());

                showPickUpAndReciever();
                ReceiverOrderMapping receiverOrderMapping = senderOrder.getReceiverOrderMapping();
                PickupOrderMapping pickupOrderMapping = senderOrder.getPickupOrderMapping();
                setPickupDetails(pickupOrderMapping);
                setRecieverDetails(receiverOrderMapping);
                toCancel = senderOrder.getOrder_id();


            }


            fromLoc.setText(senderOrder.getFrom_loc());
            toLoc.setText(senderOrder.getTo_loc());

        } catch (Exception e) {

            e.printStackTrace();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // com.ucarry.developer.android.activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //

//            Intent intent = null;//new Intent(this, CarrierDetailActivity.class);
//
//            intent = new Intent(this,MainActivity.class);
//            startActivity(intent);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void hidePickUpAndReciever() {

        llPickupParent = (LinearLayout) findViewById(R.id.pickup_parent_ll);
        llPickupParent.setVisibility(View.GONE);

        llRecieverParent = (LinearLayout) findViewById(R.id.reciever_parent_ll);
        llRecieverParent.setVisibility(View.GONE);

        subItemLL = (LinearLayout) findViewById(R.id.sub_item_ll);
        subItemLL.setVisibility(View.GONE);

        amountLL = (LinearLayout) findViewById(R.id.amount_ll);
        amountLL.setVisibility(View.GONE);


    }

    private void showPickUpAndReciever() {

        llPickupParent = (LinearLayout) findViewById(R.id.pickup_parent_ll);
        llPickupParent.setVisibility(View.VISIBLE);
        llSecondParentPickup = (LinearLayout) findViewById(R.id.pickup_second_parent_ll);
        llSecondParentPickup.setVisibility(View.GONE);
        registerPickup();

        llRecieverParent = (LinearLayout) findViewById(R.id.reciever_parent_ll);
        llRecieverParent.setVisibility(View.VISIBLE);
        llSecondParentReciever = (LinearLayout) findViewById(R.id.reciever_second_parent_ll);
        llSecondParentReciever.setVisibility(View.GONE);
        registerReciever();


        subItemLL = (LinearLayout) findViewById(R.id.sub_item_ll);
        subItemLL.setVisibility(View.VISIBLE);


    }


    private void registerReciever() {


        Button recieverButton = (Button) findViewById(R.id.reciever_details);
        recieverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSecondParentReciever.getVisibility() == View.VISIBLE) {
                    llSecondParentReciever.setVisibility(View.GONE);
                } else
                    llSecondParentReciever.setVisibility(View.VISIBLE);
            }
        });

    }


    private void registerPickup() {

        Button pickupButton = (Button) findViewById(R.id.pickup_details);
        pickupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSecondParentPickup.getVisibility() == View.VISIBLE) {
                    llSecondParentPickup.setVisibility(View.GONE);
                } else
                    llSecondParentPickup.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setPickupDetails(PickupOrderMapping pickupOrderMapping) {

        pickupName.setText(pickupOrderMapping.getName());
        pickupPhone.setText(pickupOrderMapping.getPhone());
        pickupAdd1.setText(pickupOrderMapping.getAddress_line_1());
        pickupAdd2.setText(pickupOrderMapping.getAddress_line_2());

    }

    private void setRecieverDetails(ReceiverOrderMapping receiverOrderMapping) {

        recieverName.setText(receiverOrderMapping.getName());
        recieverPhone.setText(receiverOrderMapping.getPhone_1());
        recieverAdd1.setText(receiverOrderMapping.getAddress_line_1());
        recieverAdd2.setText(receiverOrderMapping.getAddress_line_2());


    }

    private void showStatusChangeDialogue(View view, final String orderId) {

        Map<Integer, String> optionsMap = new HashMap<>();
        LayoutInflater li = LayoutInflater.from(view.getContext());
        View prompt = li.inflate(R.layout.change_status, null);

        String currStatus = null;
        if(senderOrder!=null) {

            currStatus = senderOrder.getStatus();
        }




         final String currentStatus = currStatus;
        if (nextOrder(currStatus).equalsIgnoreCase("completed")) {

            final Dialog dialog = new Dialog(MyBayActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.complete_order);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
            alertBuilder.setView(prompt);
            Button dialogButton = (Button) dialog.findViewById(R.id.btn_continue);

            final EditText pinText = (EditText) dialog.findViewById(R.id.pin);
            ImageView ivPop = (ImageView) dialog.findViewById(R.id.ivPop);
            ivPop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //dialog.dismiss();
                    final ProgressDialog pd = new ProgressDialog(MyBayActivity.this);
                    pd.setIndeterminate(true);
                    pd.setMessage("Updating ... ");
                    pd.show();

                    ApiInterface apiInterface = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);
                    OrderCompletion oc = new OrderCompletion();
                    oc.setOrderId(senderOrder.getOrderId());
                    oc.setPin(pinText.getText().toString());
                    Call<JSONObject> call = apiInterface.completeOrder(oc);

                    call.enqueue(new Callback<JSONObject>() {
                        @Override
                        public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                            pd.dismiss();
                            if(response.code()==200) {
                                dialog.dismiss();
                                fireUpdateEvent(nextOrder(currentStatus),senderOrder.getOrderId());

                            }

                            if(response.code()==400) {


                                try {
                                    Toast.makeText(MyBayActivity.this,"Wrong Pin , Please try again",Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JSONObject> call, Throwable t) {

                            if(pd.isShowing())
                                pd.dismiss();

                            Toast.makeText(MyBayActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                        }
                    });
                }
            });

            dialog.show();

        }


        else{

            final Dialog dialog = new Dialog(MyBayActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.change_status);
            TextView text = (TextView) dialog.findViewById(R.id.textView2);
            text.setText("Are you sure to change the status of the order to "+humanReadableStatus(nextOrder(currStatus)));

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
            alertBuilder.setView(prompt);
            Button dialogButton = (Button) dialog.findViewById(R.id.btn_continue);


            ImageView ivPop = (ImageView) dialog.findViewById(R.id.ivPop);
            ivPop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    fireUpdateEvent(nextOrder(currentStatus), senderOrder.getOrderId());
                }
            });

            dialog.show();

        }

    }


    private void fireUpdateEvent(String status, String orderId) {

        Log.d(TAG, status);

        final ProgressDialog pd = new ProgressDialog(MyBayActivity.this);
        pd.setIndeterminate(true);
        pd.setMessage("Updating ... ");
        pd.show();

        ApiInterface apiInterface = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();
        updateOrderRequest.setOrderId(orderId);
        updateOrderRequest.setStatus(status);
        Call<JSONObject> call = apiInterface.updateOrder(updateOrderRequest);

        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                pd.dismiss();
                if (response.code() == 200) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MyBayActivity.this);
                    builder.setMessage("Updated Successfully!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(MyBayActivity.this, MainActivity.class);
                                    startActivity(intent);

                                   // finish();



                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(alert.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

                if (pd.isShowing())
                    pd.dismiss();
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(senderOrder!=null) {
            if(senderOrder.getStatus().equals("completed")) {
                menu.clear();
                return true;
            }
        }
        MenuItem register = menu.findItem(R.id.edit_item);
        if (canCancel) {

            register.setEnabled(true);
        } else {
            register.setEnabled(false);
        }

        register.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MyBayActivity.this);
                builder.setMessage("Are you sure to cancel this?")
                        .setCancelable(false)
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                cancelOrder();


                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_with_edit, menu);
        return true;
    }


    private void cancelOrder() {

        final String orderId = toCancel;

        Log.d(TAG,orderId+":: to cancel");

        final ProgressDialog pd = new ProgressDialog(MyBayActivity.this);
        pd.setIndeterminate(true);
        pd.setMessage("Cancelling ... ");
        pd.show();

        ApiInterface apiInterface = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();
        updateOrderRequest.setOrderId(orderId);
        updateOrderRequest.setStatus("cancelled");
        Call<JSONObject> call = null;

        if(orderId.contains("ord"))
            call = apiInterface.cancelOrder(orderId);
            else
                call = apiInterface.cancelSchedule(orderId);


        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                pd.dismiss();
                if(200==response.code()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MyBayActivity.this);
                    builder.setMessage("Cancelled the event successfully!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(MyBayActivity.this, MainActivity.class);
                                    startActivity(intent);

                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

                if(pd.isShowing())
                    pd.dismiss();
            }
        });


    }

    private void prepareStatusChange() {

        if(senderOrder!=null) {

            String currentStatus = senderOrder.getStatus();
            switch (currentStatus) {

                case "scheduled":
                    changeStatusButton.setText("Pick Up");
                    break;
                case "pickedup":
                    changeStatusButton.setText("In Transit");
                    break;
                case "intransit":
                    changeStatusButton.setText("Complete Order");
                    break;



            }
        }



    }


    private String nextOrder(String currentOrder) {

        Log.d(TAG,"NExt order to return for "+currentOrder);
        String ret = null;
        switch (currentOrder) {
            case "scheduled":
                ret = "pickedup";
                break;
            case "pickedup":
                ret = "intransit";
                break;

            case "intransit":
                ret = "completed";
                break;

        }


        return ret!=null?ret:"Unknown";

    }

    private String humanReadableStatus(String status) {

        Log.d(TAG,"Status is "+status);
        String ret = null;
        switch(status) {

            case "scheduled":
                ret =  "Scheduled";
                break;

            case "pickedup":
                ret = "Picked Up";
                break;

            case "intransit":
                ret = "In Transit";
                break;

            case "complete":
                ret = "Completed";
                break;
        }

        return ret!=null?ret:"Unknown";
    }
}
