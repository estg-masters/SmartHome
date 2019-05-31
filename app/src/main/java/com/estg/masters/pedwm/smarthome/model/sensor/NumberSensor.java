package com.estg.masters.pedwm.smarthome.model.sensor;

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


    public static class Builder extends Sensor.Builder {
        private NumberSensor sensorUnderConstruction;

        public Builder() {
            this.sensorUnderConstruction = new NumberSensor();
        }

        public static Builder aNumberSensor() {
            return new Builder();
        }

        public Builder withNumberValue(float value) {
            sensorUnderConstruction.setValue(value);
            return this;
        }

        public NumberSensor build() {
            return sensorUnderConstruction;
        }
    }
}
