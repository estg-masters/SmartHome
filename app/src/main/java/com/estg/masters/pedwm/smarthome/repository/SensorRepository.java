package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.sensor.AbstractSensor;

public class SensorRepository extends AbstractRepository<AbstractSensor> {

    private static SensorRepository INSTANCE;

    private SensorRepository() {
        super();
    }

    public static SensorRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SensorRepository();
        return INSTANCE;
    }
}
