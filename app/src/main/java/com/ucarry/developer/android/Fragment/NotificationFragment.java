package com.ucarry.developer.android.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourapp.developer.karrierbay.R;

import java.util.ArrayList;
import java.util.List;

import com.ucarry.developer.android.Adapter.NotificationAdapter;
import com.ucarry.developer.android.Model.NotificationList;

public class NotificationFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<NotificationList> notificationLists = new ArrayList<>();;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preparenotificationData();


    }

    private void preparenotificationData() {

    }

}
