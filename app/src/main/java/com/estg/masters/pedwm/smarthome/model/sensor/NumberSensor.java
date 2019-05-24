package com.estg.masters.pedwm.smarthome.model.sensor;

import org.json.JSONException;
import org.json.JSONObject;

public class NumberSensor extends AbstractSensor{
    private float value;

    public NumberSensor(String id, String name, String houseId, float value) {
        super(id, name, houseId);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return null;
    }

    public static class Builder extends AbstractSensor.Builder {
        private float value;

        public Builder withValue(float value) {
            this.value = value;
            return this;
        }

        public NumberSensor build() {
            return new NumberSensor(id, name, houseId, value);
        }
    }
}
