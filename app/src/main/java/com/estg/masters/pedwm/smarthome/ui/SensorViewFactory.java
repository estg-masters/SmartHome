package com.estg.masters.pedwm.smarthome.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.model.sensor.AbstractSensor;
import com.estg.masters.pedwm.smarthome.model.sensor.BooleanSensor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SensorViewFactory {

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View makeView(AbstractSensor sensor, Context context,
                                Consumer<Integer> onClickListener) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(context);
        textView.setText(sensor.getName());
        textView.setTextSize(50);
        textView.setLayoutParams(params);
        textView.setId(View.generateViewId());

        TextView statusInfo = new TextView(context);
        statusInfo.setId(View.generateViewId());
        if (sensor instanceof BooleanSensor) { // todo: add to number sensor too
            BooleanSensor booleanSensor = (BooleanSensor) sensor;
            statusInfo.setText(booleanSensor.isOn() ? "On" : "Off");
        }

        statusInfo.setOnClickListener(v -> onClickListener.accept(v.getId()));

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setLayoutParams(params);
        view.setId(View.generateViewId());
        view.addView(textView);
        view.addView(statusInfo);

        return view;
    }
}