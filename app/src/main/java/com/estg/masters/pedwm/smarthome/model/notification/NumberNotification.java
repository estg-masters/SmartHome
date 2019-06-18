package com.estg.masters.pedwm.smarthome.model.notification;

public class NumberNotification extends AbstractNotification {
    private float number;
    private ComparingTypeEnum comparingTypeEnum;

    public NumberNotification(String id, NotificationTypeEnum type, String userId, String sensorId,
                              float number, ComparingTypeEnum comparingTypeEnum) {
        super(id, type, userId, sensorId);
        this.number = number;
        this.comparingTypeEnum = comparingTypeEnum;
    }

    public float getNumber() {
        return number;
    }

    public ComparingTypeEnum getComparingTypeEnum() {
        return comparingTypeEnum;
    }

    static class Builder {
        private float value;
        private ComparingTypeEnum comparingTypeEnum;

        public Builder withValue(float value) {
            this.value = value;
            return this;
        }

        public Builder withComparingType(ComparingTypeEnum comparingTypeEnum) {
            this.comparingTypeEnum = comparingTypeEnum;
            return this;
        }

//        public NumberNotification build() {
//            return new NumberNotification(id, type, userId, sensorId, value, comparingTypeEnum);
//        }
    }

}
