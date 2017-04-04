package Adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yourapp.developer.karrierbay.BR;
import com.yourapp.developer.karrierbay.R;

import java.util.List;

import Model.CarrierScheduleDetailAttributes;
import Model.SenderOrder;
import Utilities.CustomViewHolder;

/**
 * Created by carl on 12/1/15.
 */

public class CurrentAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<SenderOrder> historyList;
    private CarrierScheduleDetailAttributes carrierScheduleDetailAttributes;

    public CurrentAdapter(List<SenderOrder> historyList) {
        this.historyList = historyList;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_current_list, viewGroup, false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        ViewDataBinding viewDataBinding = customViewHolder.getViewDataBinding();
        viewDataBinding.setVariable(BR.sender, historyList.get(i));
        viewDataBinding.setVariable(BR.carrier, historyList.get(i).getCarrier_schedule_detail());
        viewDataBinding.setVariable(BR.user,historyList.get(i).getUser());
    }

    @Override
    public int getItemCount() {
        return (null != historyList ? historyList.size() : 0);
    }


}