package Fragment;

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

import Adapter.NotificationAdapter;
import Model.NotificationList;

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

        mAdapter = new NotificationAdapter(notificationLists);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_app);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void preparenotificationData() {
        NotificationList movie = new NotificationList("Nirmal wants to send his document from Chennai to Ooty on september 07", "3 minutes ago");
        notificationLists.add(movie);

        movie = new NotificationList("Nirmal wants to send his document from Chennai to Ooty on september 07", "3 minutes ago");
        notificationLists.add(movie);

    }

}
