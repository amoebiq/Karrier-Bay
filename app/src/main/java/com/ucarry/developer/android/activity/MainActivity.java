package com.ucarry.developer.android.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.ucarry.developer.android.Utilities.MyFirebaseInstanceIDService;
import com.yourapp.developer.karrierbay.R;

import java.util.Calendar;
import java.util.HashMap;

import com.ucarry.developer.android.Fragment.ContactFragment;
import com.ucarry.developer.android.Fragment.HomeFragment;
import com.ucarry.developer.android.Fragment.ProfileFragment;
import com.ucarry.developer.android.Fragment.*;
import com.ucarry.developer.android.Model.Constants;
import com.ucarry.developer.android.Model.QuoteRequest;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.CircleTransform;
import com.ucarry.developer.android.Utilities.SessionManager;
import com.ucarry.developer.android.Utilities.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ucarry.developer.android.Utilities.Utility.hideKeyboard;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static String TAG = "MAINACTIVITY";
    private SessionManager sessionManager;
    private NavigationView navigationView;
    private String tag;
    private HashMap<String, String> user;
    private TextView emailHeader,nameHeader;
    public ApiInterface apiService;
    public SenderOrder sender = new SenderOrder();
    public QuoteRequest quoteRequest = new QuoteRequest();
    private static int NOTIFICATION_ID = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MyFirebaseInstanceIDService.class);
        startService(intent);

        boolean isPermission = isStoragePermissionGranted();

        Log.d(TAG,isPermission+" Permission Available");

        //createNotification();
        //String refreshToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("TOKEN", "Refreshed token: " + refreshToken);
        getAndUpdateUserDetails();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sessionManager = new SessionManager(getApplicationContext());
        //Sender flow
        String s = "{\"carrier_schedule_detail_attributes\":{},\"fromDate\":\"5-3-2017\",\"fromTime\":\"13:52\",\"from_geo_lat\":\"11.8014\",\"from_geo_long\":\"76.0044\",\"from_loc\":\"Rajapalayam, Tamil Nadu, India\",\"pickup_order_mapping\":{\"address_line_1\":\"hshshsh\",\"address_line_2\":\"Rajapalayam, Tamil Nadu, India\",\"fullAdress\":\"hshhs\\ngsg\\nhshshsh\\nRajapalayam, Tamil Nadu, India\\n9894100115\",\"landmark\":\"gsg\",\"name\":\"hshhs\",\"phone_1\":\"9894100115\"},\"receiver_order_mapping\":{\"address_line_1\":\"gah\",\"address_line_2\":\"Chennai, Tamil Nadu, India\",\"fullAdress\":\"nsjsj\\ngs\\ngah\\nChennai, Tamil Nadu, India\\n9894100445\",\"landmark\":\"gs\",\"name\":\"nsjsj\",\"phone_1\":\"9894100445\"},\"sender_order_item_attributes\":[{\"end_time\":\"2017-04-05T08:22:49.072\",\"item_attributes\":{\"breadth\":\"64\",\"height\":\"54\",\"length\":\"64\",\"volumetricfullDetails\":\"64  64  54\\n\",\"item_weight\":\"21\",\"breadthIndex\":0},\"item_subtype\":\"Electronic Item\",\"item_type\":\"Article\",\"start_time\":\"2017-04-05T08:22:49.070\",\"item_subtype_index\":0,\"item_type_index\":0}],\"toDate\":\"5-3-2017\",\"toTime\":\"13:52\",\"to_geo_lat\":\"12.9716\",\"to_geo_long\":\"77.5946\",\"to_loc\":\"Chennai, Tamil Nadu, India\",\"user\":{},\"from_loc_index\":0,\"isSender\":true,\"spinWantToSendIdx\":0}";
        String quot = "{\"breadth\":\"64\",\"height\":\"54\",\"item_weight\":\"21\",\"lat1\":\"9.465337699999997\",\"lat2\":\"13.082680199999997\",\"length\":\"64\",\"long1\":\"77.5275463\",\"long2\":\"80.2707184\"}";

        Gson gson = new Gson();
        try {
            sender = gson.fromJson(s, SenderOrder.class);
            quoteRequest = gson.fromJson(quot, QuoteRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>CrowdCarry</font>"));

        View hView = navigationView.getHeaderView(0);
        emailHeader = (TextView) hView.findViewById(R.id.email_header);
        nameHeader = (TextView) hView.findViewById(R.id.name_header);
        user = sessionManager.getUserDetails();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here

                Log.d(TAG , "Drawer Closed:::"+user.get(SessionManager.KEY_NAME));
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here

                Log.d(TAG , "Drawer Opened:::"+user.get(SessionManager.KEY_NAME));
                emailHeader.setText(user.get(SessionManager.KEY_EMAIL));
                nameHeader.setText(user.get(SessionManager.KEY_NAME));

            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();






        emailHeader.setText(user.get(SessionManager.KEY_EMAIL));
        nameHeader.setText(user.get(SessionManager.KEY_NAME));

        String token = sessionManager.getvalStr(SessionManager.FCM_REG_ID);


        if(token!=null) {

            Log.d("FCM TOKEN:::", token);

            MyFirebaseInstanceIDService.updateFCMRegId(token, getApplicationContext());

        }


//        ImageView iv = (ImageView) findViewById(R.id.profilepic);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG);
//            }
//        });
        hView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Fragment frg = null;
//                frg = getSupportFragmentManager().findFragmentByTag("ProfileFragment");
//                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.detach(frg);
//                ft.attach(frg);
//                ft.commit();


                fragment(new ProfileFragment(), "ProfileFragment");
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        Log.d(TAG,"IMAGE IS CHECKED");
        String image = sessionManager.getvalStr("image");
        if(image!=null) {

            Log.d(TAG,"IMAGE IS NOT NULL");
              displayImage(hView, image);

        }
        else {

            Log.d(TAG,"IMAGE IS NULL");
        }

        fragment(new HomeFragment(), "MainFragment");
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public void createNotification() {


        Log.d("MAIN_ACTIVITY","CREATE NOTIFICATION");
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        int color = 0xff123456;
        builder.setSmallIcon(R.mipmap.ic_kb_notify)
                .setContentTitle("KarrierBay")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentText("Enthello")
                .setColor(color);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID,builder.build());

    }

    public void getAndUpdateUserDetails() {

        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
//        pd.setMessage("Loading...");
  //      pd.setIndeterminate(true);
    //    pd.show();
        ApiInterface apiInterface = ApiClient.getClientWithHeader(getApplicationContext()).create(ApiInterface.class);
        Call<User> call = apiInterface.getUserDetails();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.code()==200) {

                    Log.d(TAG,"Got 200!");
                    User user = response.body();
                    if(user.getImage()!=null)
                        sessionManager.put(SessionManager.KEY_IMAGE,user.getImage());
                    if(user.getAadhar_link()!=null)
                        sessionManager.put(SessionManager.KEY_AADHAR,user.getAadhar_link());

                    if(user.getAddress()!=null) {
                        sessionManager.put(SessionManager.KEY_ADDRESS,user.getAddress());
                    }

                    sessionManager.put(SessionManager.KEY_VERIFIED,user.getVerified());

                    HashMap<String,String> u = sessionManager.getUserDetails();
                    Log.d(TAG,"Verify User");
                    if(u.get(SessionManager.KEY_IMAGE)!=null)
                    Log.d(TAG,u.get("image"));
                    if(u.get(SessionManager.KEY_AADHAR)!=null)
                        Log.d(TAG,u.get(SessionManager.KEY_AADHAR));

                  //  pd.dismiss();

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {


            }
        });


    }

    public void displayImage(View view ,final String url) {


        final ProgressDialog pd = new ProgressDialog(view.getContext());
        pd.setIndeterminate(true);
        pd.setMessage("Downloading Image...");
        pd.show();

        String uri = Utility.getAwsUrl(url);
        ImageView iv = (ImageView) view.findViewById(R.id.profile_avatar);
        Picasso.with(view.getContext()).load(uri).transform(new CircleTransform()).into(iv);
        Log.d(TAG,uri);

        pd.dismiss();

    }
    @Override
    protected void onResume() {
        apiService = ApiClient.getClientWithHeader(this).create(ApiInterface.class);

        super.onResume();
    }
    @Override
    public void onBackPressed() {

        hideKeyboard(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            //Toast.makeText(this,tag,Toast.LENGTH_LONG).show();
            if (tag.equals("MainFragment")) {
                finish();
            } else if (tag.equals(Constants.DETAILSFRAGMENT)) {
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 1; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                sender = new SenderOrder();
                quoteRequest = new QuoteRequest();
                fragment(new HomeFragment(), "MainFragment");
            }

            //super.onBackPressed();
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                //finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyboard(this);
        int id = item.getItemId();
       /* if (id == R.id.action_notification)
        {
            fragment(new NotificationFragment(),"NotificationFragment");
            //item.setVisible(false);
        }*/
        if (id == R.id.action_home) {
            sender = new SenderOrder();
            quoteRequest = new QuoteRequest();
            fragment(new HomeFragment(), "MainFragment");
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        hideKeyboard(this);
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if(id==R.id.nav_home) {
            fragment(new HomeFragment(),"HomeFragment");
        }
        if(id==R.id.nav_my_bay)
        {
            fragment(new MyBayFragment(), "MyBayFragment");

        }

        if(id==R.id.nav_legal) {

            Intent intent = new Intent(this,LegalListActivity.class);
            startActivity(intent);
        }
        if(id==R.id.carrier_list) {

//            Bundle bundle = new Bundle();
//            bundle.putBoolean("isCarrierFlow", true);
//            bundle.putBoolean("isSenderFlow", false);
//
//            CarrierListFragment clf = new CarrierListFragment();
//            clf.setArguments(bundle);
//            fragment(clf,"CarrierListFragment");

            Intent intent = new Intent(this,CarrierListActivity.class);
            startActivity(intent);

        }

        if(id==R.id.sender_list) {
//            this.sender = new SenderOrder();

          //  Bundle bundle = new Bundle();
//            bundle.putBoolean("isSenderFlow", true);
//            bundle.putBoolean("isCarrierFlow", false);
//            SenderFragment senderFragment = new SenderFragment();
//
//            CarrierListFragment clf = new CarrierListFragment();
//            SenderListFragment slf = new SenderListFragment();
//            clf.setArguments(bundle);
//            fragment(slf,"SenderListFragment");

           // Log.d("Firing sender","Sender List");
          //  fragment(new SenderListFragment(),"SenderListFragement");

            Intent intent = new Intent(this,SenderListActivityListActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_contact_us) {
            fragment(new ContactFragment(), "ContactFragment");
        }
        if (id == R.id.nav_rate_app) {
            //final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            final String appPackageName = "com.yourapp.batterystatus";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
        if (id == R.id.nav_logout) {
            sessionManager.logoutUser();
            finish();
            //Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManager.checkLogin(), Toast.LENGTH_LONG).show();
        }

        if(id == R.id.nav_settings) {

            Intent intent = new Intent(this,Settings.class);
            startActivity(intent);
        }
        return false;
    }

    public void fragment(Fragment fragment, String transaction) {
        tag = transaction;
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_transaction, fragment, transaction);
        fragmentTransaction.addToBackStack(transaction);
        fragmentTransaction.commit();
        Log.d("backFragment", tag);
    }


    public void dateClick(View view) {
        final EditText et = (EditText) view;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this,
                new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker arg0,
                                                  int arg1, int arg2, int arg3) {

                                et.setText(arg3 + "-" + (arg2 + 1) + "-" + arg1);
                            }
                        }, year, month, day).show();
    }



    public void timeClick(View view) {
        final EditText et = (EditText) view;
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

}