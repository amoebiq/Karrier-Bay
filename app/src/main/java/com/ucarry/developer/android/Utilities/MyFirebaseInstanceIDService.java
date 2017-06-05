package com.ucarry.developer.android.Utilities;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ucarry.developer.android.Model.FCMRequest;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by skadavath on 5/24/17.
 */

public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService{


    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        Log.d(TAG,token);
        Log.d("FCM","Updating token");

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.put(SessionManager.FCM_REG_ID,token);



    }


    public static void updateFCMRegId(String token , Context ctx) {

        Call<User> call ;
        ApiInterface apiInterface = ApiClient.getClientWithHeader(ctx).create(ApiInterface.class);
        FCMRequest request = new FCMRequest();
        request.setRegId(token);


        call = apiInterface.updateFCM(request);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.d(TAG,response.code()+"");

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Log.d(TAG,t.getLocalizedMessage());

            }
        });
    }

}
