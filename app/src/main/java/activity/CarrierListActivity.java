package activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.dummy.DummyContent;

import java.util.List;

import Adapter.CarrierAdapter;
import Adapter.SenderAdapter;
import Model.CarrierSchedules;
import Model.SenderOrder;
import RetroGit.ApiClient;
import RetroGit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CarrierDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CarrierListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    CarrierAdapter carrierAdapter;
    List<CarrierSchedules> carrierSchedulesList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcarrier);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>CARRIERS</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        View recyclerView = findViewById(R.id.carrier_list);
        assert recyclerView != null;


        if (findViewById(R.id.carrier_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        loadSenderList();
    }


    public void loadSenderList() {

        initViews();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setMessage("Loading Carriers...");
        pd.show();

        Call<List<CarrierSchedules>> call;

        ApiInterface apiInterface = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);


        call = apiInterface.getAllCarrierList("carrier", "schedules","all");

        call.enqueue(new Callback<List<CarrierSchedules>>() {
            @Override
            public void onResponse(Call<List<CarrierSchedules>> call, Response<List<CarrierSchedules>> response) {

                Log.d("CARRIER_LIST",response.code()+"");
                if (response.code() == 200 && response.body() != null) {
                    pd.dismiss();
                    List<CarrierSchedules> list = response.body();
                    System.out.println("here getting response for senders");
                    Log.d("LoginResponse", response.message());
                    carrierAdapter = new CarrierAdapter(list);
                    recyclerView.setAdapter(carrierAdapter);
//                    mRecyclerView.setAdapter(mAdapter);




                } else {
                    //                  Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<CarrierSchedules>> call, Throwable t) {
                pd.dismiss();
                Log.d("CARRIER_LIST",t.getLocalizedMessage()+"");
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
