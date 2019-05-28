package com.estg.masters.pedwm.smarthome.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.model.sensor.AbstractSensor;
import com.estg.masters.pedwm.smarthome.model.sensor.BooleanSensor;
import com.estg.masters.pedwm.smarthome.repository.SensorRepository;
import com.estg.masters.pedwm.smarthome.ui.SensorViewFactory;

public class SensorsActivity extends AppCompatActivity {
    private String sourceId;
    private String m_Text;

    private LinearLayout sensors_layout;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        builder.setTitle("Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            m_Text = input.getText().toString();
            AbstractSensor sensor = BooleanSensor.Builder
                    .aSensor()
                    .withNewId()
                    .withSourceId(sourceId)
                    .withHouseId(sourceId) //todo
                    .withName(m_Text)
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
