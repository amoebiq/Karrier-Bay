package Adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yourapp.developer.karrierbay.BR;
import com.yourapp.developer.karrierbay.R;

import java.util.List;

import Model.SenderOrder;
import Model.SenderOrderItemAttributes;
import Utilities.CustomViewHolder;

/**
 * Created by carl on 12/1/15.
 */

public class HistoryAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<SenderOrder> historyList;
    private SenderOrderItemAttributes[] historyItemAttributes;

    public HistoryAdapter(List<SenderOrder> historyList) {
        this.historyList = historyList;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_history_list, viewGroup, false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        ViewDataBinding viewDataBinding = customViewHolder.getViewDataBinding();
        viewDataBinding.setVariable(BR.sender, historyList.get(i));
        //I think this is for Sender
        historyItemAttributes = historyList.get(i).getSender_order_item_attributes();
       //I think this is for currier
        historyItemAttributes = historyList.get(i).getSender_order_item();
          for(int j=0; j<historyItemAttributes.length ; j++)
          {
              viewDataBinding.setVariable(BR.senderitems, historyItemAttributes[j]);
              viewDataBinding.setVariable(BR.item,historyItemAttributes[j].getItem_attributes());
          }
        viewDataBinding.setVariable(BR.user,historyList.get(i).getUser());
    }

    @Override
    public int getItemCount() {
        return (null != historyList ? historyList.size() : 0);
    }


}