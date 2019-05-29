package com.estg.masters.pedwm.smarthome.model.sensor;

import org.json.JSONException;
import org.json.JSONObject;

public class NumberSensor extends BooleanSensor {
    private float value;

    public NumberSensor(String id, String name, String houseId, boolean isOn, String sourceId, float value) {
        super(id, name, houseId, isOn, sourceId);
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

    public static class Builder extends BooleanSensor.Builder {
        private float number;

        public static Builder aNumberSensor() {
            return new Builder();
        }

        public Builder withNumberValue(float value) {
            this.number = value;
            return this;
        }

        public NumberSensor build() {
            return new NumberSensor(id, name, houseId, value, sourceId, number);
        }
    }
}
