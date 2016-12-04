package ru.mikhalev.vladimir.mvpauth.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ru.mikhalev.vladimir.mvpauth.core.layers.view.BaseActivity;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, RootActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}