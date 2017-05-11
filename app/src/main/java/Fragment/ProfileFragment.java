package Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.squareup.picasso.Picasso;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.FragmentProfileBinding;

import java.util.HashMap;

import Model.User;
import Model.UserUpdateRequest;
import RetroGit.ApiClient;
import RetroGit.ApiInterface;
import Utilities.BaseFragment;
import Utilities.CircleTransform;
import Utilities.SessionManager;
import activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BaseFragment implements
        GoogleApiClient.OnConnectionFailedListener {


    private FragmentProfileBinding binding;
    private HashMap<String, String> user;
    private SessionManager sessionManager;
    private GoogleApiClient mGoogleApiClient;
    private String lat,lon;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int PERMISSION_REQUEST_CODE = 100;
    ViewGroup currView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        currView = container;
        binding.setHandler(new ProfileHandler());
        binding.emailEdittext.setEnabled(false);
        binding.locationEdittext.setEnabled(false);
        binding.phoneNumberEdittext.setEnabled(false);

        binding.profileSaveButton.setVisibility(container.INVISIBLE);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails();
        displayImage("https://s3.amazonaws.com/ucarrytest/docs/72/IMG-20170509-WA0048_72.jpg");
        Log.d("PROFILE_FRAGMENT","Phone set is ::: "+user.get(SessionManager.KEY_PHONE).toString());
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

        Button updateButton = (Button) view.findViewById(R.id.profile_save_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog pd = new ProgressDialog(getContext());
                pd.setIndeterminate(true);
                pd.setMessage("Updating Details...");
                pd.show();
                Log.d("PROFILE_UPDATE","Button Clicked");

                ApiInterface apiInterface = ApiClient.getClientWithHeader(getContext()  ).create(ApiInterface.class);
                Call<User> call = apiInterface.editUserDetails(new UserUpdateRequest(binding.locationEdittext.getText().toString(),null));
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(response.code()==200) {

                            Toast.makeText(getContext(),"Updated Successfully" , Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            binding.profileSaveButton.setVisibility(currView.INVISIBLE);
                            binding.locationEdittext.setEnabled(false);
                            sessionManager.put(sessionManager.KEY_ADDRESS,binding.locationEdittext.getText().toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                        Log.d("PROFILE_UPDATE","failed "+t.getMessage());
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                    }
                });


            }
        });
        ImageView iv = (ImageView)view.findViewById(R.id.profilepic);
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("PROFILE","Clicked...");
                return false;
            }
        });
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int it = item.getItemId();
        if(it==R.id.editIcon) {

            binding.locationEdittext.setEnabled(true);
            binding.profileSaveButton.setVisibility(currView.VISIBLE);

        }
        return super.onOptionsItemSelected(item);


    }

    public void displayImage(final String url) {


        final ProgressDialog pd = new ProgressDialog(currView.getContext());
        pd.setIndeterminate(true);
        pd.setMessage("Downloading Image...");
        //pd.show();


        String endpoint = url.split("https://s3.amazonaws.com")[1];
        Log.d("IMAGE_DISPLAY",endpoint);
        final String uri = "https://s3-us-west-2.amazonaws.com/"+endpoint;
        ImageView iv = (ImageView) currView.findViewById(R.id.profilepic);
        Picasso.with(currView.getContext()).load(uri).transform(new CircleTransform()).into(iv);
        Log.d("IMAGE_DISPLAY",url);

       // pd.dismiss();


    }
}
