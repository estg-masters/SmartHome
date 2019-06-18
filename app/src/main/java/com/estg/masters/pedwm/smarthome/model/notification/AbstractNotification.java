package com.estg.masters.pedwm.smarthome.model.notification;

public abstract class AbstractNotification {
    private String key;
    private NotificationTypeEnum type;
    private String userId;
    private String sensorId;

    public AbstractNotification(String key, NotificationTypeEnum type, String userId, String sensorId) {
        this.key = key;
        this.type = type;
        this.userId = userId;
        this.sensorId = sensorId;
    }

    public String getKey() {
        return key;
    }

    public NotificationTypeEnum getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getSensorId() {
        return sensorId;
    }

}
