package com.estg.masters.pedwm.smarthome.services;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.repository.NotificationRepository;
import com.estg.masters.pedwm.smarthome.repository.SensorRepository;
import com.estg.masters.pedwm.smarthome.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().get("userKey").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            getNotificationData(remoteMessage);
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d("REFRESH TOKEN", token);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            DatabaseReference userTokensReference = UserRepository.getInstance().getReference().child(mAuth.getCurrentUser().getUid()).child("tokens");

            userTokensReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<String> tokens = getUserTokens(dataSnapshot);

                    if (!tokens.contains(token)) {
                        tokens.add(token);
                    }

                    userTokensReference.setValue(tokens);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("GET USER ERROR", databaseError.toException().toString());
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<String> getUserTokens(DataSnapshot snapshot) {
        return Objects.isNull(snapshot) ? new ArrayList<>() : (List<String>) snapshot.getValue();
    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getNotificationData(RemoteMessage remoteMessage) {
        SensorRepository.getSensorRef().child(remoteMessage.getData().get("sensorKey"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot sensorSnapshot) {
                        NotificationRepository.getInstance().getReference().child(remoteMessage
                                .getData().get("notificationKey")).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot notificationSnapshot) {
                                        buildAndDisplayNotification(sensorSnapshot, notificationSnapshot);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.e("ERROR", "RETREIVING SENSOR");
                                    }
                                }
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("ERROR", "RETREIVING SENSOR");
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildAndDisplayNotification(DataSnapshot sensorSnapshot, DataSnapshot notificationSnapshot) {
        final String notificationChannelId = "ChannerSmartHome";
        final String sensorName = sensorSnapshot.child("name").getValue().toString();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                notificationChannelId)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle(buildNotificationTitle(sensorName))
                .setContentText(buildNotificationContnet(sensorName, notificationSnapshot))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationChannel channel = new NotificationChannel(notificationChannelId, "SmartHome",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(new Random().nextInt(10000), builder.build());
    }

    private String buildNotificationTitle(String sensorName) {
        return "SmartHome - " + sensorName;
    }

    private String buildNotificationContnet(String sensorName, DataSnapshot notificationSnapshot) {
        if(notificationSnapshot.child("type").getValue().equals("BOOLEAN")) {
            String sensorPolarity = notificationSnapshot.child("type").getValue().equals("true") ?
                    "ON" : "OFF";
                return "The sensor '" + sensorName + "' is now " + sensorPolarity;
        } else {
            switch (notificationSnapshot.child("comparingType").getValue().toString()) {
                case "BIGGER":
                    return "The sensor '" + sensorName + "' has a value bigger than " +
                            notificationSnapshot.child("number").getValue().toString();
                case "EQUALS":
                    return "The sensor '" + sensorName + "' has a value equals than " +
                            notificationSnapshot.child("number").getValue().toString();
                case "LESSER":
                    return "The sensor '" + sensorName + "' has a value lesser than " +
                            notificationSnapshot.child("number").getValue().toString();
                default:
                    return "";
            }
        }
    }
}
