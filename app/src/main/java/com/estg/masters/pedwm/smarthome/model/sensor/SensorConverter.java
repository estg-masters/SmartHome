package com.estg.masters.pedwm.smarthome.model.sensor;

import com.google.firebase.database.DataSnapshot;

public class SensorConverter {

    public static Sensor fromSnapshot(DataSnapshot sensorSnapshot) {
        if(isNumberSensor(sensorSnapshot)) {
            return numberSensorOf(sensorSnapshot);
        } else {
            return booleanSensorOf(sensorSnapshot);
        }
    }

    private static Sensor booleanSensorOf(DataSnapshot sensorSnapshot) {
        return Sensor.Builder
                .aSensor()
                .withId(sensorSnapshot.child("key").getValue().toString())
                .withName(sensorSnapshot.child("name").getValue().toString())
                .withSourceId(sensorSnapshot.child("sourceId").getValue().toString())
                .withHouseId(sensorSnapshot.child("houseId").getValue().toString())
                .withValue(Boolean.valueOf(sensorSnapshot.child("on").getValue().toString()))
                .build();

    }

    private static Sensor numberSensorOf(DataSnapshot sensorSnapshot) {
        return NumberSensor.Builder
                .aSensor()
                .withNumberValue(Float.valueOf(sensorSnapshot.child("value").getValue().toString()))
                .withId(sensorSnapshot.child("key").getValue().toString())
                .withName(sensorSnapshot.child("name").getValue().toString())
                .withSourceId(sensorSnapshot.child("sourceId").getValue().toString())
                .withHouseId(sensorSnapshot.child("houseId").getValue().toString())
                .withValue(Boolean.valueOf(sensorSnapshot.child("on").getValue().toString()))
                .build();
    }

    private static boolean isNumberSensor(DataSnapshot sensorSnapshot) {
        return sensorSnapshot.hasChild("value");
    }
}
