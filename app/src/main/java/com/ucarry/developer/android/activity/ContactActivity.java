package com.ucarry.developer.android.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ucarry.developer.android.Model.ContactUs;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.SessionManager;
import com.yourapp.developer.karrierbay.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {

    private static final String TAG = ContactActivity.class.getName();
    Spinner issueSpinner;
    LinearLayout otherIssueLayout;
    EditText contactName;
    EditText contactNumber;
    EditText otherContactIssue;
    SessionManager sessionManager ;
    Button submitIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Contact Us</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        issueSpinner = (Spinner) findViewById(R.id.issue_spinner);
        otherIssueLayout = (LinearLayout) findViewById(R.id.other_issue_layout);
        submitIssue = (Button) findViewById(R.id.submit_issue);
        contactName = (EditText) findViewById(R.id.contact_name);
        contactNumber = (EditText) findViewById(R.id.contact_me_at);
        otherContactIssue = (EditText) findViewById(R.id.other_issue);

        otherIssueLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(ContactActivity.this,R.array.issue_list, android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issueSpinner.setAdapter(arrayAdapter);

        try {

            sessionManager = new SessionManager(getApplicationContext());
            contactName.setText(sessionManager.getvalStr(SessionManager.KEY_NAME));
            contactNumber.setText(sessionManager.getvalStr(SessionManager.KEY_PHONE));
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        issueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String issue = issueSpinner.getSelectedItem().toString();
                if(issue.equalsIgnoreCase("other")) {
                    otherIssueLayout.setVisibility(View.VISIBLE);
                }
                else {
                    otherIssueLayout.setVisibility(View.GONE);
                }
                Log.d(TAG,issue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submitIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContactUs contactUs = new ContactUs();
                if(validate()) {
                    ApiInterface apiInterface = ApiClient.getClientWithHeader(ContactActivity.this).create(ApiInterface.class);
                    contactUs.setName(contactName.getText().toString());
                    contactUs.setNumber(contactNumber.getText().toString());
                    if(issueSpinner.getSelectedItem().toString().equalsIgnoreCase("other")) {

                        contactUs.setIssue(otherContactIssue.getText().toString());
                    }
                    else {
                        contactUs.setIssue(issueSpinner.getSelectedItem().toString());
                    }
                    Call<JSONObject> call = apiInterface.support(contactUs);
                    final ProgressDialog pd = new ProgressDialog(ContactActivity.this);
                    pd.setIndeterminate(true);
                    pd.setMessage("Please wait...");
                    pd.show();

                    call.enqueue(new Callback<JSONObject>() {
                        @Override
                        public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                            pd.dismiss();
                            if(response.code()==200) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(ContactActivity.this);
                                builder.setMessage("Query submitted successfully . We will contact you soon")
                                        .setCancelable(false)

                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {


                                                Intent intent = new Intent(ContactActivity.this, MainActivity.class);
                                                startActivity(intent);


                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.show();
                                alert.getButton(alert.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));

                            }
                            else {

                                Toast.makeText(ContactActivity.this,"Some issue contacting server "+response.code(),Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<JSONObject> call, Throwable t) {
                            if(pd.isShowing())
                                pd.dismiss();

                            Toast.makeText(ContactActivity.this,"Some issue contacting server",Toast.LENGTH_LONG).show();
                            t.printStackTrace();

                        }
                    });

                }
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG,"Back");

        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }


    private boolean validate() {

        if(contactName.getText().toString().isEmpty() || contactNumber.toString().isEmpty()) {
            return false;
        }


        if(issueSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Issue")) {
            Toast.makeText(ContactActivity.this,"Please select the issue",Toast.LENGTH_LONG).show();
            return false;

        }

        return true;
    }




}
