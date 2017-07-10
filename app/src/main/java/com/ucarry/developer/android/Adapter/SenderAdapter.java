package com.ucarry.developer.android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.yourapp.developer.karrierbay.R;

import java.util.ArrayList;
import java.util.List;

import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Model.SenderOrderItemAttributes;
import com.ucarry.developer.android.Utilities.CircleTransform;
import com.ucarry.developer.android.Utilities.HeaderViewHolder;
import com.ucarry.developer.android.Utilities.SenderViewHolder;
import com.ucarry.developer.android.Utilities.Utility;
import com.ucarry.developer.android.activity.SenderListActivityDetailActivity;
import com.ucarry.developer.android.activity.SenderListActivityDetailFragment;

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
        this.senderOrderList.add(0,new SenderOrder());

    }

    @Override
    public int getItemViewType(int position) {
        return (position==0) ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("ON_CREATE",""+viewType);
        View view;
        switch (getItemViewType(viewType)) {

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
                sVH.tvAmount.setText(senderOrderList.get(position).getTotal_amount());

                Log.d("SENDER_LIST",senderOrderList.get(position).getFrom_loc()+" ::: "+senderOrderList.get(position).getId());

              //  Log.d("SENDER_DETAIL",senderOrderList.get(position).getSender_order_item_attributes()[0].getItem_type());

                   Picasso.Builder picBuilder = new Picasso.Builder(sVH.mView.getContext());
                picBuilder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                });

                picBuilder.build().load(Utility.getAwsUrl(senderOrderList.get(position).getUser().getImage())).placeholder(R.drawable.carrier).transform(new CircleTransform()).error(R.drawable.carrier).into(sVH.sender_image);


                sVH.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("SENDER_LIST","TOT:::POS"+senderOrderList.size()+" "+position);
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
                hVH.custom_header.setText(senderOrderList.size()-1+" SENDERS FOUND");
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
