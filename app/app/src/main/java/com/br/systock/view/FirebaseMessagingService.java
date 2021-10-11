package com.br.systock.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.br.systock.R;
import com.br.systock.model.Notifications;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String FCM_PARAM_TEXT = "text";
    private static final String FCM_PARAM_TEXT_BODY = "body";
    private static final String TAG = "NOTIFICATION";
    private static final String Notifications = "Notifications";
    private static final String User = "User";
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userId;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(User);


        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();


        com.br.systock.model.Notifications notifications = new Notifications(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User").child(userId).child("Notifications");
        myRef.push().setValue(notifications);

        String from = remoteMessage.getFrom();

    }


    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bundle bundle = new Bundle();
        String text = data.get(FCM_PARAM_TEXT);
        String body = data.get(FCM_PARAM_TEXT_BODY);

        if (text != null && !text.isEmpty()) {
            bundle.putString(FCM_PARAM_TEXT, text);
        }

        if (body != null && !body.isEmpty()) {
            bundle.putString(FCM_PARAM_TEXT_BODY, body);
        }

        Intent intent = new Intent(this, NotificationScreen.class);
        intent.putExtras(bundle);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(notification.getTitle())
                .setLargeIcon(icon)
                .setColor(Color.BLUE)
                .setLights(Color.BLUE, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.app_icon);

        try {
            String picture_url = notification.getImageUrl().toString();
            if (picture_url != null && !"".equals(picture_url)) {
                URL url = new URL(picture_url);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "");
    }

}