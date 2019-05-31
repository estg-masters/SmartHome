package com.estg.masters.pedwm.smarthome.model.sensor;

import java.util.UUID;

public class NumberSensor extends Sensor {
    private float value;

    public NumberSensor(String id, String name, String houseId, boolean isOn, String sourceId, float value) {
        super(id, name, houseId, sourceId, isOn);
        this.value = value;
    }

    public NumberSensor() {
        super();
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }


    public static class Builder {
        private NumberSensor sensorUnderConstruction;

        public Builder() {
            this.sensorUnderConstruction = new NumberSensor();
        }

        public static Builder aSensor() {
            return new Builder();
        }

        public Builder withNumberValue(float value) {
            sensorUnderConstruction.setValue(value);
            return this;
        }

        public Builder withValue(boolean value) {
            sensorUnderConstruction.setOn(value);
            return this;
        }

        public Builder withId(String id) {
            sensorUnderConstruction.setId(id);
            return this;
        }

        public Builder withNewId() {
            sensorUnderConstruction.setId(UUID.randomUUID().toString());
            return this;
        }

        public Builder withName(String name) {
            sensorUnderConstruction.setName(name);
            return this;
        }

        public Builder withHouseId(String houseId) {
            sensorUnderConstruction.setHouseId(houseId);
            return this;
        }

        public Builder withSourceId(String sourceId) {
            sensorUnderConstruction.setSourceId(sourceId);
            return this;
        }

        public NumberSensor build() {
            return sensorUnderConstruction;
        }
    }
}
