package com.estg.masters.pedwm.smarthome.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estg.masters.pedwm.smarthome.model.Room;

public class RoomViewFactory {

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View makeView(Room room, Context context,
                                View.OnClickListener onDeleteClickListener) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(context);
        textView.setText(room.getName());
        textView.setTextSize(50);
        textView.setLayoutParams(params);
        textView.setId(View.generateViewId());


        Button deleteButton = new Button(context);
        deleteButton.setText("X");
        deleteButton.setOnClickListener(onDeleteClickListener);


        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setLayoutParams(params);
        view.setId(View.generateViewId());

        view.addView(textView);
        view.addView(deleteButton);

        return view;
    }
}
