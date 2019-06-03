package com.estg.masters.pedwm.smarthome.model.notification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class BooleanAbstractNotification extends AbstractNotification {
    private boolean value;

    public BooleanAbstractNotification(String id, String userId, String sensorId,
                                       boolean value) {
        super(id, NotificationType.BOOLEAN, userId, sensorId);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return super.toJsonObject().put("value", getValue());
    }

    public static class Builder {
        String id;
        NotificationType type;
        String userId;
        String sensorId;
        boolean value;

        public static Builder aNotification() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withNewId() {
            this.id = UUID.randomUUID().toString();
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withSensorId(String sensorId) {
            this.sensorId = sensorId;
            return this;
        }

        public Builder withValue(boolean value) {
            this.value = value;
            return this;
        }

        public BooleanAbstractNotification build(){
            return new BooleanAbstractNotification(id, userId, sensorId, value);
        }
    }
}
