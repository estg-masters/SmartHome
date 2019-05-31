package com.estg.masters.pedwm.smarthome.activity;

import com.estg.masters.pedwm.smarthome.model.Room;
import com.google.firebase.database.DataSnapshot;

class RoomConverter {
    public static Room fromSnapshot(DataSnapshot dataSnapshot) {
        return Room.Builder
                .aRoom()
                .withHouseId(dataSnapshot.child("houseId").getValue().toString())
                .withId(dataSnapshot.child("key").getValue().toString())
                .withName(dataSnapshot.child("name").getValue().toString())
                .build();
    }
}
