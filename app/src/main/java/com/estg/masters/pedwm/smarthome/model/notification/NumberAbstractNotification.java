package com.estg.masters.pedwm.smarthome.model.notification;

import org.json.JSONException;
import org.json.JSONObject;

public class NumberAbstractNotification extends AbstractNotification {
    private float number;
    private ComparingType comparingType;

    public NumberAbstractNotification(String id, NotificationType type, String userId, String sensorId,
                                      float number, ComparingType comparingType) {
        super(id, type, userId, sensorId);
        this.number = number;
        this.comparingType = comparingType;
    }

    public float getNumber() {
        return number;
    }

    public ComparingType getComparingType() {
        return comparingType;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return super.toJsonObject()
                .put("value", getNumber())
                .put("comparator", getComparingType().toString());
    }

    static class Builder extends AbstractNotification.Builder {
        private float value;
        private ComparingType comparingType;

        public Builder withValue(float value) {
            this.value = value;
            return this;
        }

        public Builder withComparingType(ComparingType comparingType) {
            this.comparingType = comparingType;
            return this;
        }

        public NumberAbstractNotification build() {
            return new NumberAbstractNotification(id, type, userId, sensorId, value, comparingType);
        }
    }

}
