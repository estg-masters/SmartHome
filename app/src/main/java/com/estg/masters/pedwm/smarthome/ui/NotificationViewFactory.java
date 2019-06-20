package com.estg.masters.pedwm.smarthome.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.model.notification.NotificationTypeEnum;
import com.estg.masters.pedwm.smarthome.model.notification.NumberNotification;
import com.estg.masters.pedwm.smarthome.repository.SensorRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

public class NotificationViewFactory {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View makeView(DataSnapshot dataSnapshot, String sensorName, Context context, View.OnClickListener onDeleteClickListener) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(context);
        textView.setText(buildTitle(sensorName, dataSnapshot));
        textView.setTextSize(20);
        textView.setLayoutParams(params);
        textView.setId(View.generateViewId());


        Button deleteButton = new Button(context);
        deleteButton.setText("X");
        deleteButton.setOnClickListener(onDeleteClickListener);


        view.setLayoutParams(params);
        view.setId(View.generateViewId());

        view.addView(textView);
        view.addView(deleteButton);


        return view;
    }

    private static String buildTitle(String sensorName, DataSnapshot notification){
        if(notification.child("type").getValue().equals("BOOLEAN")){
            return  sensorName + " (" + notification.child("value").getValue().toString() + ")";
        } else {
            switch (notification.child("comparingTypeEnum").getValue().toString()){
                case "BIGGER":
                    return sensorName + " (>" + notification.child("number").getValue().toString() + ")";
                case "EQUALS":
                    return sensorName + " (=" + notification.child("number").getValue().toString() + ")";
                case "LESSER":
                    return sensorName + " (<" + notification.child("number").getValue().toString() + ")";
                default:
                    return "";
            }
        }
    }


}
