package com.codediffusion.tyidedriver.firebaseNotification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.text.Html;

import androidx.core.app.NotificationCompat;

import com.codediffusion.tyidedriver.R;

import static com.codediffusion.tyidedriver.firebaseNotification.MyFirebaseMessenging.NotificationTitle;
import static com.codediffusion.tyidedriver.firebaseNotification.MyFirebaseMessenging.ReceivedMessage;


public class OreolNotification  extends ContextWrapper {

    public static final String CHANNEL_ID="com.codediffusion.tyidedriver";
    public static final String CHANNEL_NAME="com.codediffusion.tyidedriver";
    public NotificationManager notificationManager;
    public OreolNotification(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {

        NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(false);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager(){
        if (notificationManager==null){
            notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public NotificationCompat.Builder getOreoNotification(String body, PendingIntent pendingIntent, Uri defaultSound) {

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        //Default Sound
        MediaPlayer sound = MediaPlayer.create(getApplicationContext(), R.raw.phone);
        sound.start();


        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(Html.fromHtml("<strong>"+NotificationTitle+"</strong>"));
       // builder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(ReceivedMessage));
       // builder.setContentTitle(ReceivedMessage);
        builder.setSmallIcon(R.drawable.primary_logo);
       // builder.setSound(defaultSound);
        builder.setShowWhen(true);
        builder.setAutoCancel(true);

        return builder;
    }


}
