package ru.mikhalev.vladimir.mvpshop.features.root;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Developer Vladimir Mikhalev 06.12.2016
 */
public interface IRootPresenter {
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    void onActivityResult(int requestCode, int resultCode, Intent intent);
}
