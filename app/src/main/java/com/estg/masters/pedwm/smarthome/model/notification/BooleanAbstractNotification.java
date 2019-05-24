package com.estg.masters.pedwm.smarthome.model.notification;

import org.json.JSONException;
import org.json.JSONObject;

public class BooleanAbstractNotification extends AbstractNotification {
    private boolean value;

    public BooleanAbstractNotification(String id, NotificationType type, String userId, String sensorId,
                                       boolean value) {
        super(id, type, userId, sensorId);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return super.toJsonObject().put("value", getValue());
    }

    static class Builder extends AbstractNotification.Builder{
        private boolean value;

        public Builder withValue(boolean value) {
            this.value = value;
            return this;
        }

        public BooleanAbstractNotification build() {
            return new BooleanAbstractNotification(id, type, userId, sensorId, value);
        }
    }
}
