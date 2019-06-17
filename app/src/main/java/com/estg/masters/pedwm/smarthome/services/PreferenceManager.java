package com.estg.masters.pedwm.smarthome.services;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREFERENCE_NAME = "comestgmasterspedwmsmarthome";

    private static Context context;
    private static PreferenceManager instance;

    private PreferenceManager(Context ctx){
        context = ctx;
    }

    public static PreferenceManager getInstance(Context ctx) {
        if(instance == null) {
            instance = new PreferenceManager(ctx);
        }

        return instance;
    }

    public void storeValue(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
}
