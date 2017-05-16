package Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yourapp.developer.karrierbay.R;

import java.util.ArrayList;
import java.util.List;

import Model.SenderOrder;
import Model.SenderOrderItemAttributes;
import Model.SenderOrderResponse;
import Utilities.CircleTransform;
import Utilities.HeaderViewHolder;
import Utilities.SenderViewHolder;
import activity.SenderListActivityDetailActivity;
import activity.SenderListActivityDetailFragment;

/**
 * Created by skadavath on 5/15/17.
 */

    public class SenderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SenderOrder> senderOrderList;

    private View mView;

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_CELL = 1;

    public SenderAdapter(List<SenderOrder> list) {

        this.senderOrderList = list;

    }

    @Override
    public int getItemViewType(int position) {
        return (position==0) ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("ON_CREATE",""+viewType);
        View view;
        switch (viewType) {

            case (TYPE_HEADER):
                Log.d("SENDER_LIST","Type Header");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_header_text_view, parent, false);
                return new HeaderViewHolder(view);

            case (TYPE_CELL):
                Log.d("SENDER_LIST","Type Cell");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.senderlistactivity_list_content, parent, false);
                return new SenderViewHolder(view);

        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


       // holder.sender_from_loc.setText(senderOrderList.get(position).getFrom_loc());

        Log.d("ON_CREATE",""+position);

        switch(getItemViewType(position)) {

            case TYPE_CELL:
            SenderViewHolder sVH = (SenderViewHolder)holder;
                sVH.sender_name.setText(senderOrderList.get(position).getUser().getName());
                sVH.sender_from_val.setText(senderOrderList.get(position).getFrom_loc());
                sVH.sender_to_val.setText(senderOrderList.get(position).getTo_loc());
                sVH.sender_address.setText(senderOrderList.get(position).getUser().getAddress());



              //  Log.d("SENDER_DETAIL",senderOrderList.get(position).getSender_order_item_attributes()[0].getItem_type());

            Picasso.with(sVH.mView.getContext())

                    .load(senderOrderList.get(position).getUser().getImage())
                    .placeholder(R.drawable.carrier)
                    .error(R.drawable.myimage).transform(new CircleTransform())
                    .into(sVH.sender_image);


                sVH.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, SenderListActivityDetailActivity.class);
                    intent.putExtra(SenderListActivityDetailFragment.ARG_ITEM_ID, "1");
                    getDetails(intent,senderOrderList.get(position));
                    context.startActivity(intent);

                }
            });

                break;

            case TYPE_HEADER:
                HeaderViewHolder hVH = (HeaderViewHolder)holder;
                hVH.custom_header.setText(senderOrderList.size()+" SENDERS FOUND");
                break;

        }
    }

    @Override
    public int getItemCount() {

        Log.d("GET_ITEM_COUNT","GET_ITEM_COUNT::: "+senderOrderList.size());
        return senderOrderList.size();
    }


    public Intent getDetails(Intent intent , SenderOrder order) {

        ArrayList<SenderOrderItemAttributes> items = order.getOrder_items();
        SenderOrderItemAttributes item = (SenderOrderItemAttributes)items.get(0);

        Log.d("XXXXX",item.getItem_type());


        intent.putExtra(SenderListActivityDetailFragment.USER_NAME, order.getUser().getName());
        intent.putExtra(SenderListActivityDetailFragment.ADDRESS,order.getUser().getAddress());
        intent.putExtra(SenderListActivityDetailFragment.IMAGE,order.getUser().getImage());
        intent.putExtra(SenderListActivityDetailFragment.FROM_ADDRESS,order.getFrom_loc());
        intent.putExtra(SenderListActivityDetailFragment.TO_ADDRESS,order.getTo_loc());
        intent.putExtra(SenderListActivityDetailFragment.RATE,order.getTotal_amount());
        intent.putExtra(SenderListActivityDetailFragment.SUB_CATEGORY,item.getItem_subtype());
        intent.putExtra(SenderListActivityDetailFragment.CATEGORY,item.getItem_type());

        return intent;
    }


}
