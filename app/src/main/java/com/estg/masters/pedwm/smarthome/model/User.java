package com.estg.masters.pedwm.smarthome.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User implements JsonModel{
    private String key;
    private String name;
    private List<String> tokens;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public static User fromJson(JSONObject userJson) throws JSONException {
        return Builder
                .aUser()
                .withId(userJson.getString("key"))
                .withName(userJson.getString("name"))
                .withTokens(fromJsonArray(userJson.getJSONArray("tokens")))
                .build();
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("key", key)
                .put("name", name)
                .put("tokens", tokens);
    }

    private static List<String> fromJsonArray(JSONArray jsonTokens) throws JSONException {
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < jsonTokens.length(); i++) {
            tokens.add(jsonTokens.getString(i));
        }
        return tokens;
    }

    public static class Builder {
        private User user;

        public static Builder aUser() {
            return new Builder();
        }

        Builder() {
            this.user = new User();
        }

        public Builder withId(String id) {
            this.user.setKey(id);
            return this;
        }

        public Builder withName(String name) {
            this.user.setName(name);
            return this;
        }

        public Builder withTokens(List<String> tokens) {
            this.user.setTokens(tokens);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
