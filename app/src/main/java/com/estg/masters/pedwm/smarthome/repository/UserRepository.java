package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository extends AbstractRepository<User> {

    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReference("user");
    private static UserRepository INSTANCE;

    private UserRepository() {
        super(databaseReference);
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }
}
