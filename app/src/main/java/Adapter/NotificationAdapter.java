package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yourapp.developer.karrierbay.R;

import java.util.ArrayList;
import java.util.List;

import Model.NotificationList;

/**
 * Created by nirmal.ku on 1/27/2017.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CustomViewHolder> {

private List<NotificationList> notificationLists = new ArrayList<NotificationList>();

    public NotificationAdapter(List<NotificationList> mnotificationLists) {

        this.notificationLists = mnotificationLists;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView title,time;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            time = (TextView)itemView.findViewById(R.id.time);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_row, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.title.setText(notificationLists.get(position).getTitle());
        holder.time.setText(notificationLists.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return notificationLists.size();
    }


}
