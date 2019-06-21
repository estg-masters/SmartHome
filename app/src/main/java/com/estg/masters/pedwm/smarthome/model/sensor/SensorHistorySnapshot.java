package com.estg.masters.pedwm.smarthome.model.sensor;

import java.util.Calendar;
import java.util.UUID;

public class SensorHistorySnapshot extends NumberSensor {

    private Calendar date;

    public SensorHistorySnapshot(String id, String name, String houseId, boolean isOn, String sourceId, float value) {
        super(id, name, houseId, isOn, sourceId, value);
        this.date = Calendar.getInstance();
    }

    public SensorHistorySnapshot() {
        super();
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public static class Builder {
        private SensorHistorySnapshot sensorUnderConstruction;

        public Builder() {
            this.sensorUnderConstruction = new SensorHistorySnapshot();
        }

        public static SensorHistorySnapshot.Builder aSensor() {
            return new SensorHistorySnapshot.Builder();
        }

        public SensorHistorySnapshot.Builder withNumberValue(float value) {
            sensorUnderConstruction.setValue(value);
            return this;
        }

        public SensorHistorySnapshot.Builder withValue(float value) {
            sensorUnderConstruction.setValue(value);
            return this;
        }

        public SensorHistorySnapshot.Builder withDate(Calendar calendar) {
            sensorUnderConstruction.setDate(calendar);
            return this;
        }

        public SensorHistorySnapshot.Builder withId(String id) {
            sensorUnderConstruction.setId(id);
            return this;
        }

        public SensorHistorySnapshot.Builder withNewId() {
            sensorUnderConstruction.setId(UUID.randomUUID().toString());
            return this;
        }

        public SensorHistorySnapshot.Builder withName(String name) {
            sensorUnderConstruction.setName(name);
            return this;
        }

        public SensorHistorySnapshot.Builder withHouseId(String houseId) {
            sensorUnderConstruction.setHouseId(houseId);
            return this;
        }

        public SensorHistorySnapshot.Builder withSourceId(String sourceId) {
            sensorUnderConstruction.setSourceId(sourceId);
            return this;
        }

        public SensorHistorySnapshot.Builder withOn(boolean isOn) {
            sensorUnderConstruction.setOn(isOn);
            return this;
        }

        public SensorHistorySnapshot build() {
            return sensorUnderConstruction;
        }
    }
}
