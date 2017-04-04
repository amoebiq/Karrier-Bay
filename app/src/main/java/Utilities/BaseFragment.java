package Utilities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import RetroGit.ApiClient;
import RetroGit.ApiInterface;

/**
 * Created by nirmal.ku on 11/7/2016.
 */
public class BaseFragment extends Fragment {

    protected Typeface mTfRegular;
    protected Typeface mTfBold;
    protected Typeface mTfSemiBold;
    protected ApiInterface apiService;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfBold = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
        mTfSemiBold = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Semibold.ttf");
        apiService = ApiClient.getClient().create(ApiInterface.class);


    }
}
