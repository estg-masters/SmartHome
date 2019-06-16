package com.estg.masters.pedwm.smarthome.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //TODO HANDLE NOTIFICATION HERE
    }

    @Override
    public void onNewToken(String s) {
        //TODO HANDLE TOKEN CHANGES HERE
    }
}
