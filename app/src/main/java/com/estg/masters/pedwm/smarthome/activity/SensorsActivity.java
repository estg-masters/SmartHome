package com.estg.masters.pedwm.smarthome.activity;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.model.sensor.AbstractSensor;
import com.estg.masters.pedwm.smarthome.model.sensor.BooleanSensor;
import com.estg.masters.pedwm.smarthome.model.sensor.SensorConverter;
import com.estg.masters.pedwm.smarthome.repository.SensorRepository;
import com.estg.masters.pedwm.smarthome.ui.SensorViewFactory;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SensorsActivity extends AppCompatActivity {
    private String sourceId;

    private final DatabaseReference sensorRef = SensorRepository.getSensorRef();

    private LinearLayout sensors_layout;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        getSensors();
    }

    private void getSensors() {
        String sourceId = getSourceId();
        sensorRef.orderByChild("sourceId").equalTo(sourceId).addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addSensorToView(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Change the value
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // remove from view
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
    private void addSensorToView(DataSnapshot sensorSnap) {
        addSensorToView(SensorConverter.fromSnapshot(sensorSnap));
    }

    private String getSourceId() {
        return getIntent().getStringExtra("sourceId");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initActivity() {
        setContentView(R.layout.activity_sensors);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.sourceId = getIntent().getStringExtra("sourceId");

        sensors_layout = findViewById(R.id.sensors_layout);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addSensor());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addSensor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add sensor");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String inputText = input.getText().toString();
            AbstractSensor sensor = BooleanSensor.Builder
                    .aSensor()
                    .withNewId()
                    .withSourceId(sourceId)
                    .withHouseId(sourceId) //todo
                    .withName(inputText)
                    .withValue(false)
                    .build();

            SensorRepository.getInstance().add(sensor.getKey(), sensor);
            addSensorToView(sensor);
        });

        // todo: add sensors from repo
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addSensorToView(AbstractSensor sensor) {
        sensors_layout.addView(SensorViewFactory.makeView(sensor, this, viewId -> updateSensor(viewId, sensor)));
    }

    private void updateSensor(int viewId, AbstractSensor sensor) {
        BooleanSensor sensor1 = (BooleanSensor) sensor;
        sensor1.setValue(!sensor1.isOn());
        ((TextView) findViewById(viewId)).setText(sensor1.isOn() ? "On" : "Off");
    }
}
