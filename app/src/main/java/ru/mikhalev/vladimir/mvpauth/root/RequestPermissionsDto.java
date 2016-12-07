package ru.mikhalev.vladimir.mvpauth.root;

import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Developer Vladimir Mikhalev 06.12.2016
 */

public class RequestPermissionsDto {
    private int requestCode;
    private boolean granted;
    private int resultCode;
    @Nullable
    private Intent intent;

    public RequestPermissionsDto(int requestCode, boolean granted) {
        this.requestCode = requestCode;
        this.granted = granted;
    }

    public RequestPermissionsDto(int requestCode, int resultCode, @Nullable Intent intent) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.intent = intent;
    }


    public boolean isGranted() {
        return granted;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Nullable
    public Intent getIntent() {
        return intent;
    }
}
