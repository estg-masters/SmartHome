package com.estg.masters.pedwm.smarthome.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Map;

public class IntentNavigationUtils {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void goToActivity(Context from, Class to, Map<String, String> extras) {
        Intent intent = new Intent(from, to);
        extras.forEach(intent::putExtra);
        from.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void goToActivityAndFinish(Context from, Class to, Map<String, String> extras) {
        goToActivity(from, to, extras);
        ((Activity) from).finish();
    }


}
