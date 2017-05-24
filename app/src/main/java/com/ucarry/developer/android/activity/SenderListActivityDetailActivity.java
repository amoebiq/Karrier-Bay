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
 * An com.ucarry.developer.android.activity representing a single SenderListActivity detail screen. This
 * com.ucarry.developer.android.activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link SenderListActivityListActivity}.
 */
public class SenderListActivityDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senderlistactivity_detail);

        // Show the Up button in the action bar.

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);



        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>SENDER DETAILS</font>"));

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
            arguments.putString(SenderListActivityDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(SenderListActivityDetailFragment.ARG_ITEM_ID));

            arguments.putString(SenderListActivityDetailFragment.ORDER_ID,getIntent().getStringExtra(SenderListActivityDetailFragment.ORDER_ID));
            arguments.putString(SenderListActivityDetailFragment.USER_NAME,getIntent().getStringExtra(SenderListActivityDetailFragment.USER_NAME));
            arguments.putString(SenderListActivityDetailFragment.ADDRESS,getIntent().getStringExtra(SenderListActivityDetailFragment.ADDRESS));
            arguments.putString(SenderListActivityDetailFragment.IMAGE,getIntent().getStringExtra(SenderListActivityDetailFragment.IMAGE));
            arguments.putString(SenderListActivityDetailFragment.FROM_ADDRESS,getIntent().getStringExtra(SenderListActivityDetailFragment.FROM_ADDRESS));
            arguments.putString(SenderListActivityDetailFragment.TO_ADDRESS,getIntent().getStringExtra(SenderListActivityDetailFragment.TO_ADDRESS));
            arguments.putString(SenderListActivityDetailFragment.RATE,getIntent().getStringExtra(SenderListActivityDetailFragment.RATE));
            arguments.putString(SenderListActivityDetailFragment.CATEGORY,getIntent().getStringExtra(SenderListActivityDetailFragment.CATEGORY));
            arguments.putString(SenderListActivityDetailFragment.SUB_CATEGORY,getIntent().getStringExtra(SenderListActivityDetailFragment.SUB_CATEGORY));
            SenderListActivityDetailFragment fragment = new SenderListActivityDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.senderlistactivity_detail_container, fragment)
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
            navigateUpTo(new Intent(this, SenderListActivityListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
