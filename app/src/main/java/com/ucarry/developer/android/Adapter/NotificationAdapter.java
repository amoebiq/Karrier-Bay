package com.ucarry.developer.android.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucarry.developer.android.Model.Notifications;
import com.ucarry.developer.android.Utilities.NotificationViewHolder;
import com.yourapp.developer.karrierbay.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ucarry.developer.android.Model.NotificationList;

/**
 * Created by skadavath on 1/27/2017.
 */
public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

private List<Notifications> notificationLists = new ArrayList<Notifications>();
    private static String TAG = NotificationAdapter.class.getName();

    public NotificationAdapter(List<Notifications> mnotificationLists) {

        this.notificationLists = mnotificationLists;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_row, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        NotificationViewHolder notificationViewHolder = (NotificationViewHolder) holder;

        notificationViewHolder.title.setText(getProperMessage(notificationLists.get(position).getMessage()));
        notificationViewHolder.time.setText(getTimeToDisplay(notificationLists.get(position)));

    }

    @Override
    public int getItemCount() {
        return notificationLists.size();
    }

    private String getProperMessage(String msg) {

        if(msg.contains("Please see")) {
            String m =  msg.substring(0,msg.indexOf("Please see"));
            Log.d("Return Message",m);
            return m;

        }
        else
        return msg;

    }

    private String getTimeToDisplay(Notifications notifications) {

        Log.d(TAG,notifications.getCreatedAt());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = new Date();


        try {
            Date d = dateFormat.parse(notifications.getCreatedAt());

            long diff = date.getTime() - d.getTime();
            int diffDays = (int) diff / (24 * 60 * 60 * 1000);
            System.out.println("DIFF IS "+diffDays);
            String diffFinal = "";
            if (diffDays > 0)
                diffFinal = diffDays + " days";
            else if (diffDays == 0) {

                diffDays = (int) diff / (60 * 60 * 1000);
                diffFinal = diffDays + " hours";

            }

            Log.d(TAG,diffFinal);

           return diffFinal;


        } catch (ParseException e) {
            e.printStackTrace();
        }

            return "";
    }
}
