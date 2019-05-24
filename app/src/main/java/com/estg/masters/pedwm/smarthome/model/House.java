package com.estg.masters.pedwm.smarthome.model;

import org.json.JSONException;
import org.json.JSONObject;

public class House implements JsonModel{
    private String key;
    private String name;
    private String adminId;

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
                .put("adminId", adminId);
    }

    public static House fromJson(JSONObject jsonObject) throws JSONException {
        return Builder.aHouse()
                .withId(jsonObject.getString("key"))
                .withName(jsonObject.getString("name"))
                .withAdminId(jsonObject.getString("adminId"))
                .build();
    }

    static class Builder {
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

        public Builder withName(String name) {
            this.house.setKey(name);
            return this;
        }

        public Builder withAdminId(String adminId) {
            this.house.setKey(adminId);
            return this;
        }

        public House build() {
            return this.house;
        }
    }
}
