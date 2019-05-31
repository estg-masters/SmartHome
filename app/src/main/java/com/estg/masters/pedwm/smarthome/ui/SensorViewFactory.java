package com.estg.masters.pedwm.smarthome.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.model.sensor.NumberSensor;
import com.estg.masters.pedwm.smarthome.model.sensor.Sensor;

public class SensorViewFactory {

    public static final int sensorValueInputType = InputType.TYPE_CLASS_NUMBER
            | InputType.TYPE_NUMBER_FLAG_SIGNED;

    public SensorViewFactory() {
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View makeView(Sensor sensor, Context context,
                                View.OnClickListener onChangeClickListener,
                                TextView.OnEditorActionListener onNumberChangeClickListener) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(context);
        textView.setText(sensor.getName());
        int textSize = 25;
        textView.setTextSize(textSize);
        textView.setLayoutParams(params);
        textView.setId(View.generateViewId());

        Switch onSwitch = new Switch(context);
        onSwitch.setId(View.generateViewId());
        onSwitch.setChecked(sensor.isOn());
        onSwitch.setOnClickListener(onChangeClickListener);

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setLayoutParams(params);
        view.setId(View.generateViewId());

        view.addView(textView);
        view.addView(onSwitch);

        if (sensor instanceof NumberSensor) {
            NumberSensor numberSensor = (NumberSensor) sensor;

            EditText valueView = new EditText(context);
            valueView.setText(String.valueOf(numberSensor.getValue()));
            valueView.setTextSize(textSize);
            valueView.setInputType(sensorValueInputType);
            valueView.setOnEditorActionListener(onNumberChangeClickListener);
            valueView.setId(View.generateViewId());
            valueView.setLayoutParams(params);
            view.addView(valueView);
        }

        return view;
    }
}
