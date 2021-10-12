package com.codediffusion.tyidedriver.firebaseNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.codediffusion.tyidedriver.MainActivity;
import com.codediffusion.tyidedriver.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessenging  extends FirebaseMessagingService {
  public static String ReceivedMessage,NotificationTitle;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        Log.e("dhahdas",remoteMessage.getNotification().getTitle()+"");

        Log.d("Msg", "Message received [" + remoteMessage.getNotification().getBody() + "]");

         ReceivedMessage=remoteMessage.getNotification().getBody();
         NotificationTitle=remoteMessage.getNotification().getTitle();

        if (remoteMessage.getFrom().toString().contains("")){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                sendOreoNotification(remoteMessage);
            }else {
                sendPersonalNotfication(remoteMessage);
            }

        }



    }


    private void sendOreoNotification(RemoteMessage remoteMessage) {

        String message=remoteMessage.getData().get("body");

        Log.e("oreoNotification", remoteMessage.getData() + "");



            RemoteMessage.Notification notification = remoteMessage.getNotification();
            Intent intent = new Intent(this, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Uri defaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);


            //Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            if (defaultSound == null) {
                defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                if (defaultSound == null) {
                    defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                }
            }
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), defaultSound);
            ringtone.setStreamType(AudioManager.STREAM_ALARM);
            OreolNotification oreoPersonalNotification = new OreolNotification(this);
            NotificationCompat.Builder builder = oreoPersonalNotification.getOreoNotification( message, pendingIntent
                    , defaultSound);

        MediaPlayer sound = MediaPlayer.create(getApplicationContext(), R.raw.phone);
        sound.start();

            oreoPersonalNotification.notificationManager.notify(2, builder.build());
        }




    private void sendPersonalNotfication(RemoteMessage remoteMessage) {


        String message=remoteMessage.getData().get("message");


        Log.e("personalMessage",message+"");

            RemoteMessage.Notification notification = remoteMessage.getNotification();
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (defaultSound==null){

                defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                if (defaultSound == null){
                    defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                }
            }
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), defaultSound);
            ringtone.setStreamType(AudioManager.STREAM_ALARM);
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.primary_logo);

            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setLights(Color.YELLOW, 500, 5000);
            builder.setContentTitle(Html.fromHtml("<strong>"+NotificationTitle+"</strong>"));
             builder.setContentTitle(ReceivedMessage);

            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
            builder.setShowWhen(true);

            MediaPlayer sound = MediaPlayer.create(getApplicationContext(), R.raw.phone);
            sound.start();

            builder.setAutoCancel(false);
            //builder.setSound(defaultSound);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1, builder.build());
        }

}
