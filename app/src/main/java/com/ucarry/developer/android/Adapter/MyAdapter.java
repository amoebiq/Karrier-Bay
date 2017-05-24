package com.ucarry.developer.android.Adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.yourapp.developer.karrierbay.BR;
import com.yourapp.developer.karrierbay.R;

import java.util.List;

import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Utilities.BindingUtils;
import com.ucarry.developer.android.Utilities.CircleTransform;
import com.ucarry.developer.android.Utilities.CustomViewHolder;

/**
 * Created by carl on 12/1/15.
 */

public class MyAdapter extends RecyclerView.Adapter<CustomViewHolder>     {
    private List<SenderOrder> orderList;

    public MyAdapter(List<SenderOrder> orderList ) {
        this.orderList = orderList;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_carrier_list, viewGroup, false);
        
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Log.d("In Custom View Holder","In CustomView Holder");

        ViewDataBinding viewDataBinding = customViewHolder.getViewDataBinding();
        Picasso.with(viewDataBinding.getRoot().getContext())
                //.load("http://i.imgur.com/DvpvklR.png")
                .load(orderList.get(i).getUser().getImage())
//                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.myimage).transform(new CircleTransform())
                .into(customViewHolder.imageView);
        viewDataBinding.setVariable(BR.sender, orderList.get(i));
        viewDataBinding.setVariable(BR.senderitems, orderList.get(i).getSender_order_item()[0]);
        viewDataBinding.setVariable(BR.item,orderList.get(i).getSender_order_item()[0].getItem_attributes());
        viewDataBinding.setVariable(BR.user,orderList.get(i).getUser());
        viewDataBinding.setVariable(BR.presenter,new BindingUtils());






    }



    @Override
    public int getItemCount() {
        return (null != orderList ? orderList.size() : 0);
    }




}