package com.estg.masters.pedwm.smarthome.activity;

import android.annotation.TargetApi;
import android.content.Intent;
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
import com.estg.masters.pedwm.smarthome.repository.HouseRepository;
import com.estg.masters.pedwm.smarthome.ui.HouseViewFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HousesActivity extends AppCompatActivity {

    private String inputText = "";
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private HouseRepository houseRepository = HouseRepository.getInstance();

    private LinearLayout housesView;

    private Map<String, View> housesInView = new HashMap<>();

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();

        HouseRepository.getHouseRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (userCanViewHouse(dataSnapshot))
                    addHouseToView(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                onChildRemoved(dataSnapshot);
                onChildAdded(dataSnapshot, s);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                housesView.removeView(housesInView.get(dataSnapshot.getKey()));
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean userCanViewHouse(DataSnapshot houseSnapshot) {
        return Optional.ofNullable(houseSnapshot.child("adminId").getValue()).orElse("")
                .toString().equals(currentUser.getUid());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initActivity() {
        setContentView(R.layout.activity_houses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.add_house_button);
        fab.setOnClickListener(view -> createHouse());
        housesView = findViewById(R.id.houses_recycler_view);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addHouseToView(House house) {
        View houseView = HouseViewFactory.makeView(house, this, this::goToActivity);
        housesView.addView(houseView);
        housesInView.put(house.getKey(), houseView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addHouseToView(DataSnapshot houseSnapshot) {
        addHouseToView(House.fromSnapshot(houseSnapshot));
    }

    private void goToActivity(House house) {
        Intent intent = new Intent(HousesActivity.this, HouseActivity.class);
        intent.putExtra("house", house);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createHouse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add House");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            inputText = input.getText().toString();
            House house = House.Builder.aHouse()
                    .withNewId()
                    .withName(inputText)
                    .withAdminId(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .build();
            houseRepository.save(house.getKey(), house);
            addHouseToView(house);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}
