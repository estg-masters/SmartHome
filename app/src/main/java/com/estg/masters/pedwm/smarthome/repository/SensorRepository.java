package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.sensor.Sensor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SensorRepository extends AbstractRepository<Sensor> {

    private static SensorRepository INSTANCE;
    private static final DatabaseReference sensorRef = FirebaseDatabase.getInstance()
            .getReference("sensor");

    private SensorRepository() {
        super(sensorRef);
    }

    public static DatabaseReference getSensorRef() {
        return sensorRef;
    }

    public static SensorRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SensorRepository();
        return INSTANCE;
    }
}
