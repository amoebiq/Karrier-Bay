package com.ucarry.developer.android.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yourapp.developer.karrierbay.R;

import java.util.List;

import com.ucarry.developer.android.Adapter.MyAdapter;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Utilities.Utility;
import com.ucarry.developer.android.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by skadavath on 4/14/17.
 */

public class SenderListFragment extends android.support.v4.app.Fragment {

    SenderOrder sender;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("SENDER_LIST","onCreateView of Sender");
        return inflater.inflate(R.layout.carrier_mainlayout, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);



        Log.d("SENDER_LIST","onViewCreated of Sender");
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setIndeterminate(true);
        pd.setMessage("Loading...");
        pd.show();

        //   RowCarrierListBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_carrier_list, container, false);
        sender = ((MainActivity) getActivity()).sender;
        Utility.hideKeyboard(getActivity());
        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        Call<List<SenderOrder>> call;

            ((MainActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Senders</font>"));
            call = ((MainActivity) getActivity()).apiService.getAllSenderCarrierList("sender", "orders","all");


        call.enqueue(new Callback<List<SenderOrder>>() {
            @Override
            public void onResponse(Call<List<SenderOrder>> call, Response<List<SenderOrder>> response) {

                if (response.code() == 200 && response.body() != null) {
                    pd.dismiss();
                    List<SenderOrder> list = response.body();
                    System.out.println("here getting response for senders");
                    Log.d("LoginResponse", response.message());
                    MyAdapter mAdapter = new MyAdapter(list);
                    mRecyclerView.setAdapter(mAdapter);




                } else {
                    Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SenderOrder>> call, Throwable t) {
                Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
            }
        });


    }
}