package com.ucarry.developer.android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.yourapp.developer.karrierbay.R;


import java.util.List;

import com.ucarry.developer.android.Adapter.SenderAdapter;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An com.ucarry.developer.android.activity representing a list of Items. This com.ucarry.developer.android.activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the com.ucarry.developer.android.activity presents a list of items, which when touched,
 * lead to a {@link SenderListActivityDetailActivity} representing
 * item details. On tablets, the com.ucarry.developer.android.activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class SenderListActivityListActivity extends AppCompatActivity {


    /**
     * Whether or not the com.ucarry.developer.android.activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    SenderAdapter senderAdapter;
    List<SenderOrder> senderOrderList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senderlistactivity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarsender);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Senders</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("SENDER_LIST","Setting title....");

        loadSenderList();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        View recyclerView = findViewById(R.id.senderlistactivity_list);
        assert recyclerView != null;
        //setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.senderlistactivity_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // com.ucarry.developer.android.activity should be in two-pane mode.
            mTwoPane = true;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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


    public void loadSenderList() {

        initViews();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setMessage("Loading Senders...");
        pd.show();

        Call<List<SenderOrder>> call;

        ApiInterface apiInterface = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);


        call = apiInterface.getAllSenderCarrierList("sender", "orders","all","","");

        call.enqueue(new Callback<List<SenderOrder>>() {
            @Override
            public void onResponse(Call<List<SenderOrder>> call, Response<List<SenderOrder>> response) {

                if (response.code() == 200 && response.body() != null) {
                    pd.dismiss();
                    List<SenderOrder> list = response.body();
                    //System.out.println("here getting response for senders");
                    Log.d("LoginResponse", response.message());
                   senderAdapter = new SenderAdapter(list);
                    recyclerView.setAdapter(senderAdapter);
//                    mRecyclerView.setAdapter(mAdapter);




                } else {
  //                  Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SenderOrder>> call, Throwable t) {
    //            Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initViews() {

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        //recyclerView.setHasFixedSize(true);
      //  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }


}
