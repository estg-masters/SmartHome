package com.estg.masters.pedwm.smarthome.model;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonModel {
    JSONObject toJsonObject() throws JSONException;

    String getKey();

}
