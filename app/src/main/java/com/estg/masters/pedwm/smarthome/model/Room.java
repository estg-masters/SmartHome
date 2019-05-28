package com.estg.masters.pedwm.smarthome.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Room implements JsonModel {
    private String id;
    private String name;
    private String houseId;


    @Override
    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("houseId", houseId);
    }

    @Override
    public String getKey() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public static class Builder {
        private Room room;

        private Builder() {
            room = new Room();
        }

        public Builder aRoom() {
            return new Builder();
        }

        public Builder withId(String id) {
            room.setId(id);
            return this;
        }

        public Builder withNewId() {
            room.setId(UUID.randomUUID().toString());
            return this;
        }

        public Builder withName(String name) {
            room.setName(name);
            return this;
        }

        public Builder withHouseId(String houseId) {
            room.setHouseId(houseId);
            return this;
        }

        public Room build() {
            return room;
        }
    }
}
