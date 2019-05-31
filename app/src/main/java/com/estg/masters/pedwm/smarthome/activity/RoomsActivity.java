package com.estg.masters.pedwm.smarthome.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.model.House;
import com.estg.masters.pedwm.smarthome.model.Room;
import com.estg.masters.pedwm.smarthome.repository.RoomRepository;
import com.estg.masters.pedwm.smarthome.ui.IntentNavigationUtils;
import com.estg.masters.pedwm.smarthome.ui.RoomViewFactory;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;
import java.util.Map;

public class RoomsActivity extends AppCompatActivity {


    private LinearLayout roomsLayout;

    private Map<String, View> roomsInView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        RoomRepository.getReference(getHouseId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addRoomToView(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // todo update
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                roomsLayout.removeView(roomsInView.remove(dataSnapshot.getKey()));
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
    private void addRoomToView(DataSnapshot dataSnapshot) {
        Room room = RoomConverter.fromSnapshot(dataSnapshot);
        addRoomToView(room);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initActivity() {
        setContentView(R.layout.activity_rooms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addRoom());

        roomsInView = new HashMap<>();
        roomsLayout = findViewById(R.id.roomsContent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addRoom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Room");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String inputText = input.getText().toString();
            Room room = Room.Builder
                    .aRoom()
                    .withNewId()
                    .withName(inputText)
                    .withHouseId(getHouseId())
                    .build();

            RoomRepository.getInstance().save(room.getKey(), room);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addRoomToView(Room room) {
        View roomView = RoomViewFactory.makeView(room, this, null /* todo delete room */);
        roomView.setOnClickListener(v -> goToSensors(room));
        roomsLayout.addView(roomView);
        roomsInView.put(room.getKey(), roomView);
    }


    private String getHouseId() {
        return ((House) getIntent().getSerializableExtra("source")).getKey();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToSensors(Room room) {
        Map<String, String> extras = new HashMap<>();
        extras.put("sourceId", room.getKey());
        IntentNavigationUtils.goToActivity(RoomsActivity.this, SensorsActivity.class, extras);
    }

}
