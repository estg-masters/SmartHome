package com.estg.masters.pedwm.smarthome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.estg.masters.pedwm.smarthome.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToHousesView(View view) {
        Intent intent = new Intent(MainActivity.this, HousesActivity.class);
        startActivity(intent);
    }
}
