package com.ucarry.developer.android.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.squareup.picasso.Picasso;
import com.ucarry.developer.android.Model.BankDetail;
import com.ucarry.developer.android.Model.BankDetailRequest;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.databinding.FragmentProfileBinding;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ucarry.developer.android.Model.ImageUploadResponse;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.Model.UserUpdateRequest;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.BaseFragment;
import com.ucarry.developer.android.Utilities.CircleTransform;
import com.ucarry.developer.android.Utilities.SessionManager;
import com.ucarry.developer.android.activity.MainActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    private static final int PICK_IMAGE = 1;
    private String attachmentType = "image";
    private static final String attachmentTypeImage = "image";
    private static final String attachmentTypeAadhar = "aadhar";
    private static final String TAG = "ProfileFragment";
    public ArrayAdapter<CharSequence>  bankSpinnerAdapter;
    public ArrayAdapter<CharSequence> adapter;
    public Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        currView = container;
        binding.setHandler(new ProfileHandler());
        binding.emailEdittext.setEnabled(false);
        binding.locationEdittext.setEnabled(false);
        binding.phoneNumberEdittext.setEnabled(false);
        binding.locationEdittext.setBackgroundColor(getResources().getColor(R.color.colorPrimaryTransparent));
        binding.bankAccountNumberEt.setEnabled(false);
        binding.bankIfscEt.setEnabled(false);
        binding.profilepic.setEnabled(false);
        binding.aadharAttachment.setVisibility(currView.INVISIBLE);
        binding.aadharAttachmentVerified.setVisibility(currView.INVISIBLE);
        binding.pendingNotofication.setVisibility(currView.INVISIBLE);
        binding.profileSaveButton.setVisibility(container.INVISIBLE);

        binding.aadharAttachment.setEnabled(false);



            return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setHasOptionsMenu(true);
        //MenuItem item = (MenuItem) view.findViewById(R.id.action_home);
        //item.setVisible(false);
       // getAndUpdateUserDetails();


        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loadDetails();

        sessionManager = new SessionManager(getActivity());
        HashMap<String,String> u = sessionManager.getUserDetails();




        Log.d("Verify","Verify Start User");
        if(u.get(SessionManager.KEY_IMAGE)!=null)
        Log.d("image",u.get(SessionManager.KEY_IMAGE));
        if(u.get(SessionManager.KEY_AADHAR)!=null) {

            Log.d("aadhar image", u.get(SessionManager.KEY_AADHAR));

        }
        user = sessionManager.getUserDetails();
        if(user.get("image")!=null)
        displayImage(user.get("image"));

        if(user.get(SessionManager.KEY_VERIFIED)!=null) {
            Log.d("AADHAR_VERIFY",user.get(SessionManager.KEY_VERIFIED));
        }
        if(user.get(SessionManager.KEY_VERIFIED)!=null && user.get(SessionManager.KEY_VERIFIED).equals("verified")) {

           binding.aadharAttachmentVerified.setVisibility(currView.VISIBLE);
        }
        else if(user.get(SessionManager.KEY_AADHAR)!=null) {

            binding.pendingNotofication.setVisibility(currView.VISIBLE);
        }
        else {

            binding.aadharAttachment.setVisibility(currView.VISIBLE);

        }
        binding.emailEdittext.setText(user.get(SessionManager.KEY_EMAIL));
        binding.phoneNumberEdittext.setText(user.get(SessionManager.KEY_PHONE));
        binding.locationEdittext.setText(user.get(SessionManager.KEY_ADDRESS));
        binding.nameHeader.setText(user.get(SessionManager.KEY_NAME));
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


        ImageButton ib = (ImageButton)getView().findViewById(R.id.edit_profile);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.locationEdittext.setEnabled(true);
                binding.profileSaveButton.setVisibility(currView.VISIBLE);
                binding.profilepic.setEnabled(true);
                binding.aadharAttachment.setEnabled(true);
                binding.bankAccountNumberEt.setEnabled(true);
                binding.bankIfscEt.setEnabled(true);

            }
        });

        Button updateButton = (Button) view.findViewById(R.id.profile_save_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog pd = new ProgressDialog(getContext());
                pd.setIndeterminate(true);
                pd.setMessage("Updating Details...");
                pd.show();
                Log.d("PROFILE_UPDATE","Button Clicked");

                ApiInterface apiInterface = ApiClient.getClientWithHeader(getContext()).create(ApiInterface.class);
                user = sessionManager.getUserDetails();
                BankDetail bankDetail = new BankDetail();
                bankDetail.setAccountNo(binding.bankAccountNumberEt.getText().toString());
                bankDetail.setIfsc(binding.bankIfscEt.getText().toString());
                bankDetail.setBank_name(binding.bankListSpinner.getSelectedItem().toString());
                BankDetailRequest  bankDetailRequest= new BankDetailRequest();
                bankDetailRequest.setBankDetail(bankDetail);


                Call<User> call = apiInterface.editUserDetails(new UserUpdateRequest(binding.locationEdittext.getText().toString(),user.get(SessionManager.KEY_IMAGE),user.get(SessionManager.KEY_AADHAR),bankDetail));
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(response.code()==200) {

                            Toast.makeText(getContext(),"Updated Successfully" , Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            binding.profileSaveButton.setVisibility(currView.INVISIBLE);
                            binding.locationEdittext.setEnabled(false);
                            binding.profilepic.setClickable(false);
                            binding.profilepic.setEnabled(false);
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
                attachmentType = attachmentTypeImage;
                chooseImage();
                return false;
            }
        });

        ImageView iv_aadhar = (ImageView)view.findViewById(R.id.aadhar_attachment);
        iv_aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attachmentType=attachmentTypeAadhar;
                chooseImage();

            }
        });
    }

    public void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE);

    }
    public void getAndUpdateUserDetails() {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.setIndeterminate(true);
        pd.show();
        ApiInterface apiInterface = ApiClient.getClientWithHeader(getContext()).create(ApiInterface.class);
        Call<User> call = apiInterface.getUserDetails();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.code()==200) {

                    Log.d("USER_GET_DETAILS","Got 200!");
                    User user = response.body();
                    if(user.getImage()!=null)
                        sessionManager.put(SessionManager.KEY_IMAGE,user.getImage());
                    if(user.getAadhar_link()!=null)
                        sessionManager.put(SessionManager.KEY_AADHAR,user.getAadhar_link());

                    sessionManager.put(SessionManager.KEY_VERIFIED,user.getVerified());

                    HashMap<String,String> u = sessionManager.getUserDetails();
                    Log.d("Verify","Verify User");
                    Log.d("image",u.get("image"));
                    if(u.get(SessionManager.KEY_AADHAR)!=null)
                    Log.d("aadhar image",u.get(SessionManager.KEY_AADHAR));

                    pd.dismiss();

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(pd.isShowing()) {
                    pd.dismiss();
                }

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
            binding.profilepic.setEnabled(true);




        }
        return super.onOptionsItemSelected(item);


    }

    public void displayImage(final String url) {

        Log.d("IMAGE_DISPLAY",url);

        final ProgressDialog pd = new ProgressDialog(currView.getContext());
        pd.setIndeterminate(true);
        pd.setMessage("Downloading Image...");
        pd.show();

        String uri = null;
        String endpoint = null;
        if(url.contains("https://s3.amazonaws.com")) {

             endpoint = url.split("https://s3.amazonaws.com")[1];
             uri = "https://s3-us-west-2.amazonaws.com/"+endpoint;
        }
        else
            uri = url;
        Log.d("IMAGE_DISPLAY",uri);
        //final String uri = "https://s3-us-west-2.amazonaws.com/"+endpoint;

        int id = 0;
        if(attachmentType.equals(attachmentTypeImage)) {
            id = R.id.profilepic;
        }
        else if(attachmentType.equals(attachmentTypeAadhar)) {
            id = R.id.aadhar_attachment;
        }
        ImageView iv = (ImageView) currView.findViewById(id);
        if(id==R.id.profilepic)
        Picasso.with(currView.getContext()).load(uri).transform(new CircleTransform()).into(iv);
        Log.d("IMAGE_DISPLAY",uri);

        pd.dismiss();


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
        Log.d("IMAGE","Got the Image::::"+requestCode+" "+resultCode);
        File file = null;
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            // process the result

            Log.d("IMAGE", "Got the Image");
            Uri selectedImage = data.getData();
//            String wholeID = getRealPathFromURI(selectedImage);//DocumentsContract.getDocumentId(selectedImage);
//            String id = wholeID.split(":")[1];
//            String[] column = {MediaStore.Images.Media.DATA};
//            String sel = MediaStore.Images.Media._ID + "=?";
//            Cursor cursor = currView.getContext().getContentResolver().
//                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                            column, sel, new String[]{id}, null);
//            String filePath = "";
//            int columnIndex = cursor.getColumnIndex(column[0]);
//            if (cursor.moveToFirst()) {
//                filePath = cursor.getString(columnIndex);
//            }
//            cursor.close();

            file = new File(getRealPathFromDocumentUri(getContext(),selectedImage));

            uploadImage(file);
        }


        }
        catch(Exception e) {

            e.printStackTrace();
            Toast.makeText(getContext(),"Oops.. Something went wrong",Toast.LENGTH_LONG).show();

        }


    }


    public static String getRealPathFromDocumentUri(Context context, Uri uri){
        String filePath = "";

        Pattern p = Pattern.compile("(\\d+)$");
        Matcher m = p.matcher(uri.toString());
        if (!m.find()) {

            return filePath;
        }
        String imgId = m.group();

        String[] column = { MediaStore.Images.Media.DATA };
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ imgId }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
    }

    public void uploadImage(File file) {

        final ProgressDialog pd = new ProgressDialog(currView.getContext());
        pd.setIndeterminate(true);
        pd.setMessage("Processing...");
        pd.show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture",file.getName(),requestBody);

        ApiInterface apiInterface = ApiClient.getClientWithHeader(currView.getContext()).create(ApiInterface.class);
        Call<ImageUploadResponse> call = apiInterface.uploadFile(body,file.getName());

        call.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {

                pd.dismiss();
                Log.d("UPLOAD","Success:::"+response.code());

                ImageUploadResponse object = response.body();

                Log.d("UPLOAD","Successsss:::"+object.getUrl());

                Toast.makeText(currView.getContext(),"Succesfully Uploaded",Toast.LENGTH_LONG).show();

                if(attachmentType.equals(attachmentTypeImage))
                    sessionManager.put(SessionManager.KEY_IMAGE,object.getUrl());
                else if(attachmentType.equals(attachmentTypeAadhar))
                    sessionManager.put(SessionManager.KEY_AADHAR,object.getUrl());

                displayImage(object.getUrl());
                //displayImageWithTN(object.getUrl());

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {

                Log.d("UPLOAD","Failed "+t.getMessage());

            }
        });


    }

    private void loadDetails() {

        Log.d(TAG,"Loading user...");
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setIndeterminate(true);
        pd.setMessage("Loading your details...");
        pd.show();

        ApiInterface apiInterface = ApiClient.getClientWithHeader(getContext()).create(ApiInterface.class);
        Call<User> call = apiInterface.getUserDetails();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                pd.dismiss();
                if(response.code()==200 || response.code()==201) {

                    User user = response.body();
                    if(user!=null) {

                        sessionManager = new SessionManager(getContext());
                        sessionManager.put(SessionManager.KEY_PHONE,user.getPhone());
                        BankDetail bankDetail = user.getBankDetail();
                        //Log.d(TAG,bankDetail.getBank_name());
                        if(bankDetail!=null) {
                            Log.d(TAG,bankDetail.getBank_name());
                            sessionManager.put(SessionManager.BANK_DETAIL_ACC_NO,bankDetail.getAccountNo());
                            sessionManager.put(SessionManager.BANK_DETAIL_BANK_NAME,bankDetail.getBank_name());
                            sessionManager.put(SessionManager.BANK_DETAIL_IFSC,bankDetail.getIfsc());
                            binding.bankAccountNumberEt.setText(bankDetail.getAccountNo());
                            binding.bankIfscEt.setText(bankDetail.getIfsc());







                        }

                    }

                    //spinner = (Spinner) view.findViewById(R.id.bank_list_spinner);
                    spinner = (Spinner) binding.bankListSpinner;
// Create an ArrayAdapter using the string array and a default spinner layout
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.bank_list, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    if(sessionManager.getvalStr(SessionManager.BANK_DETAIL_BANK_NAME)!=null) {

                        Log.d(TAG,"PositionXXX:::"+sessionManager.getvalStr(SessionManager.BANK_DETAIL_BANK_NAME));
                        int pos = adapter.getPosition(sessionManager.getvalStr(SessionManager.BANK_DETAIL_BANK_NAME));
                        spinner.setSelection(pos);

                    }
                    bankSpinnerAdapter = adapter;

// Apply the adapter to the spinner



                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                if(pd.isShowing())
                    pd.dismiss();

                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
