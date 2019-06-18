package com.estg.masters.pedwm.smarthome.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User{
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

        public Builder withNewId() {
            this.user.setKey(UUID.randomUUID().toString());
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
