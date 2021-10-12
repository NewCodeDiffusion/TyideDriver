package com.codediffusion.tyidedriver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.walkthrough.IntroActivity;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener  {

    Animation animFadeIn;
    LinearLayout linearLayout;
    ActionBar actionBar;
    private String status,DriverID;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  prefs = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
       if (prefs.contains(CommonData.DriverID)){
           DriverID=prefs.getString(CommonData.DriverID,"");

           Log.e("kjhgfd",DriverID+"");
        }
        status = prefs.getString("loginstatus", "");

        Log.e("kjhgfd",status+"");


        actionBar= getSupportActionBar();
        actionBar.hide();

        changestatusbarcolor();
        if(Build.VERSION.SDK_INT>=21){
            /*getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN);*/
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
        }

        // load the animation
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation_fade_in);
        // set animation listener
        animFadeIn.setAnimationListener(this);
        // animation for image
        linearLayout = (LinearLayout) findViewById(R.id.layout_linear);
        // start the animation
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.startAnimation(animFadeIn);



        createNotificationChannel();




    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changestatusbarcolor(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        /*// Start Main Screen
        SharedPreferences ss=getSharedPreferences("session",MODE_PRIVATE);
        if (Integer.parseInt(ss.getString("Act","0"))==1){
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }else {
            Intent i = new Intent(this, IntroActivity.class);
            startActivity(i);

        }*/

        if (status.contains("true")){
           /* if (!DriverID.equals("")){
                Log.e("ghgghghgh","1");
                Intent i = new Intent(this, Home.class);
                startActivity(i);
                finishAffinity();
            }else{
                Log.e("ghgghghgh","2");

                Intent i = new Intent(this, Login.class);
                startActivity(i);
                finishAffinity();
            }
*/
        }else {
            Intent i = new Intent(this, IntroActivity.class);
            startActivity(i);
            finishAffinity();
        }


    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        Uri sound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.phone);
        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final long[] VIBRATE_PATTERN = {0, 500};

            mChannel = new NotificationChannel("videocall", "VIDEO CALL", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setLightColor(Color.GRAY);
            mChannel.setVibrationPattern(VIBRATE_PATTERN);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setDescription("VIDEO CALL");
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            mChannel.setSound(sound, audioAttributes);
            NotificationManager notificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);

        }

    }


}