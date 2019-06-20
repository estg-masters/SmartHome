package com.estg.masters.pedwm.smarthome.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.repository.NotificationRepository;
import com.estg.masters.pedwm.smarthome.repository.SensorRepository;
import com.estg.masters.pedwm.smarthome.ui.NotificationViewFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class NotificationsActivity extends AppCompatActivity {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private NotificationRepository notificationRepository = NotificationRepository.getInstance();

    public LinearLayout notificationsView;
    private Map<String, View> notificationsInView = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActivity();
        initEventListenerForNotifications();
    }

    private void initActivity() {
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notificationsView = findViewById(R.id.notificationsContent);
    }

    private void initEventListenerForNotifications() {
        notificationRepository.getReference().addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(userCanSeeNotification(dataSnapshot)){
                    addNotificationToView(dataSnapshot);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                onChildRemoved(dataSnapshot);
                onChildAdded(dataSnapshot, s);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                notificationsView.removeView(notificationsInView.get(dataSnapshot.getKey()));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error", "Database onCancelled", databaseError.toException());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addNotificationToView(DataSnapshot dataSnapshot) {
        getSensorName(dataSnapshot.child("sensorId").getValue().toString(), sensorName -> {
            View view = NotificationViewFactory.makeView(dataSnapshot, sensorName, this,
                    v -> NotificationRepository.getInstance().getReference()
                            .child(dataSnapshot.getKey()).removeValue(
                                    (databaseError, databaseReference) ->
                                            Log.e("R_NOTIFICATION",
                                                    "Successfully removed notification")
                            )
                    );

            notificationsInView.put(dataSnapshot.getKey(), view);
            notificationsView.addView(view);
        });
    }

    private boolean userCanSeeNotification(DataSnapshot notification) {
        return notification.child("userId").getValue().equals(currentUser.getUid());
    }

    private void getSensorName(String sensorId, Consumer<String> consumer){
        SensorRepository.getSensorRef().child(sensorId).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consumer.accept(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("SENSOR REPO", "Failed to get sensor");
            }
        });
    }

}
