package com.estg.masters.pedwm.smarthome.model.sensor;

import org.json.JSONException;
import org.json.JSONObject;

public class NumberSensor extends AbstractSensor{
    private float value;

    public NumberSensor(String id, String name, String houseId, float value, String sourceId) {
        super(id, name, houseId, sourceId);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return super.toJsonObject()
                .put("value", this.value);
    }

    public static class Builder extends AbstractSensor.Builder {
        private float value;

        public Builder withValue(float value) {
            this.value = value;
            return this;
        }

        public NumberSensor build() {
            return new NumberSensor(id, name, houseId, value, sourceId);
        }
    }
}
