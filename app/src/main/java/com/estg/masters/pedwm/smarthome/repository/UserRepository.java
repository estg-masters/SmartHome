package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.User;

public class UserRepository extends AbstractRepository<User> {

    private static UserRepository INSTANCE;

    private UserRepository() {
        super();
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }
}
