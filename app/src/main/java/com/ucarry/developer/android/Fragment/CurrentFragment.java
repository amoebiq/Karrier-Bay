package com.ucarry.developer.android.Fragment;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.FragmentCurrentBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import com.ucarry.developer.android.Adapter.CurrentAdapter;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentFragment extends Fragment {


    private FragmentCurrentBinding binding;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<SenderOrder> historyLists;
    private List<SenderOrder> historyLists1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_current, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = binding.recyclerViewHistory;
        preparenotificationData();
        //Toast.makeText(getActivity(),"Current com.ucarry.developer.android.Fragment",Toast.LENGTH_LONG).show();
    }

    private void preparenotificationData() {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setIndeterminate(true);
        pd.setMessage("Loading...");
        pd.show();

        Call<List<SenderOrder>> call = ((MainActivity)getActivity()).apiService.getSenderOrCarrierOrder("carrier", "schedules","true",null);
        call.enqueue(new Callback<List<SenderOrder>>() {
            @Override
            public void onResponse(Call<List<SenderOrder>> call, Response<List<SenderOrder>> response) {

                if(200==response.code()) {
                    pd.dismiss();
                    historyLists = response.body();
                    Gson gson = new Gson();

                    Log.d("currentresult",  gson.toJson(historyLists).toString());
//                    mAdapter = new CurrentAdapter(historyLists);
//                    mRecyclerView.setHasFixedSize(true);
//                    // use a linear layout manager
//                    mLayoutManager = new LinearLayoutManager(getActivity());
//                    mRecyclerView.setLayoutManager(mLayoutManager);
//                    mRecyclerView.setAdapter(mAdapter);
//                   // Toast.makeText(getActivity(), "No problem"+ historyLists.get(0).getCarrier_schedule_detail().getMode(), Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getActivity(), "Authentication problem", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SenderOrder>> call, Throwable t) {
                Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
            }
        });


        final List<SenderOrder> previousList = historyLists;
        Call<List<SenderOrder>> call1 = ((MainActivity)getActivity()).apiService.getSenderOrCarrierOrder("sender", "orders","true",null);
        call1.enqueue(new Callback<List<SenderOrder>>() {
            @Override
            public void onResponse(Call<List<SenderOrder>> call1, Response<List<SenderOrder>> response) {

                if(200==response.code()) {
                    pd.dismiss();
                    historyLists1 = response.body();

                    if(historyLists==null) {

                        historyLists = new ArrayList<SenderOrder>();
                    }

                    if(historyLists1==null) {

                        historyLists1 = new ArrayList<SenderOrder>();
                    }


                    Log.d("SIZE:::",""+historyLists.size());
                    List<SenderOrder> newList = new ArrayList<SenderOrder>(historyLists.size()+historyLists1.size());
                    newList.addAll(historyLists);
                    newList.addAll(historyLists1);

                    Gson gson = new Gson();

                    Log.d("currentressenderoder",  gson.toJson(newList).toString());
                    mAdapter = new CurrentAdapter(newList);
                    //mRecyclerView.setHasFixedSize(true);
                    // use a linear layout manager
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    // Toast.makeText(getActivity(), "No problem"+ historyLists.get(0).getCarrier_schedule_detail().getMode(), Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getActivity(), "Authentication problem", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SenderOrder>> call, Throwable t) {
                Toast.makeText(getActivity(), "Incorrect Request", Toast.LENGTH_LONG).show();
            }
        });

    }

}
