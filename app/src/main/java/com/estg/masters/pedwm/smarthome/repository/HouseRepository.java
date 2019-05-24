package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.House;

public class HouseRepository extends AbstractRepository<House> {

    private static HouseRepository INSTANCE;

    private HouseRepository() {
        super();
    }

    public HouseRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HouseRepository();
        }
        return INSTANCE;
    }
}
