package com.estg.masters.pedwm.smarthome.model.sensor;

import org.json.JSONException;
import org.json.JSONObject;

public class BooleanSensor extends AbstractSensor {
    private boolean value;

    public BooleanSensor(String id, String name, String houseId, boolean value, String sourceId) {
        super(id, name, houseId, sourceId);
        this.value = value;
    }

    public boolean isOn() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return super.toJsonObject().put("value", value);
    }

    public static class Builder extends AbstractSensor.Builder {
        private boolean value;

        public static Builder aSensor() {
            return new Builder();
        }

        public Builder withValue(boolean value) {
            this.value = value;
            return this;
        }

        @Override
        public Builder withId(String id) {
            return (Builder) super.withId(id);
        }

        @Override
        public Builder withNewId() {
            return (Builder) super.withNewId();
        }

        @Override
        public Builder withName(String name) {
            return (Builder) super.withName(name);
        }

        @Override
        public Builder withHouseId(String houseId) {
            return (Builder) super.withHouseId(houseId);
        }

        @Override
        public Builder withSourceId(String sourceId) {
            return (Builder) super.withSourceId(sourceId);
        }

        public BooleanSensor build() {
            return new BooleanSensor(id, name, houseId, value, sourceId);
        }
    }
}
