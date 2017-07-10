package com.ucarry.developer.android.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.Utilities.CircleTransform;
import com.ucarry.developer.android.Utilities.Utility;
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

        ImageView iv = (ImageView) findViewById(R.id.profilepic);
        Log.d(TAG,user.getName());

        Picasso.Builder picBuilder = new Picasso.Builder(ProfileVIewActivity.this);
        picBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        picBuilder.build().load(Utility.getAwsUrl(user.getImage())).placeholder(R.drawable.carrier).transform(new CircleTransform()).error(R.drawable.carrier).into(iv);

        try {
            TextView nameTv = (TextView) findViewById(R.id.name_header);
            nameTv.setText(user.getName());

            TextView phoneText = (TextView) findViewById(R.id.phone_number_edittext);
            TextView emailText = (TextView) findViewById(R.id.email_edittext);
            String phone = user.getPhone();
            String email = user.getEmail();
            if(phone!=null)
            phoneText.setText(getStars(phone.length()-5)+phone.substring(phone.length()-5));
            if(email!=null)
            emailText.setText(email.substring(0,3)+getStars(email.length()-3));

        }
        catch (Exception e) {

            Toast.makeText(ProfileVIewActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
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

            Intent intent = new Intent(this, CarrierDetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getStars(int length) {

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++) {
            sb.append("*");
        }

        return sb.toString();
    }


}
