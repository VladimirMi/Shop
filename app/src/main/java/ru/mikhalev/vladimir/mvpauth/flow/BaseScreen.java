package ru.mikhalev.vladimir.mvpauth.flow;

import android.util.Log;

import flow.ClassKey;
import ru.mikhalev.vladimir.mvpauth.mortar.ScreenScoper;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public abstract class BaseScreen<T> extends ClassKey {
    private static final String TAG = "BaseScreen";

    public String getScopeName() {
        return getClass().getName();
    }

    public abstract Object createScreenComponent(T parentComponent);

    public void unregisterScope() {
        Log.e(TAG, "unregisterScope: " + this.getScopeName());
        ScreenScoper.destroyScreenScope(this.getScopeName());
    }
}
