package com.estg.masters.pedwm.smarthome.repository;

import com.estg.masters.pedwm.smarthome.model.JsonModel;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository<T extends JsonModel> {
    private Map<String, T> repository;

    public AbstractRepository() {
        this.repository = new HashMap<>();
    }

    public T save(String key, T object) {
        return this.repository.put(key, object);
    }

    public T remove(String key) {
        return this.repository.remove(key);
    }

    public T get(String key) {
        return this.repository.get(key);
    }
}
