package com.estg.masters.pedwm.smarthome.model.sensor;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractSensor {
    private String id;
    private String name;
    private String houseId;

    public AbstractSensor(String id, String name, String houseId) {
        this.id = id;
        this.name = name;
        this.houseId = houseId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHouseId() {
        return houseId;
    }

    JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("houseId", houseId);
    }

    public abstract JSONObject toJson() throws JSONException ;

    static class Builder {
        String id;
        String name;
        String houseId;

        public static Builder aSensor() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withHouseId(String houseId) {
            this.houseId = houseId;
            return this;
        }
    }
}
