package com.estg.masters.pedwm.smarthome.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.model.House;
import com.estg.masters.pedwm.smarthome.repository.HouseRepository;
import com.estg.masters.pedwm.smarthome.ui.IntentNavigationUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HouseActivity extends AppCompatActivity {

    private House house;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        getHouse();
        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        TextView houseName = findViewById(R.id.house_name_text_view);
        houseName.setText(house.getName());
        Button goToSensors = findViewById(R.id.see_house_sensors_button);
        Button goToRooms = findViewById(R.id.see_house_rooms_button);
        goToSensors.setOnClickListener(v -> goToSensors());
        goToRooms.setOnClickListener(v -> goToRooms());
    }

    private void getHouse() {
        house = (House) this.getIntent().getSerializableExtra("house");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToActivity(Class activityToGo, Map<String, Serializable> extras) {
        IntentNavigationUtils.goToActivitySerializable(HouseActivity.this, activityToGo, extras);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToSensors() {
        Map<String, Serializable> extras = new HashMap<>();
        extras.put("source", house);
        goToActivity(SensorsActivity.class, extras);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void goToRooms() {
        Map<String, Serializable> extras = new HashMap<>();
        extras.put("source", house);
        goToActivity(RoomsActivity.class, extras);
    }
}
