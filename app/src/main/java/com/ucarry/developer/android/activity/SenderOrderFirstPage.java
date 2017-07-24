package com.ucarry.developer.android.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.text.Line;
import com.ucarry.developer.android.Fragment.SenderTripScheduleFragment;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Model.ItemAttributes;
import com.ucarry.developer.android.Model.QuoteRequest;
import com.ucarry.developer.android.Model.QuoteResponse;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Model.SenderOrderItemAttributes;
import com.ucarry.developer.android.Model.SenderOrderResponse;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.Utility;
import com.yourapp.developer.karrierbay.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SenderOrderFirstPage extends AppCompatActivity {

    private LinearLayout orderPassengerlayout ;
    private LinearLayout orderSubcategoryLayout;
    private LinearLayout ordervolumetricHeader;
    private LinearLayout ordervolumetricetsLayout;
    private LinearLayout orderSubcategoryETLayout;
    private EditText orderWeightEt;
    private EditText fromLoc;
    private EditText toLoc;
    private EditText depDate;
    private EditText depTime;
    private EditText arrDate;
    private EditText arrTime;

    private EditText heightET;
    private EditText breadthET;
    private EditText lengthET;

    private boolean isFromLoc = false;
    private boolean isFromDate = false;
    private boolean isFromTime = false;


    private boolean passenger=false;

    private static String ARTICLE = "ARTICLE";
    private static String PASSENGER = "PASSENGER";
    private static String ITEM_SUB_TYPE = "Electronic Item";
    private static String fromDateData = null;
    private static String fromDataTime = null;
    private static String toDateData = null;
    private static String toDataTime = null;
    private static boolean isPassenger = false;

    private double lat1;
    private double lat2;
    private double long1;
    private double llong2;

    private SenderOrder order = new SenderOrder();
    private SenderOrderItemAttributes orderItems = new SenderOrderItemAttributes();
    private ItemAttributes itemAttributes = new ItemAttributes();

    private Button nextButton;

    QuoteRequest qr = new QuoteRequest();

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    private static String TAG = "SENDERORD1PAGE";

    public ApiInterface apiService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_order_first_page);



        Toolbar toolbar = (Toolbar) findViewById(R.id.beasender_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Order Schedule</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setItemSpinner();
        setSubItemSpinner();

        getBinding();

        //Utility.hideKeyboard(SenderOrderFirstPage.this);

        orderItems.setItem_type(ARTICLE);
        orderItems.setItem_subtype(ITEM_SUB_TYPE);

        orderPassengerlayout.setVisibility(View.GONE);

        depDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromDate = true;
                dateClick(view);
            }
        });

        arrDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromDate = false;
                dateClick(view);
            }
        });

        fromLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromLoc = true;
                openAutocompleteActivity();
            }
        });

        toLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromLoc = false;
                openAutocompleteActivity();
            }
        });


//        orderWeightEt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG,"Clicked");
//
//            }
//        });

        nextButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {



                if(validate()) {

                    //dummyFeed();
                    Log.d(TAG,"Next clicked");

                    apiService = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);


                    Call<QuoteResponse> call = apiService.getQuote(qr);

                    fromDateData = depDate.getText().toString();
                    fromDataTime = depTime.getText().toString();
                    toDateData = arrDate.getText().toString();
                    toDataTime = arrTime.getText().toString();



                    final ProgressDialog pd = new ProgressDialog(SenderOrderFirstPage.this);
                    pd.setIndeterminate(true);
                    pd.setMessage(Constants.LOADING_MESSAGE);
                    pd.show();

                    ArrayList<SenderOrderItemAttributes> list = new ArrayList<SenderOrderItemAttributes>();
                    list.add(orderItems);

                    orderItems.setStart_time(fromDateData+" "+fromDataTime);
                    orderItems.setEnd_time(toDateData+" "+toDataTime);

                    order.setOrder_items(list);


                    call.enqueue(new Callback<QuoteResponse>() {
                        @Override
                        public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {

                            pd.dismiss();
                            if(response.code()==200 || response.code()==201) {

                                final QuoteResponse quoteResponse = ((QuoteResponse) response.body());
                                final Dialog dialog = new Dialog(SenderOrderFirstPage.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.quote_popup);


                                TextView text = (TextView) dialog.findViewById(R.id.textView2);
                                text.setText("The approximate charge for your courier is RS." + Math.ceil(Double.parseDouble(quoteResponse.quote.getGrand_total())) + " The prices may be vary according to the exact " +
                                        "pick up and delivery points");

                                Button dialogButton = (Button) dialog.findViewById(R.id.btn_continue);
                                ImageView ivPop = (ImageView) dialog.findViewById(R.id.ivPop);

                                // if button is clicked, close the custom dialog
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {



                                        Intent intent = new Intent(SenderOrderFirstPage.this,SenderOrderSecondPage.class);
                                        intent.putExtra("SenderOrder",order);
                                        intent.putExtra("Quote",quoteResponse.quote);
                                        startActivity(intent);
                                        dialog.dismiss();

//                                        GoogleDirection.withServerKey("AIzaSyBw3tDakHnjfI2RjQnN_AQShOS63IDW4rE")
//                                                .from(new LatLng(Double.parseDouble(qr.getLat1()),Double.parseDouble(qr.getLong1())))
//                                                .to(new LatLng(Double.parseDouble(qr.getLat2()),Double.parseDouble(qr.getLong2())))
//                                                .avoid(AvoidType.FERRIES)
//                                                .avoid(AvoidType.HIGHWAYS)
//                                                .execute(new DirectionCallback() {
//                                                    @Override
//                                                    public void onDirectionSuccess(Direction direction, String rawBody) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onDirectionFailure(Throwable t) {
//
//                                                    }
//                                                });


//                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                                                Uri.parse("http://maps.google.com/maps?saddr="+qr.getLat1()+","+qr.getLong1()+"&daddr="+qr.getLat2()+","+qr.getLong2()));
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                                        intent.addCategory(Intent.CATEGORY_LAUNCHER );
//                                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                                        startActivity(intent);
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


                                Log.d("LoginResponse", response.message());

                            }

                        }

                        @Override
                        public void onFailure(Call<QuoteResponse> call, Throwable t) {

                            if(pd.isShowing())
                                pd.dismiss();

                        }
                    });

                }
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(Constants.CONFIRM_BACK_NAVIGATION)
                    .setCancelable(false)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            navigateUpTo(new Intent(SenderOrderFirstPage.this, MainActivity.class));
                        }
                    });


            AlertDialog alert = builder.create();

            alert.show();
            alert.getButton(alert.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            alert.getButton(alert.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setItemSpinner() {


        final Spinner itemSpinner = (Spinner) findViewById(R.id.sender_order_itemlist);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.wanttosend,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String item = itemSpinner.getItemAtPosition(i).toString();
                orderItems.setItem_type(item);
                if(item.equalsIgnoreCase(PASSENGER)) {

                    passenger = true;
                    hideArticles();
                    showPassenger();

                }
                if(item.equalsIgnoreCase(ARTICLE)) {

                    passenger = false;
                    hidePassenger();
                    showArticles();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void hideArticles() {

        ordervolumetricHeader.setVisibility(View.GONE);
        ordervolumetricetsLayout.setVisibility(View.GONE);
        orderSubcategoryLayout.setVisibility(View.GONE);
        orderSubcategoryETLayout.setVisibility(View.GONE);

    }

    private void hidePassenger() {

        orderPassengerlayout.setVisibility(View.GONE);

    }

    private void showArticles() {

        ordervolumetricHeader.setVisibility(View.VISIBLE);
        ordervolumetricetsLayout.setVisibility(View.VISIBLE);
        orderSubcategoryLayout.setVisibility(View.VISIBLE);
        orderSubcategoryETLayout.setVisibility(View.VISIBLE);

    }

    private void showPassenger() {

        orderPassengerlayout.setVisibility(View.VISIBLE);

    }

    private void setSubItemSpinner() {

        final Spinner subItemSpinner = (Spinner) findViewById(R.id.sender_order_itemsublist_article);
        ArrayAdapter<CharSequence> subAdapter = ArrayAdapter.createFromResource(this,R.array.article_subcategory,R.layout.support_simple_spinner_dropdown_item);
        subAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        subItemSpinner.setAdapter(subAdapter);

        subItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSubType = subItemSpinner.getItemAtPosition(i).toString();
                orderItems.setItem_subtype(itemSubType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }


    private void getBinding() {

        orderPassengerlayout = (LinearLayout)findViewById(R.id.orderspassengerlayout);
        orderSubcategoryLayout = (LinearLayout) findViewById(R.id.ordersubcategorylayout);
        ordervolumetricHeader = (LinearLayout) findViewById(R.id.ordervolumetricheader);
        ordervolumetricetsLayout = (LinearLayout) findViewById(R.id.ordervolumetricetslayout);
        orderSubcategoryETLayout = (LinearLayout) findViewById(R.id.ordersubcategoryetlayout);

        orderWeightEt = (EditText) findViewById(R.id.orderweightet);

        depDate = (EditText) findViewById(R.id.sender_order_et_dep_date);
        depTime = (EditText) findViewById(R.id.sender_order_et_dep_time);
        arrDate = (EditText) findViewById(R.id.sender_order_et_arr_date);
        arrTime = (EditText) findViewById(R.id.sender_order_et_arr_time);

        fromLoc = (EditText) findViewById(R.id.sender_order_from_et);

        toLoc = (EditText) findViewById(R.id.sender_order_to);

        nextButton = (Button) findViewById(R.id.beasender_next);

        lengthET = (EditText) findViewById(R.id.orderheighttet);
        breadthET = (EditText) findViewById(R.id.orderbreadthtet);
        heightET = (EditText) findViewById(R.id.orderheighttet);





    }


    public void dateClick(View view) {
        final TextView et = (TextView) view;


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this,
                new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker arg0,
                                                  int arg1, int arg2, int arg3) {

                                et.setText(arg3 + "-" + (arg2 + 1) + "-" + arg1);

                                if(et==arrDate) {

                                    Log.d(TAG,"Arrival Date Clicked");
                                    timeClick(false);
                                }
                                else {
                                    Log.d(TAG,"Departure Date Clicked");
                                    timeClick(true);
                                }


                                if(isFromDate) {

                                    Log.d(TAG,"ITS FROM DATE SO SETTING TO fromDAteDATA");

                                    fromDateData = arg3 + "-" + (arg2 + 1) + "-" + arg1;

                                }
                                else {
                                    Log.d(TAG,"ITS TO DATE SO SETTING TO fromDAteDATA");
                                    toDateData = arg3 + "-" + (arg2 + 1) + "-" + arg1;
                                }
                            }
                        }, year, month, day);

        dpd.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        dpd.show();

        isFromDate = false;
    }

    public void timeClick(View view) {

        Log.d(TAG,"time Clicked");
        final EditText et = (EditText) view;
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                et.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void timeClick(boolean isTo) {

        Log.d(TAG,"time Clicked");
        final EditText et;
        if(isTo)
            et = depTime;
        else
            et = arrTime;


        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                et.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");

        mTimePicker.show();
    }

    private void openAutocompleteActivity() {

        orderWeightEt.setFocusable(true);
        orderWeightEt.setFocusableInTouchMode(true);
        try {
            // The autocomplete com.ucarry.developer.android.activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.

            AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(Place.TYPE_COUNTRY)
                    .setCountry("IN")
                    .build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(autocompleteFilter)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d(TAG,"In Exception....");
            Log.d(TAG,e.getLocalizedMessage());
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if(isFromLoc) {

                    fromLoc.setText(place.getAddress().toString());
//                    carrierSchedules.setFrom_loc(place.getAddress().toString());
//                    carrierSchedules.setFrom_geo_lat(place.getLatLng().latitude+"");
//                    carrierSchedules.setFrom_geo_lat(place.getLatLng().longitude+"");

                    order.setFrom_geo_lat(place.getLatLng().latitude+"");
                    order.setFrom_geo_long(place.getLatLng().longitude+"");
                    order.setFrom_loc(place.getAddress().toString());


                }
                else {

                    toLoc.setText(place.getAddress().toString());
//                    carrierSchedules.setTo_loc(place.getAddress().toString());
//                    carrierSchedules.setTo_geo_lat(place.getLatLng().latitude+"");
//                    carrierSchedules.setTo_geo_long(place.getLatLng().longitude+"");

                    order.setTo_geo_long(place.getLatLng().longitude+"");
                    order.setTo_geo_lat(place.getLatLng().latitude+"");
                    order.setTo_loc(place.getAddress().toString());

                }

                Log.i(TAG, "Place: " + place.getName() + place.getLatLng() + place.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("testing", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    private boolean validate() {


        boolean invalid=false;
        if(!isPassenger && orderWeightEt.getText().length()==0) {

            orderWeightEt.setError("Please Enter Weight");
            invalid = true;


        }

        if(fromLoc.getText().length()==0 || toLoc.getText().length()==0 || depDate.getText().length()==0 || arrDate.getText().length()==0 || depTime.getText().length()==0 || arrTime.getText().length()==0 || lengthET.getText().length()==0 || breadthET.getText().length()==0 || heightET.getText().length()==0 || invalid) {

            if(fromLoc.getText().length()==0) {
                fromLoc.setError("Please fill from location");
            }
            if(toLoc.getText().length()==0) {

                toLoc.setError("Please fill to location");

            }


            if(depDate.getText().length()==0) {
                depDate.setError("Please fill from Date");
            }
            if(arrDate.getText().length()==0) {

                arrDate.setError("Please fill arrival date");


            }

            if(lengthET.getText().length()==0) {
                lengthET.setError("Please Enter length in cm");
            }

            if(breadthET.getText().length()==0) {
                breadthET.setError("Please Enter breadth in cm");
            }

            if(heightET.getText().length()==0) {
                heightET.setError("Please Enter height in cm");
            }

            return false;


        }

        qr.setHeight(heightET.getText().toString());
        qr.setBreadth(breadthET.getText().toString());
        qr.setLength(lengthET.getText().toString());
        qr.setItem_weight(orderWeightEt.getText().toString());

        qr.setLat1(order.getFrom_geo_lat());
        qr.setLong1(order.getFrom_geo_long());
        qr.setLat2(order.getTo_geo_lat());
        qr.setLong2(order.getTo_geo_long());

        order.setRef_1(orderWeightEt.getText().toString());

        itemAttributes.setBreadth(qr.getBreadth());
        itemAttributes.setLength(qr.getLength());
        itemAttributes.setHeight(qr.getLength());
        itemAttributes.setWeight(order.getRef_1());

        orderItems.setItem_attributes(itemAttributes);

        orderItems.setQuantity(1);





        return true;
    }


    public void dummyFeed() {

        qr.setLat1("8.524139100000001");
        qr.setLong2("74.9851678");
        qr.setLong1("76.9366376");
        qr.setLat2("12.5102239");
        qr.setLength("12");
        qr.setBreadth("12");
        qr.setHeight("12");

    }



}
