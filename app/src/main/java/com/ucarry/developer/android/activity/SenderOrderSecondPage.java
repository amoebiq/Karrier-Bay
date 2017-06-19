package com.ucarry.developer.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ucarry.developer.android.Model.PickupOrderMapping;
import com.ucarry.developer.android.Model.Quote;
import com.ucarry.developer.android.Model.ReceiverOrderMapping;
import com.ucarry.developer.android.Model.SenderOrder;
import com.yourapp.developer.karrierbay.R;

public class SenderOrderSecondPage extends AppCompatActivity {

    private EditText pickupName;
    private EditText etPickMobile;
    private EditText etFlatNo;
    private EditText etFlatName;
    private EditText etPckAddress;
    private EditText etDeliveryName;
    private EditText etDeliveyMobile;
    private EditText etDeliveyFlatNo;
    private EditText etDeliveryFlatName;
    private EditText etDeliAddress;
    private Button btn_sender_next;
    private SenderOrder order;
    private Quote quote;
    private PickupOrderMapping pickupOrderMapping = new PickupOrderMapping();
    private ReceiverOrderMapping receiverOrderMapping = new ReceiverOrderMapping();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_order_second_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Order Reciever Details</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);

        getBindings();
        order = (SenderOrder) getIntent().getSerializableExtra("SenderOrder");
        quote = (Quote) getIntent().getSerializableExtra("Quote");
        initialize();
        dummyData();

        btn_sender_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()) {

                    order.setPickupOrderMapping(pickupOrderMapping);

                    Intent intent = new Intent(SenderOrderSecondPage.this,SenderOrderSummary.class);
                    intent.putExtra("SenderOrder",order);
                    intent.putExtra("Quote",quote);
                    startActivity(intent);
                }

            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, SenderOrderFirstPage.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getBindings() {

        pickupName = (EditText) findViewById(R.id.etPickName);
        etPickMobile = (EditText) findViewById(R.id.etPickMobile);
        etFlatNo = (EditText) findViewById(R.id.etFlatNo);
        etFlatName = (EditText) findViewById(R.id.etFlatName);
        etPckAddress = (EditText) findViewById(R.id.etPckAddress);
        etDeliveryName = (EditText) findViewById(R.id.etDeliveryName);
        etDeliveyMobile = (EditText) findViewById(R.id.etDeliveyMobile);
        etDeliveyFlatNo = (EditText) findViewById(R.id.etDeliveyFlatNo);
        etDeliveryFlatName = (EditText) findViewById(R.id.etDeliveryFlatName);
        etDeliAddress = (EditText) findViewById(R.id.etDeliAddress);
        btn_sender_next = (Button) findViewById(R.id.btn_sender_next);


    }


    private boolean validate() {

        if(pickupName.getText().length()==0 || etPickMobile.getText().length()==0 || etPickMobile.getText().length()!=10 || etDeliveyMobile.getText().length()==0 || etDeliveyMobile.getText().length()!=10) {

            if(pickupName.getText().length()==0) {

                pickupName.setError("Please enter valid Name");
            }


            if(etPickMobile.getText().length()==0 || etPickMobile.getText().length()!=10) {
                etPickMobile.setError("Please enter valid 10 digit phone");
            }


            if(etDeliveyMobile.getText().length()==0 || etDeliveyMobile.getText().length()!=10) {
                etDeliveyMobile.setError("Please enter valid 10 digit phone");
            }

            return false;



        }


        pickupOrderMapping.setName(pickupName.getText().toString());
        pickupOrderMapping.setPhone_1(etPickMobile.getText().toString());
        pickupOrderMapping.setAddress_line_1(etFlatName.getText().toString()+","+etFlatNo.getText().toString());
        pickupOrderMapping.setAddress_line_2(etPckAddress.getText().toString());

        return true;

    }

    private void initialize() {

        etPckAddress.setText(order.getFrom_loc());
        etDeliAddress.setText(order.getTo_loc());



    }


    private void dummyData() {

        pickupName.setText("Runcy");
        etDeliveryName.setText("Nikhil M.K");
        etPickMobile.setText("9895123441");
        etDeliveyMobile.setText("9008901211");



    }

}
