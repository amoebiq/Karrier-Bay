package Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.FragmentContactBinding;

import Utilities.BaseFragment;

public class ContactFragment extends BaseFragment {



    private FragmentContactBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        binding.setHandler(new handler());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.contactButton.setTypeface(mTfSemiBold);
        binding.contactTitle.setTypeface(mTfRegular);
        binding.contactEdittext.setTypeface(mTfSemiBold);
    }


    public class handler
    {
        public void contactOnclick(View view)
        {
            Toast.makeText(getActivity(),binding.contactEdittext.getText().toString(),Toast.LENGTH_LONG).show();
        }
    }
}
