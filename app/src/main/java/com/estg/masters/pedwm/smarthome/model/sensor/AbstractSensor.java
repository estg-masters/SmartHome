package com.estg.masters.pedwm.smarthome.model.sensor;

import com.estg.masters.pedwm.smarthome.model.JsonModel;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractSensor implements JsonModel {
    private String key;
    private String name;
    private String houseId;

    public AbstractSensor(String key, String name, String houseId) {
        this.key = key;
        this.name = name;
        this.houseId = houseId;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getHouseId() {
        return houseId;
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("key", key)
                .put("name", name)
                .put("houseId", houseId);
    }

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
