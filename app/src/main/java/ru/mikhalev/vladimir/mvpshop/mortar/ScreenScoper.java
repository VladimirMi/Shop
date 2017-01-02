package ru.mikhalev.vladimir.mvpshop.mortar;

import android.support.annotation.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.flow.BaseScreen;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public class ScreenScoper {
    private static Map<String, MortarScope> sScopeMap = new HashMap<>();

    public static MortarScope getScreenScope(BaseScreen screen) {
        if (!sScopeMap.containsKey(screen.getScopeName())) {
            Timber.e("getScreenScope: create new scope");
            return createScreenScope(screen);
        } else {
            Timber.e("getScreenScope: return existed scope");
            return sScopeMap.get(screen.getScopeName());
        }
    }

    public static void registerScope(MortarScope scope) {
        sScopeMap.put(scope.getName(), scope);
    }

    private static void cleanScopeMap() {
        Iterator<Map.Entry<String, MortarScope>> iterator = sScopeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MortarScope> entry = iterator.next();
            if (entry.getValue().isDestroyed()) {
                iterator.remove();
            }
        }
    }

    public static void destroyScreenScope(String scopeName) {
        MortarScope scope = sScopeMap.get(scopeName);
        scope.destroy();
        cleanScopeMap();
    }

    @Nullable
    private static String getParentScopeName(BaseScreen screen) {
        try {
            String genericName = ((Class) ((ParameterizedType) screen.getClass().getGenericSuperclass()).
                    getActualTypeArguments()[0]).getName();
            String parentScopeName = genericName;

            if (parentScopeName.contains("$")) {
                parentScopeName = parentScopeName.substring(0, genericName.indexOf("$"));
            }

            return parentScopeName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static MortarScope createScreenScope(BaseScreen screen) {
        Timber.e("createScreenScope: with name : " + screen.getScopeName());
        String parentScopeName = getParentScopeName(screen);
        MortarScope parentScope = sScopeMap.get(parentScopeName);
        Object screenComponent = screen.createScreenComponent(parentScope.getService(DaggerService.SERVICE_NAME));
        MortarScope newScope = parentScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, screenComponent)
                .build(screen.getScopeName());
        registerScope(newScope);
        return newScope;
    }
}