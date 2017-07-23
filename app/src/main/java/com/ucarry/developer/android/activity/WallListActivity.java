package com.ucarry.developer.android.activity;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ucarry.developer.android.Adapter.NotificationAdapter;
import com.ucarry.developer.android.Model.Notifications;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.yourapp.developer.karrierbay.R;

import com.ucarry.developer.android.activity.dummy.DummyContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class WallListActivity extends AppCompatActivity {

    private static final String TAG = WallListActivity.class.getName();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>My Wall</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        loadNotifications();



    }


    private void loadNotifications() {

        Log.d(TAG,"Loading Notifications");
        final ProgressDialog pd = new ProgressDialog(WallListActivity.this);
        pd.setIndeterminate(true);
        pd.setMessage("Loading Notifications");
        pd.show();


        Call<List<Notifications>> call;

        ApiInterface apiInterface = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);

        call = apiInterface.getNotifications();

        call.enqueue(new Callback<List<Notifications>>() {
            @Override
            public void onResponse(Call<List<Notifications>> call, Response<List<Notifications>> response) {
                pd.dismiss();
                if(response.code()==200 || response.code()==201) {

                    List<Notifications> notifications = response.body();
                    NotificationAdapter notificationAdapter = new NotificationAdapter(notifications);
                    recyclerView.setAdapter(notificationAdapter);

                }

                else {

                    Toast.makeText(WallListActivity.this,"Error Loading Notifications", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Notifications>> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                Toast.makeText(WallListActivity.this,"Error Loading Notifications", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void initViews() {

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        //recyclerView.setHasFixedSize(true);
        //  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


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

}
