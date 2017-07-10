package com.ucarry.developer.android.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.ucarry.developer.android.Model.User;
import com.yourapp.developer.karrierbay.R;

public class ProfileVIewActivity extends AppCompatActivity {

    public static final String TAG = "ProfileView";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Details</font>"));
        }

        User user = (User)getIntent().getSerializableExtra(CarrierDetailFragment.USER_OBJ);
        Log.d(TAG,user.getName());

    }
}
