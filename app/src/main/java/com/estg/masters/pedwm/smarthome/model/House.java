package com.estg.masters.pedwm.smarthome.model;

import org.json.JSONException;
import org.json.JSONObject;

public class House {
    private String id;
    private String name;
    private String adminId;

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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("adminId", adminId);
    }

    public static House fromJson(JSONObject jsonObject) throws JSONException {
        return Builder.aHouse()
                .withId(jsonObject.getString("id"))
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
            this.house.setId(id);
            return this;
        }

        public Builder withName(String name) {
            this.house.setId(name);
            return this;
        }

        public Builder withAdminId(String adminId) {
            this.house.setId(adminId);
            return this;
        }

        public House build() {
            return this.house;
        }
    }
}
