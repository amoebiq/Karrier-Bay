package Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yourapp.developer.karrierbay.R;

import java.util.List;

import Model.SenderOrder;
import Utilities.CircleTransform;
import Utilities.SenderViewHolder;
import activity.SenderListActivityDetailActivity;
import activity.SenderListActivityDetailFragment;

/**
 * Created by skadavath on 5/15/17.
 */

    public class SenderAdapter extends RecyclerView.Adapter<SenderViewHolder> {

    private List<SenderOrder> senderOrderList;

    private View mView;

    public SenderAdapter(List<SenderOrder> list) {

        this.senderOrderList = list;

    }
    @Override
    public SenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.senderlistactivity_list_content,parent,false);
        return new SenderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SenderViewHolder holder, final int position) {


       // holder.sender_from_loc.setText(senderOrderList.get(position).getFrom_loc());


        holder.sender_name.setText(senderOrderList.get(position).getUser().getName());
        holder.sender_from_val.setText(senderOrderList.get(position).getFrom_loc());
        holder.sender_to_val.setText(senderOrderList.get(position).getTo_loc());

        Picasso.with(holder.mView.getContext())

                .load(senderOrderList.get(position).getUser().getImage())
                .placeholder(R.drawable.carrier)
                .error(R.drawable.myimage).transform(new CircleTransform())
                .into(holder.sender_image);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(context, SenderListActivityDetailActivity.class);
                intent.putExtra(SenderListActivityDetailFragment.ARG_ITEM_ID, "1");
                 context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return senderOrderList.size();
    }
}
