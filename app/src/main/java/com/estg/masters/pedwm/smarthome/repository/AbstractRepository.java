package com.estg.masters.pedwm.smarthome.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public abstract class AbstractRepository<T> {

    private final DatabaseReference databaseReference;

    public AbstractRepository(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void save(String key, T object) {
        this.databaseReference.child(key).setValue(object);
    }

    public Task<Void> remove(String key) {
        return this.databaseReference.child(key).removeValue();
    }

    public DatabaseReference getByField(String field, String value) {
        return this.databaseReference.orderByChild(field).equalTo(value).getRef();
    }

    public DatabaseReference getReference() {
        return databaseReference;
    }
}
