package ru.mikhalev.vladimir.mvpauth.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import ru.mikhalev.vladimir.mvpauth.root.RootActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, RootActivity.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}