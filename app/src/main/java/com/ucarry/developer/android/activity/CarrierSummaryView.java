package com.ucarry.developer.android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ucarry.developer.android.Model.CarrierScheduleRequest;
import com.ucarry.developer.android.Model.CarrierSchedules;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.yourapp.developer.karrierbay.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CarrierSummaryView extends AppCompatActivity {

    private CarrierSchedules carrierSchedules;
    private static String TAG = "CARRIER_SUMMARY_VIEW";
    public ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier_summary_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.carriersummaryviewtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Carrier Trip Schedule Summary</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carrierSchedules = (CarrierSchedules) getIntent().getSerializableExtra("CarrierSchedules");

        TextView tvFrom = (TextView) findViewById(R.id.csummaryfrom);
        tvFrom.setText(carrierSchedules.getFrom_loc());

        TextView tvTo = (TextView) findViewById(R.id.csummaryto);
        tvTo.setText(carrierSchedules.getTo_loc());

        TextView readToCarry = (TextView) findViewById(R.id.csummaryreadytocarry);
        readToCarry.setText(carrierSchedules.getCarrierScheduleDetailAttributes().getReady_to_carry());

        TextView start = (TextView) findViewById(R.id.csummaryfromdate);
        start.setText(carrierSchedules.getCarrierScheduleDetailAttributes().getStart_time());


        TextView end = (TextView) findViewById(R.id.csummarytodate);
        end.setText(carrierSchedules.getCarrierScheduleDetailAttributes().getEnd_time());


        Button proceed = (Button) findViewById(R.id.proceedbeacarrier);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG,"PROCEEDING");

                Gson gson = new Gson();






                Log.d(TAG , gson.toJson(carrierSchedules));

                CarrierScheduleRequest csr = new CarrierScheduleRequest();
                csr.setCarrierSchedules(carrierSchedules);

                final ProgressDialog pd = new ProgressDialog(CarrierSummaryView.this);
                pd.setIndeterminate(true);
                pd.setMessage(Constants.WAIT_MESSAGE);
                pd.show();
                apiService = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);
                Call<CarrierScheduleRequest> call = apiService.createSchedule(csr);

                call.enqueue(new Callback<CarrierScheduleRequest>() {
                    @Override
                    public void onResponse(Call<CarrierScheduleRequest> call, Response<CarrierScheduleRequest> response) {

                        Log.d(TAG,response.code()+"");
                        pd.dismiss();
                        if(response.code()==201) {

                            Toast.makeText(getApplicationContext(),"Successfully Created the schedule",Toast.LENGTH_LONG);
                            Intent intent = new Intent(CarrierSummaryView.this , SenderListActivityListActivity.class);
                            startActivity(intent);

                        }

                    }

                    @Override
                    public void onFailure(Call<CarrierScheduleRequest> call, Throwable t) {

                        Log.d(TAG,t.getLocalizedMessage()+"");

                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);

                    }
                });


            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, BEACARRIER.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
