package ru.mikhalev.vladimir.mvpauth.flow;

import android.util.Log;

import flow.ClassKey;
import ru.mikhalev.vladimir.mvpauth.mortar.ScreenScoper;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public abstract class AbsScreen<T> extends ClassKey {
    private static final String TAG = "AbsScreen";
    public String getScopeName() {
        return getClass().getName();
    }

    public abstract Object createScreenComponent(T parentComponent);

    // TODO: 27.11.2016 unregister scope
    public void unregisterScope() {
        Log.e(TAG, "unregisterScope: " + this.getScopeName());
        ScreenScoper.destroyScreenScope(this.getScopeName());
    }
}
