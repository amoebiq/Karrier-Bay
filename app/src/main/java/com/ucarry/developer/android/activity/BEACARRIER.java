package com.ucarry.developer.android.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import com.ucarry.developer.android.Model.CarrierScheduleDetail;
import com.ucarry.developer.android.Model.CarrierScheduleDetailAttributes;
import com.ucarry.developer.android.Model.CarrierSchedules;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Utilities.Utility;
import com.yourapp.developer.karrierbay.R;

import java.util.Calendar;
import java.util.Date;

public class BEACARRIER extends AppCompatActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static String TAG = "BE_A_CARRIER";
    private CarrierSchedules carrierSchedules = new CarrierSchedules();
    private CarrierScheduleDetailAttributes carrierScheduleDetailAttributes = new CarrierScheduleDetailAttributes();
    private boolean isFromLoc = false;
    private boolean isFromDate = false;
    private boolean isFromTime = false;
    private TextView fromLocTextView;
    private TextView toLocView;
    private EditText fromLocInput;
    private EditText toLocInput;
    private EditText depDateInput;
    private EditText arrDateInput;
    private EditText depTimeInput;
    private EditText arrTimeInput;


    private String tag;
    private static String ARTICLE = "ARTICLE";
    private static String PASSENGER = "PASSENGER";
    private static String fromDateData = null;
    private static String fromDataTime = null;
    private static String toDateData = null;
    private static String toDataTime = null;
    private static boolean isPassenger = false;
    Fragment fragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacarrier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.beacarrier_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Trip Schedule</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carrierSchedules.setCarrierScheduleDetailAttributes(carrierScheduleDetailAttributes);

        EditText et = (EditText) findViewById(R.id.carrier_passenger_capacityET);
        fromLocInput = (EditText) findViewById(R.id.carrier_schedule_from_edittext);
        toLocInput = (EditText) findViewById(R.id.becarrier_to_loc);
        depDateInput = (EditText) findViewById(R.id.etDEPDate);
        depTimeInput = (EditText) findViewById(R.id.depCarrierTime);
        arrDateInput = (EditText) findViewById(R.id.etToDate);
        arrTimeInput = (EditText) findViewById(R.id.arrCarrierTIme);

        et.setVisibility(View.GONE);

//        final Spinner litreSpinner = (Spinner) findViewById(R.id.carrier_litre_capacity);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.capacity,R.layout.support_simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        litreSpinner.setAdapter(adapter);
//
//        final Spinner passengerSpinner = (Spinner)findViewById(R.id.carrier_passenger_capacity);
//        ArrayAdapter<CharSequence> adapterPassenger = ArrayAdapter.createFromResource(this,R.array.passengers,R.layout.support_simple_spinner_dropdown_item);
//        adapterPassenger.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        passengerSpinner.setAdapter(adapterPassenger);
//        passengerSpinner.setVisibility(View.GONE);
        fromLocTextView = (TextView) findViewById(R.id.carrier_schedule_from_edittext);
        fromLocTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG,"Google");
                isFromLoc = true;

                openAutocompleteActivity();

            }
        });

        toLocView = (TextView) findViewById(R.id.becarrier_to_loc);
        toLocView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG,"Google");
                isFromLoc = false;

                openAutocompleteActivity();

            }
        });

        final EditText fromDpET = (EditText) findViewById(R.id.etDEPDate);
        fromDpET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"FROM DATE CLICKEDDDD");
                isFromDate = true;
                dateClick(view);

                Log.d(TAG,"SET::::"+fromDpET.getText().toString());
            }
        });
        TextView fromDp = (TextView) findViewById(R.id.depCarrierDate);
        fromDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"FROM DATE CLICKED");
                isFromDate = true;
                dateClick(view);

            }
        });

        TextView fromTp = (TextView) findViewById(R.id.depCarrierTime);
        fromTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromTime = true;
                timeClick(view);

            }
        });



        final CheckBox cbArticle = (CheckBox) findViewById(R.id.article_checkbox);
        //cbArticle.setChecked(true);
        final CheckBox cbPassenger = (CheckBox) findViewById(R.id.passenger_checkbox);

        cbArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cbArticle.isChecked()) {
                    isPassenger = false;
                    cbPassenger.setChecked(false);
//                    passengerSpinner.setVisibility(View.GONE);
//                    litreSpinner.setVisibility(View.VISIBLE);
                    carrierSchedules.getCarrierScheduleDetailAttributes().setReady_to_carry(ARTICLE);
                    EditText et = (EditText) findViewById(R.id.carrier_passenger_capacityET);
                    et.setVisibility(View.VISIBLE);
                    //et.append("");

                }

            }
        });

        cbPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbPassenger.isChecked()) {
                    isPassenger = true;
                    cbArticle.setChecked(false);
//                    litreSpinner.setVisibility(View.GONE);
//
//                    passengerSpinner.setVisibility(View.VISIBLE);
                    carrierSchedules.getCarrierScheduleDetailAttributes().setReady_to_carry(PASSENGER);
                    EditText et = (EditText) findViewById(R.id.carrier_passenger_capacityET);
                    et.setVisibility(View.VISIBLE);
                    //et.append("L");

                }
            }
        });

        Button nextButton = (Button) findViewById(R.id.beacarrier_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d(TAG, "Next button Clicked");

                if (validate()) {

                    Intent intent = new Intent(BEACARRIER.this, CarrierSummaryView.class);
                    fromDateData = ((EditText) findViewById(R.id.etDEPDate)).getText().toString();
                    fromDataTime = ((TextView) findViewById(R.id.depCarrierTime)).getText().toString();
                    toDateData = ((EditText) findViewById(R.id.etToDate)).getText().toString();
                    toDataTime = ((TextView) findViewById(R.id.arrCarrierTIme)).getText().toString();

                    carrierSchedules.getCarrierScheduleDetailAttributes().setStart_time(fromDateData + " " + fromDataTime);
                    carrierSchedules.getCarrierScheduleDetailAttributes().setEnd_time(toDateData + " " + toDataTime);

                    Log.d(TAG, fromDataTime + ":::" + fromDateData + ":::" + toDateData + ":::" + toDataTime);
                    EditText capacityET = (EditText) findViewById(R.id.carrier_passenger_capacityET);
                    String capacity = capacityET.getText().toString();
                    if (!isPassenger) {
                        capacity = capacity + "L";
                    }

                    carrierSchedules.getCarrierScheduleDetailAttributes().setCapacity(capacity);
                    intent.putExtra("CarrierSchedules", carrierSchedules);
                    startActivity(intent);


                }

            }
        });


    }


    private boolean checkAndReturn(String s) {

        if(s==null)
            return false;
        if(s.isEmpty())
            return false;

        return true;
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
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            navigateUpTo(new Intent(BEACARRIER.this, MainActivity.class));
                        }
                    });


            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void openAutocompleteActivity() {
        try {
            // The autocomplete com.ucarry.developer.android.activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
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

                    fromLocTextView.setText(place.getAddress().toString());
                    carrierSchedules.setFrom_loc(place.getAddress().toString());
                    carrierSchedules.setFrom_geo_lat(place.getLatLng().latitude+"");
                    carrierSchedules.setFrom_geo_lat(place.getLatLng().longitude+"");

                }
                else {

                    toLocView.setText(place.getAddress().toString());
                    carrierSchedules.setTo_loc(place.getAddress().toString());
                    carrierSchedules.setTo_geo_lat(place.getLatLng().latitude+"");
                    carrierSchedules.setTo_geo_long(place.getLatLng().longitude+"");

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

    public void dateClick(View view) {
        final TextView et = (TextView) view;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this,
                new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker arg0,
                                                  int arg1, int arg2, int arg3) {

                                et.setText(arg3 + "-" + (arg2 + 1) + "-" + arg1);

                                if(isFromDate) {

                                    Log.d(TAG,"ITS FROM DATE SO SETTING TO fromDAteDATA");

                                    fromDateData = arg3 + "-" + (arg2 + 1) + "-" + arg1;

                                }
                                else {
                                    Log.d(TAG,"ITS TO DATE SO SETTING TO fromDAteDATA");
                                    toDateData = arg3 + "-" + (arg2 + 1) + "-" + arg1;
                                }
                            }
                        }, year, month, day).show();



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

    public void fragment(Fragment fragment, String transaction) {
        tag = transaction;
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_transaction, fragment, transaction);
        fragmentTransaction.addToBackStack(transaction);
        fragmentTransaction.commit();
        Log.d("backFragment", tag);
    }


    private boolean validate() {

        toLocInput.setError(null);
        fromLocInput.setError(null);
        arrDateInput.setError(null);
        arrTimeInput.setError(null);
        depDateInput.setError(null);
        depTimeInput.setError(null);




        if(fromLocInput.getText().length()==0 || toLocInput.getText().length()==0 || depDateInput.getText().length()==0 || depTimeInput.getText().length()==0 || arrDateInput.getText().length()==0 || arrTimeInput.getText().length()==0) {




            if(fromLocInput.getText().length()==0)
                fromLocInput.setError("Provide Start Location");


            if(toLocInput.getText().length()==0)
                toLocInput.setError("Provide Destination Location");



            if(arrDateInput.getText().length()==0)
                arrDateInput.setError("Provide Arrival Date");



            if(arrTimeInput.getText().length()==0)
                arrTimeInput.setError("Provide Arrival Time");



            if(depDateInput.getText().length()==0)
                depDateInput.setError("Provide Departure Date");



            if(depTimeInput.getText().length()==0)
                depTimeInput.setError("Provide Departure Time");





            return false;

        }

        String fromDateStr = depDateInput.getText().toString()+" "+depTimeInput.getText().toString();
        String toDateStr = arrDateInput.getText().toString()+" "+arrTimeInput.getText().toString();
        try {
            Date start = Utility.getDateFromString(fromDateStr);
            Date end = Utility.getDateFromString(toDateStr);
            if (start.compareTo(end) > 0) {

                Toast.makeText(getApplicationContext(),Constants.FROM_TO_DATE_VALIDATION,Toast.LENGTH_LONG).show();
                return false;

            }

        }
        catch(Exception e) {

        }
        Log.d(TAG,fromDateStr);
        Log.d(TAG,toDateStr);





        return true;
    }

}
