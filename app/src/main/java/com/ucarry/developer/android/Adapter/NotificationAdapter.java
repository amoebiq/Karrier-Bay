package com.ucarry.developer.android.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucarry.developer.android.Model.Notifications;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Model.SenderOrderItemAttributes;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.NotificationViewHolder;
import com.ucarry.developer.android.activity.ContactActivity;
import com.ucarry.developer.android.activity.NotificationSenderFlow;
import com.ucarry.developer.android.activity.SenderListActivityDetailActivity;
import com.ucarry.developer.android.activity.SenderListActivityDetailFragment;
import com.yourapp.developer.karrierbay.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ucarry.developer.android.Model.NotificationList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final NotificationViewHolder notificationViewHolder = (NotificationViewHolder) holder;

        String message = notificationLists.get(position).getMessage();
        String time = getTimeToDisplay(notificationLists.get(position));
        String ref1 = notificationLists.get(position).getRef_1();
        Log.d(TAG,ref1!=null?ref1:"REFFF"+"XXXX");
        if(ref1!=null) {
            notificationViewHolder.knowMore.setVisibility(View.VISIBLE);
        }
        else {
            notificationViewHolder.knowMore.setVisibility(View.GONE);
        }
        final String notifType = notificationLists.get(position).getNotifType();
        notificationViewHolder.title.setText(getProperMessage(message));
        notificationViewHolder.time.setText(time);


        notificationViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.d(TAG,notifType);
                if(notifType.equalsIgnoreCase("NOTIFY_CARRIER")) {

                    Log.d(TAG,notifType+"XXX");

                    final ProgressDialog pd = new ProgressDialog(view.getContext());
                    pd.setIndeterminate(true);
                    pd.setMessage("Loading...");
                    pd.show();

                    ApiInterface apiInterface = ApiClient.getClientWithHeader(view.getContext()).create(ApiInterface.class);

                    Call<SenderOrder> call = apiInterface.getOrderDetails(notificationLists.get(position).getRef_1());

                    call.enqueue(new Callback<SenderOrder>() {
                        @Override
                        public void onResponse(Call<SenderOrder> call, Response<SenderOrder> response) {
                            pd.dismiss();
                            if(response.code()==200) {

                                SenderOrder senderOrder = response.body();

                                Context context = view.getContext();
                                Intent intent = new Intent(context, NotificationSenderFlow.class);
                                intent = getExtraIntents(intent,senderOrder);
                                context.startActivity(intent);



                            }
                        }

                        @Override
                        public void onFailure(Call<SenderOrder> call, Throwable t) {
                            if(pd.isShowing())
                                pd.dismiss();
                            t.printStackTrace();

                        }
                    });

                }

            }
        });

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

    private Intent getExtraIntents(Intent intent,SenderOrder order) {

        ArrayList<SenderOrderItemAttributes> items = order.getOrder_items();

        SenderOrderItemAttributes item = null;
        if(items==null || items.size()==0) {

            item = new SenderOrderItemAttributes();

        }
        else
            item = (SenderOrderItemAttributes)items.get(0);

        //Log.d("XXXXX",item.getItem_type());

        intent.putExtra(SenderListActivityDetailFragment.ORDER_ID,order.getOrderId());
        intent.putExtra(SenderListActivityDetailFragment.USER_NAME, order.getUser().getName());
        intent.putExtra(SenderListActivityDetailFragment.ADDRESS,order.getUser().getAddress());
        intent.putExtra(SenderListActivityDetailFragment.IMAGE,order.getUser().getImage());
        intent.putExtra(SenderListActivityDetailFragment.FROM_ADDRESS,order.getFrom_loc());
        intent.putExtra(SenderListActivityDetailFragment.TO_ADDRESS,order.getTo_loc());
        intent.putExtra(SenderListActivityDetailFragment.RATE,order.getTotal_amount());
        intent.putExtra(SenderListActivityDetailFragment.SUB_CATEGORY,item.getItem_subtype());
        intent.putExtra(SenderListActivityDetailFragment.CATEGORY,item.getItem_type());
        intent.putExtra(SenderListActivityDetailFragment.USER_OBJ,order.getUser());


        return intent;
    }
}
