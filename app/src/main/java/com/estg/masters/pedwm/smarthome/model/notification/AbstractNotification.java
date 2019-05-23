package com.estg.masters.pedwm.smarthome.model.notification;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractNotification {
    private String id;
    private NotificationType type;
    private String userId;
    private String sensorId;

    public AbstractNotification(String id, NotificationType type, String userId, String sensorId) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.sensorId = sensorId;
    }

    public String getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getSensorId() {
        return sensorId;
    }

    JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("id", getId())
                .put("type", getType().toString())
                .put("userId", getUserId())
                .put("sensorId", getSensorId());
    }

    public abstract JSONObject toJson() throws JSONException;

    static class Builder {
        String id;
        NotificationType type;
        String userId;
        String sensorId;

        public static Builder aNotification() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withType(NotificationType type) {
            this.type = type;
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
    }
}
