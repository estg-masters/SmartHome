package com.estg.masters.pedwm.smarthome.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.model.House;
import com.estg.masters.pedwm.smarthome.repository.HouseRepository;
import com.estg.masters.pedwm.smarthome.ui.IntentNavigationUtils;
import com.google.firebase.database.collection.ImmutableSortedMap;

import java.util.HashMap;
import java.util.Map;

public class HouseActivity extends AppCompatActivity {

    private static final HouseRepository houseRepo = HouseRepository.getInstance();

    private String houseId;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        houseId = this.getIntent().getStringExtra("houseId");

        TextView houseName = findViewById(R.id.house_name_text_view);
        houseName.setText(houseRepo.get(houseId).getName());

        Button goToSensors = findViewById(R.id.see_house_sensors_button);
        Button goToRooms = findViewById(R.id.see_house_rooms_button);

        goToSensors.setOnClickListener(v -> goToActivity(SensorsActivity.class));
        goToRooms.setOnClickListener(v -> goToActivity(RoomsActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToActivity(Class activityToGo) {
        Map<String, String> extras = new HashMap<>();
        extras.put("sourceId", houseId);
        extras.put("sourceType", "house");
        IntentNavigationUtils.goToActivity(HouseActivity.this, activityToGo, extras);
    }
}
