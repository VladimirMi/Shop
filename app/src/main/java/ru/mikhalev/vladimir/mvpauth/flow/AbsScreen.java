package ru.mikhalev.vladimir.mvpauth.flow;

import flow.ClassKey;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public abstract class AbsScreen<T> extends ClassKey {
    public String getScopeName() {
        return getClass().getName();
    }

    public abstract Object createScreenComponent(T parentComponent);

    // TODO: 27.11.2016 unregister scope
    public void unregisterScope() {

    }
}
