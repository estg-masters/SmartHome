package com.estg.masters.pedwm.smarthome.activity;

import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.model.House;
import com.estg.masters.pedwm.smarthome.model.Room;
import com.estg.masters.pedwm.smarthome.model.sensor.NumberSensor;
import com.estg.masters.pedwm.smarthome.model.sensor.Sensor;
import com.estg.masters.pedwm.smarthome.model.converters.SensorConverter;
import com.estg.masters.pedwm.smarthome.repository.SensorRepository;
import com.estg.masters.pedwm.smarthome.ui.SensorViewFactory;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SensorsActivity extends AppCompatActivity {
    private Map<String, View> sensorsInView = new HashMap<>();

    private final DatabaseReference sensorRef = SensorRepository.getSensorRef();

    private LinearLayout sensorsLayout;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        getSensors();
    }

    private void getSensors() {
        String sourceId = getSourceId();
        String sourceIdType = getSourceType();
        sensorRef.orderByChild(sourceIdType).equalTo(sourceId).addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addSensorToView(dataSnapshot);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                onChildRemoved(dataSnapshot);
                onChildAdded(dataSnapshot, s);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                sensorsLayout.removeView(sensorsInView.remove(dataSnapshot.getKey()));
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

    private Switch getSwitch(@NonNull DataSnapshot dataSnapshot) {
        LinearLayout linearLayout = (LinearLayout) sensorsInView.get(dataSnapshot.getKey());
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof Switch)
                return (Switch) linearLayout.getChildAt(i);
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addSensorToView(DataSnapshot sensorSnap) {
        addSensorToView(SensorConverter.fromSnapshot(sensorSnap));
    }

    private String getSourceType() {
        return getIntent().getSerializableExtra("source") instanceof House ?
                "houseId" : "sourceId";
    }

    private String getSourceId() {
        Serializable source = getIntent().getSerializableExtra("source");
        if (source instanceof House) {
            return ((House) source).getKey();
        } else {
            return ((Room) source).getKey();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initActivity() {
        setContentView(R.layout.activity_sensors);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sensorsLayout = findViewById(R.id.sensors_layout);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addSensor());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addSensor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add sensor");

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Sensor Name");

        final EditText inputValue = new EditText(this);
        inputValue.setInputType(InputType.TYPE_NULL);
        inputValue.setHint("Sensor Numeric Value");

        final CheckBox isNumeric = new CheckBox(this);
        isNumeric.setText("Has numeric value");
        isNumeric.setChecked(false);
        isNumeric.setOnClickListener(v ->
                inputValue.setInputType(((CheckBox) v).isChecked()
                        ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_NULL));

        layout.addView(input);
        layout.addView(isNumeric);
        layout.addView(inputValue);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String inputText = input.getText().toString();
            String inputValueString = inputValue.getText().toString();
            float value = Float.parseFloat(inputValueString.equals("") ? "0" : inputValueString);
            Sensor sensor;
            if (isNumeric.isChecked()) {
                sensor = NumberSensor.Builder
                        .aSensor()
                        .withNumberValue(value)
                        .withNewId()
                        .withSourceId(getSourceId())
                        .withHouseId(getHouseId())
                        .withName(inputText)
                        .withValue(false)
                        .build();
            } else {
                sensor = Sensor.Builder
                        .aSensor()
                        .withNewId()
                        .withSourceId(getSourceId())
                        .withHouseId(getHouseId())
                        .withName(inputText)
                        .withValue(false)
                        .build();
            }


            SensorRepository.getInstance().save(sensor.getKey(), sensor);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private String getHouseId() {
        Serializable source = getIntent().getSerializableExtra("source");
        if (source instanceof House) {
            return ((House) source).getKey();
        } else {
            return ((Room) source).getHouseId();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addSensorToView(Sensor sensor) {
        View view = getSensorView(sensor);
        sensorsLayout.addView(view);
        sensorsInView.put(sensor.getKey(), view);
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private View getSensorView(Sensor sensor) {
        return SensorViewFactory.makeView(sensor, this, v -> switchSensor(sensor),
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE ||
                            actionId == EditorInfo.IME_ACTION_NEXT) {
                        updateSensorAndView(sensor, v, actionId);
                        return false;
                    }
                    return false;
                });
    }

    private void updateSensorAndView(Sensor sensor, TextView v, int actionId) {
        ((NumberSensor) sensor).setValue(Float.valueOf(v.getText().toString()));
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            v.clearFocus();
            hideSoftKeyboard(SensorsActivity.this, v);
        }
        updateSensor(sensor);
    }

    private void switchSensor(Sensor sensor) {
        sensor.setOn(!sensor.isOn());
        updateSensor(sensor);
    }

    private void updateSensor(Sensor sensor) {
        SensorRepository.getInstance().save(sensor.getKey(), sensor);
    }
}
