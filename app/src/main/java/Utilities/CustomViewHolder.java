package Utilities;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.yourapp.developer.karrierbay.R;

/**
 * Created by carl on 12/1/15.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder    {

  private ViewDataBinding mViewDataBinding;
    public  ImageView imageView;


    public CustomViewHolder( ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());

        mViewDataBinding = viewDataBinding;
        imageView= (ImageView) viewDataBinding.getRoot().findViewById(R.id.ivPic);
        mViewDataBinding.executePendingBindings();
    }

    public ViewDataBinding getViewDataBinding() {

        return mViewDataBinding;
    }



}
