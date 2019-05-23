package com.estg.masters.pedwm.smarthome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.estg.masters.pedwm.smarthome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            goToActivity(LoginActivity.class);
        }
    }

    public void goToHousesView(View view) {
        goToActivity(HousesActivity.class);
    }

    public void goToActivity(Class activityToGo) {
        Intent intent = new Intent(MainActivity.this, activityToGo);
        startActivity(intent);
    }
}
