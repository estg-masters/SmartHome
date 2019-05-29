package com.estg.masters.pedwm.smarthome.model;

import com.google.firebase.database.DataSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class House implements JsonModel, Serializable {
    private String key;
    private String name;
    private String adminId;
    private List<Room> rooms;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("key", key)
                .put("name", name)
                .put("adminId", adminId)
                .put("rooms", rooms);
    }

    public static House fromSnapshot(DataSnapshot dataSnapshot) {
        return Builder.aHouse()
                .withId(dataSnapshot.getKey())
                .withName(String.valueOf(dataSnapshot.child("name").getValue()))
                .withAdminId(String.valueOf(dataSnapshot.child("admin_id").getValue()))
                .build();
    }

    public static class Builder {
        private House house;

        public static Builder aHouse() {
            return new Builder();
        }

        public Builder() {
            this.house = new House();
        }

        public Builder withId(String id) {
            this.house.setKey(id);
            return this;
        }

        public Builder withNewId() {
            this.house.setKey(UUID.randomUUID().toString());
            return this;
        }

        public Builder withName(String name) {
            this.house.setName(name);
            return this;
        }

        public Builder withAdminId(String adminId) {
            this.house.setAdminId(adminId);
            return this;
        }

        public Builder withRooms(List<Room> rooms) {
            this.house.setRooms(rooms);
            return this;
        }

        public House build() {
            return this.house;
        }
    }
}
