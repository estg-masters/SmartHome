package com.estg.masters.pedwm.smarthome.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HousesActivity extends AppCompatActivity {

    private String m_Text = "";
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private HouseRepository houseRepository = HouseRepository.getInstance();

    private Map<String, Integer> housesInView = new HashMap<>();

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();

        HouseRepository.getHouseRef().addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot
                                .getChildren()
                                .forEach(houseSnapshot -> {
                                    if (userCanViewHouse(houseSnapshot))
                                        addHouseToView(houseSnapshot);
                                });
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
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addHouseToView(House house) {
        LinearLayout view = findViewById(R.id.houses_recycler_view);
        if (housesInView.containsKey(house.getKey()))
            view.removeView(findViewById(
                    Optional.ofNullable(housesInView.get(house.getKey())).orElse(-1)));
        View houseView = HouseViewFactory.makeView(house, this, this::goToActivity);
        view.addView(houseView);
        housesInView.put(house.getKey(), houseView.getId());
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
            m_Text = input.getText().toString();
            House house = House.Builder.aHouse()
                    .withNewId()
                    .withName(m_Text)
                    .withAdminId(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .build();
            houseRepository.add(house.getKey(), house);
            addHouseToView(house);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}
