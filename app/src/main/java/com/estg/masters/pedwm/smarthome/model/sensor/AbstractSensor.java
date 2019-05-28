package com.estg.masters.pedwm.smarthome.model.sensor;

import com.estg.masters.pedwm.smarthome.model.JsonModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public abstract class AbstractSensor implements JsonModel {
    private String key;
    private String name;
    private String houseId;
    private String sourceId;

    public AbstractSensor(String key, String name, String houseId, String sourceId) {
        this.key = key;
        this.name = name;
        this.houseId = houseId;
        this.sourceId = sourceId;
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

    public String getSourceId() {
        return sourceId;
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("key", key)
                .put("name", name)
                .put("houseId", houseId)
                .put("sourceId", sourceId);
    }

    static class Builder {
        String id;
        String name;
        String houseId;
        String sourceId;

        public static Builder aSensor() {
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

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withHouseId(String houseId) {
            this.houseId = houseId;
            return this;
        }

        public Builder withSourceId(String sourceId) {
            this.sourceId = sourceId;
            return this;
        }
    }
}
