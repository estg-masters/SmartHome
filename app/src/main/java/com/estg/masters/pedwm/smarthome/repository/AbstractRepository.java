package com.estg.masters.pedwm.smarthome.repository;

import java.util.HashMap;
import java.util.Map;

public class AbstractRepository<T> {
    private Map<String, T> repository;

    public AbstractRepository() {
        this.repository = new HashMap<>();
    }

    public T add(String key, T object) {
        return this.repository.put(key, object);
    }

    public T remove(String key) {
        return this.repository.remove(key);
    }

    public T get(String key) {
        return this.repository.get(key);
    }
}
