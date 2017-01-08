package ru.mikhalev.vladimir.mvpshop.core;

import android.util.Log;

import flow.ClassKey;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;
import ru.mikhalev.vladimir.mvpshop.mortar.ScreenScoper;

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

    public int getLayoutResId() {
        int layout = 0;
        Screen screen;
        screen = this.getClass().getAnnotation(Screen.class);
        if (screen == null) {
            throw new IllegalStateException("@Screen annotation is missing on screen " + getScopeName());
        } else {
            layout = screen.value();
        }
        return layout;
    }
}
