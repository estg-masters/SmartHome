package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.sensor.SensorHistorySnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryRepository extends AbstractRepository<SensorHistorySnapshot> {

    private static HistoryRepository INSTANCE;
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReference("history");

    public HistoryRepository(DatabaseReference databaseReference) {
        super(databaseReference);
    }

    private HistoryRepository() {
        super(databaseReference);
    }

    public static HistoryRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HistoryRepository();
        }
        return INSTANCE;
    }

}
