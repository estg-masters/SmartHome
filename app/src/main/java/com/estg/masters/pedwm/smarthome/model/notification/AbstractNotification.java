package com.estg.masters.pedwm.smarthome.model.notification;

import com.estg.masters.pedwm.smarthome.model.JsonModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public abstract class AbstractNotification implements JsonModel {
    private String key;
    private NotificationType type;
    private String userId;
    private String sensorId;

    public AbstractNotification(String key, NotificationType type, String userId, String sensorId) {
        this.key = key;
        this.type = type;
        this.userId = userId;
        this.sensorId = sensorId;
    }

    public String getKey() {
        return key;
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

    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("key", getKey())
                .put("type", getType().toString())
                .put("userId", getUserId())
                .put("sensorId", getSensorId());
    }
}
