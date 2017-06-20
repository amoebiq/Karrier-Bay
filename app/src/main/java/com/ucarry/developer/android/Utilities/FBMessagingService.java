package com.ucarry.developer.android.Utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ucarry.developer.android.activity.CarrierListActivity;
import com.ucarry.developer.android.activity.MainActivity;
import com.yourapp.developer.karrierbay.R;

/**
 * Created by skadavath on 5/30/17.
 */

public class FBMessagingService extends FirebaseMessagingService {

    private static String TAG = "FBMESSAGESERVICE";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG,remoteMessage.getFrom());
        Log.d(TAG,"Notification Body: "+remoteMessage.getNotification().getBody());
        Log.d(TAG , "Body : "+remoteMessage.getNotification().getTitle());
        Log.d(TAG , "Action : "+remoteMessage.getData().get("action"));



        String action = remoteMessage.getData().get("action");

        sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),action);





    }

    private void sendNotification(String title , String messageBody,String action) {
        Log.d(TAG,"Title ::: "+title);
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_kb_notify)
                .setContentTitle(title)
                //.setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setColor(getResources().getColor(R.color.colorPrimaryDark))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

        Log.d(TAG,"Message Recieved::"+intent.getStringExtra("action"));

        //String action = intent.getDataString();

//        Intent intt = new Intent(this, CarrierListActivity.class);
//        intt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intt);
    }
}
