package com.estg.masters.pedwm.smarthome.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.estg.masters.pedwm.smarthome.model.User;
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

}
