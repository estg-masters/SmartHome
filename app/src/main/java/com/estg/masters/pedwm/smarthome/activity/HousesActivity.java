package com.estg.masters.pedwm.smarthome.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.model.House;
import com.estg.masters.pedwm.smarthome.repository.HouseRepository;
import com.estg.masters.pedwm.smarthome.ui.HouseViewFactory;

import java.util.Arrays;
import java.util.List;

public class HousesActivity extends AppCompatActivity {

    private String m_Text = "";

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_house_button);
        fab.setOnClickListener(view -> createHouse());

        House house1 = House.Builder
                .aHouse()
                .withNewId()
                .withAdminId("1")
                .withName("Casa da Praia")
                .build();

        House house2 = House.Builder
                .aHouse()
                .withNewId()
                .withAdminId("1")
                .withName("Casa do Campo")
                .build();

        List<House> houses = Arrays.asList(house1, house2);

        houses.forEach(this::addHouseToView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addHouseToView(House house) {
        LinearLayout recyclerView = findViewById(R.id.houses_recycler_view);
        recyclerView.addView(HouseViewFactory.makeView(house, this, this::goToActivity));
    }

    private void goToActivity(String houseId) {
        Intent intent = new Intent(HousesActivity.this, HouseActivity.class);
        intent.putExtra("houseId", houseId);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createHouse() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            m_Text = input.getText().toString();
            House house = House.Builder.aHouse()
                    .withNewId()
                    .withName(m_Text)
                    //.withAdminId(FirebaseAuth.getInstance().getCurrentUser().getUid()) todo
                    .build();
            HouseRepository.getInstance().add(house.getKey(), house);
            addHouseToView(house);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}
