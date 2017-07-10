package com.ucarry.developer.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.yourapp.developer.karrierbay.R;

/**
 * An com.ucarry.developer.android.activity representing a single Carrier detail screen. This
 * com.ucarry.developer.android.activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CarrierListActivity}.
 */
public class CarrierDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>CARRIER DETAILS</font>"));
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this com.ucarry.developer.android.activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the com.ucarry.developer.android.activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CarrierDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(CarrierDetailFragment.ARG_ITEM_ID));
            arguments.putString(CarrierDetailFragment.USER_NAME,getIntent().getStringExtra(CarrierDetailFragment.USER_NAME));
            arguments.putString(CarrierDetailFragment.ADDRESS,getIntent().getStringExtra(CarrierDetailFragment.ADDRESS));
            arguments.putString(CarrierDetailFragment.IMAGE,getIntent().getStringExtra(CarrierDetailFragment.IMAGE));
            arguments.putString(CarrierDetailFragment.FROM_ADDRESS,getIntent().getStringExtra(CarrierDetailFragment.FROM_ADDRESS));
            arguments.putString(CarrierDetailFragment.TO_ADDRESS,getIntent().getStringExtra(CarrierDetailFragment.TO_ADDRESS));

            arguments.putString(CarrierDetailFragment.READY_TO_CARRY,getIntent().getStringExtra(CarrierDetailFragment.READY_TO_CARRY));
            arguments.putString(CarrierDetailFragment.CAPACITY,getIntent().getStringExtra(CarrierDetailFragment.CAPACITY));
            arguments.putString(CarrierDetailFragment.DATE_FROM,getIntent().getStringExtra(CarrierDetailFragment.DATE_FROM));
            arguments.putString(CarrierDetailFragment.DATE_TO,getIntent().getStringExtra(CarrierDetailFragment.DATE_TO));
            arguments.putString(CarrierDetailFragment.SCHEDULE_ID,getIntent().getStringExtra(CarrierDetailFragment.SCHEDULE_ID));
            arguments.putSerializable(CarrierDetailFragment.USER_OBJ,getIntent().getSerializableExtra(CarrierDetailFragment.USER_OBJ));
            CarrierDetailFragment fragment = new CarrierDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.carrier_detail_container, fragment)
                    .commit();
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
            navigateUpTo(new Intent(this, CarrierListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
