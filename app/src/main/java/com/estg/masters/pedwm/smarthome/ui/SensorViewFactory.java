package com.estg.masters.pedwm.smarthome.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.model.notification.BooleanNotification;
import com.estg.masters.pedwm.smarthome.model.notification.ComparingTypeEnum;
import com.estg.masters.pedwm.smarthome.model.notification.NumberNotification;
import com.estg.masters.pedwm.smarthome.model.sensor.NumberSensor;
import com.estg.masters.pedwm.smarthome.model.sensor.Sensor;
import com.estg.masters.pedwm.smarthome.repository.NotificationRepository;
import com.estg.masters.pedwm.smarthome.repository.RoomRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SensorViewFactory {

    private static final String LESSER_THAN = "lesser than";
    private static final String EQUALS_TO = "equals to";
    private static final String BIGGER_THAN = "bigger than";
    private static final String IS_ON = "is on";
    private static final String IS_OFF = "is off";

    public static final int sensorValueInputType = InputType.TYPE_CLASS_NUMBER
            | InputType.TYPE_NUMBER_FLAG_SIGNED;

    public SensorViewFactory() {
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View makeView(Sensor sensor,
                                Context context,
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

        if (!sensor.getHouseId().equals(sensor.getSourceId())) {
            TextView sourceName = new TextView(context);
            sourceName.setTextSize(textSize);
            sourceName.setLayoutParams(params);
            sourceName.setId(View.generateViewId());

            RoomRepository.getInstance().getChild(sensor.getSourceId(), sensor.getHouseId(),
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            sourceName.setText(" (" +
                                    dataSnapshot
                                            .child("name")
                                            .getValue()
                                            .toString() + ")");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
            view.addView(sourceName);
        }

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

        Button addNotificationButton = new Button(context);
        addNotificationButton.setLayoutParams(params);
        addNotificationButton.setId(View.generateViewId());
        addNotificationButton.setText("Add notification");

        addNotificationButton.setOnClickListener(v -> {
            if (!(sensor instanceof NumberSensor)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add notification");

                ArrayList<String> spinnerArray = new ArrayList<>();
                spinnerArray.add("true");
                spinnerArray.add("false");

                Spinner spinner = new Spinner(context);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                spinner.setAdapter(spinnerArrayAdapter);

                builder.setView(spinner);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    BooleanNotification notification =
                            BooleanNotification.Builder.aNotification()
                                    .withNewId()
                                    .withUserId(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .withSensorId(sensor.getKey())
                                    .withValue(spinner.getSelectedItem().toString().equals("true"))
                                    .build();
                    NotificationRepository.getInstance().save(notification.getKey(), notification);
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add notifications for this sensor");
                LinearLayout linearLayout = new LinearLayout(builder.getContext());
                LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                linearLayout.setLayoutParams(linearLayoutParams);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                EditText editText = new EditText(context);
                editText.setTextSize(textSize);
                editText.setInputType(sensorValueInputType);

                editText.setLayoutParams(linearLayoutParams);

                ArrayList<String> spinnerArray = new ArrayList<>();
                spinnerArray.add(LESSER_THAN);
                spinnerArray.add(EQUALS_TO);
                spinnerArray.add(BIGGER_THAN);
                spinnerArray.add(IS_ON);
                spinnerArray.add(IS_OFF);

                Spinner spinner = new Spinner(context);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                spinner.setAdapter(spinnerArrayAdapter);

                linearLayout.addView(spinner);
                linearLayout.addView(editText);

                builder.setView(linearLayout);

                builder.setPositiveButton("OK", (dialog, which) -> {

                    if (isBooleanOption(spinner.getSelectedItem().toString())) {
                        BooleanNotification notification =
                                BooleanNotification.Builder.aNotification()
                                        .withNewId()
                                        .withUserId(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .withSensorId(sensor.getKey())
                                        .withValue(spinner.getSelectedItem().toString().equals(IS_ON))
                                        .build();
                        NotificationRepository.getInstance().save(notification.getKey(), notification);
                    } else {
                        NumberNotification notification =
                                NumberNotification.Builder.aNotification()
                                        .withNewId()
                                        .withUserId(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .withSensorId(sensor.getKey())
                                        .withValue(Integer.parseInt(editText.getText().toString()))
                                        .withComparingType(getComparingTypeOfNotification(spinner
                                                .getSelectedItem().toString()))
                                        .build();
                        NotificationRepository.getInstance().save(notification.getKey(), notification);
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            }
        });

        view.addView(addNotificationButton);

        return view;
    }

    private static ComparingTypeEnum getComparingTypeOfNotification(String comparingType) {
        switch (comparingType) {
            case LESSER_THAN:
                return ComparingTypeEnum.LESSER;
            case EQUALS_TO:
                return ComparingTypeEnum.EQUALS;
            case BIGGER_THAN:
                return ComparingTypeEnum.BIGGER;
            default:
                return null;
        }
    }

    private static boolean isBooleanOption(String comparingType) {
        return getComparingTypeOfNotification(comparingType) != null;
    }
}
