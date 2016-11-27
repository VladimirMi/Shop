package ru.mikhalev.vladimir.mvpauth.auth;

import android.support.annotation.IntDef;

/**
 * @author kenrube
 * @since 06.11.16
 */

@IntDef({
        AuthInputState.LOGIN,
        AuthInputState.IDLE})
public @interface AuthInputState {

    int LOGIN = 0;
    int IDLE = 1;
}
