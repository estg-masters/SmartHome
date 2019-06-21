package com.estg.masters.pedwm.smarthome.model.notification;

import java.util.UUID;

public class NumberNotification extends AbstractNotification {
    private float number;
    private ComparingTypeEnum comparingTypeEnum;

    public NumberNotification(String id, String userId, String sensorId, float number,
                              ComparingTypeEnum comparingTypeEnum) {
        super(id, NotificationTypeEnum.NUMBER, userId, sensorId);
        this.number = number;
        this.comparingTypeEnum = comparingTypeEnum;
    }

    public float getNumber() {
        return number;
    }

    public ComparingTypeEnum getComparingTypeEnum() {
        return comparingTypeEnum;
    }

    public static class Builder {
        private String id;
        private String sensorId;
        private String userId;
        private float value;
        private ComparingTypeEnum comparingTypeEnum;

        public static Builder aNotification() {
            return new Builder();
        }

        public Builder withValue(float value) {
            this.value = value;
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

        public Builder withComparingType(ComparingTypeEnum comparingTypeEnum) {
            this.comparingTypeEnum = comparingTypeEnum;
            return this;
        }

        public Builder withSensorId(String sensorId) {
            this.sensorId = sensorId;
            return this;
        }

        public NumberNotification build() {
            return new NumberNotification(id, userId, sensorId, value, comparingTypeEnum);
        }
    }

}
