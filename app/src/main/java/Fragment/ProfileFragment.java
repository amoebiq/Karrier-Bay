package Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.FragmentProfileBinding;

import java.util.HashMap;

import Utilities.BaseFragment;
import Utilities.SessionManager;
import activity.MainActivity;

public class ProfileFragment extends BaseFragment implements
        GoogleApiClient.OnConnectionFailedListener {


    private FragmentProfileBinding binding;
    private HashMap<String, String> user;
    private SessionManager sessionManager;
    private GoogleApiClient mGoogleApiClient;
    private String lat,lon;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setHandler(new ProfileHandler());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails();
        binding.nameHeader.setText(user.get(SessionManager.KEY_NAME));
        binding.emailHeader.setText(user.get(SessionManager.KEY_EMAIL));
        binding.emailEdittext.setText(user.get(SessionManager.KEY_EMAIL));
        binding.phoneNumberEdittext.setText(user.get(SessionManager.KEY_PHONE));
        if (user.get(SessionManager.KEY_ADDRESS) == null) {
            // for getting current location
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                    .build();
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE);
            } else {
                callPlaceDetectionApi();
            }
        } else {
            binding.locationEdittext.setText(user.get(SessionManager.KEY_ADDRESS));
        }
    }

    private void callPlaceDetectionApi() throws SecurityException {
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    binding.locationEdittext.setText(placeLikelihood.getPlace().getAddress());
                    lat = placeLikelihood.getPlace().getLatLng().latitude+"";
                    lon = placeLikelihood.getPlace().getLatLng().longitude+"";
                }
                likelyPlaces.release();
            }
        });
    }

    @Override
    public void onStop() {
        if (user.get(SessionManager.KEY_ADDRESS) == null) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class ProfileHandler {
        public void ProfileOnClick(View view) {
            Toast.makeText(getActivity(), "Your details has been updated", Toast.LENGTH_LONG).show();
            sessionManager.address(binding.locationEdittext.getText().toString(),lat,lon);
            ((MainActivity) getActivity()).fragment(new HomeFragment(), "HomeFragment");

        }
    }
}
