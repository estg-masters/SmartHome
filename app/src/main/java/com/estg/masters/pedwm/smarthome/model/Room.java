package com.estg.masters.pedwm.smarthome.model;

import java.io.Serializable;
import java.util.UUID;

public class Room implements Serializable {
    private String key;
    private String name;
    private String houseId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

        public static Builder aRoom() {
            return new Builder();
        }

        public Builder withId(String id) {
            room.setKey(id);
            return this;
        }

        public Builder withNewId() {
            room.setKey(UUID.randomUUID().toString());
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
