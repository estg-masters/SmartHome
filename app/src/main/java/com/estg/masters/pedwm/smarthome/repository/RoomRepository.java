package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.Room;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomRepository extends AbstractRepository<Room> {

    private static RoomRepository instance;


    private static final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("houses");

    public RoomRepository() {
        super(reference);
    }

    public static RoomRepository getInstance() {
        if(instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    public static DatabaseReference getReference(String houseKey) {
        return reference.child(houseKey).child("rooms");
    }

    @Override
    public void save(String key, Room object) {
        reference
                .child(object.getHouseId())
                .child("rooms")
                .child(key)
                .setValue(object);
    }

    @Override
    public Task<Void> remove(String key) {
        return null;
    }

    public Task<Void> removeRoom(String roomKey, String houseKey) {
        return reference
                .child(houseKey)
                .child("rooms")
                .child(roomKey)
                .removeValue();
    }

    @Override
    public DatabaseReference getByField(String field, String value) {
        return super.getByField(field, value); // todo
    }
}
