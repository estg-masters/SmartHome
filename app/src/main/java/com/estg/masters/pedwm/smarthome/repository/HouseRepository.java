package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.House;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HouseRepository extends AbstractRepository<House> {

    private static HouseRepository INSTANCE;
    private static final DatabaseReference houseRef = FirebaseDatabase.getInstance()
            .getReference("houses");

    private HouseRepository() {
        super();
    }

    public static HouseRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HouseRepository();
        }
        return INSTANCE;
    }
}
