package com.estg.masters.pedwm.smarthome.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.estg.masters.pedwm.smarthome.R;
import com.estg.masters.pedwm.smarthome.ui.IntentNavigationUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setContentView(R.layout.activity_main);
        initActivity();
    }

    private void initActivity() {
        initAuth();
        initActionBar();
        initGoogleSignInApi();
    }

    private void initAuth() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    private void initGoogleSignInApi() {
        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    private void initActionBar() {
        getSupportActionBar().setTitle("Smart houses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        redirectToMainActivityIfUserIsSet();
    }

    private void redirectToMainActivityIfUserIsSet() {
        if (currentUser == null) {
            goToActivityAndFinish(LoginActivity.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToHousesView(View view) {
        goToActivity(HousesActivity.class);
    }

    public void goToActivityAndFinish(Class activityToGo) {
        Intent intent = new Intent(MainActivity.this, activityToGo);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToActivity(Class activityToGo) {
        IntentNavigationUtils.goToActivity(MainActivity.this, activityToGo, Collections.emptyMap());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            logOut();
        }
        return true;
    }

    private void logOut() {
        try {
            mAuth.signOut();
            Auth.GoogleSignInApi.signOut(googleApiClient);
            Log.d("LogOut", "Logged out");
        } finally {
            goToActivityAndFinish(LoginActivity.class);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
