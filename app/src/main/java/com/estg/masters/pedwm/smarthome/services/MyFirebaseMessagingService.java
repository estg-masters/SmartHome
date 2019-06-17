package com.estg.masters.pedwm.smarthome.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.estg.masters.pedwm.smarthome.model.User;
import com.estg.masters.pedwm.smarthome.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //TODO HANDLE NOTIFICATION HERE
    }



    @Override
    public void onNewToken(String token) {
        Log.d("REFRESH TOKEN", token);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            UserRepository.getInstance().getByField("key", FirebaseAuth.getInstance()
                    .getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    saveTokenOnRealTimeDB(dataSnapshot, token);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("", "Failed to load specified user");
                }
            });
        } else {
            PreferenceManager preferenceManager = PreferenceManager.getInstance(getApplicationContext());
            saveTokenOnPreferences(token);

            System.out.println(preferenceManager.getValue("FMC"));
        }
    }

    private void saveTokenOnRealTimeDB(DataSnapshot dataSnapshot, String token) {
        User user = (User) dataSnapshot.getValue();
        user.getTokens().add(token);

        UserRepository.getInstance().save(FirebaseAuth.getInstance().getUid(), user);
    }

    private void saveTokenOnPreferences(String token) {
        PreferenceManager preferenceManager = PreferenceManager.getInstance(getApplicationContext());
        preferenceManager.storeValue("FMC", token);
    }


}
