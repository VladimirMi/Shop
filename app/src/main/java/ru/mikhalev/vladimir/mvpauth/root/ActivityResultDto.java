package ru.mikhalev.vladimir.mvpauth.root;

import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Developer Vladimir Mikhalev, 08.12.2016.
 */
public class ActivityResultDto {
    private int requestCode;
    private int resultCode;
    @Nullable
    private Intent intent;

    public ActivityResultDto(int requestCode, int resultCode, Intent intent) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.intent = intent;
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
