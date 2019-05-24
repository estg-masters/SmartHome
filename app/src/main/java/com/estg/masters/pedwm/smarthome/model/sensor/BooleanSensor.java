package com.estg.masters.pedwm.smarthome.model.sensor;

import org.json.JSONException;
import org.json.JSONObject;

public class BooleanSensor extends AbstractSensor {
    private boolean value;

    public BooleanSensor(String id, String name, String houseId, boolean value) {
        super(id, name, houseId);
        this.value = value;
    }

    public boolean isOn() {
        return value;
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return super.toJsonObject().put("value", value);
    }

    public static class Builder extends AbstractSensor.Builder {
        private boolean value;

        public Builder withValue(boolean value) {
            this.value = value;
            return this;
        }

        public BooleanSensor build() {
            return new BooleanSensor(id, name, houseId, value);
        }
    }
}
