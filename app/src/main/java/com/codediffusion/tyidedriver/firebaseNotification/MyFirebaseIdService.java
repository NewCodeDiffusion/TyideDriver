package com.codediffusion.tyidedriver.firebaseNotification;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseIdService extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);


    }


}
