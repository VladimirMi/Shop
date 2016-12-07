package ru.mikhalev.vladimir.mvpauth.core.di;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Developer Vladimir Mikhalev, 03.11.2016.
 */

public class DaggerService {
    public static final String SERVICE_NAME = "DAGGER_SERVICE";

    @SuppressWarnings("unchecked")
    public static <T> T getDaggerComponent(Context context) {
        return (T) context.getSystemService(SERVICE_NAME);
    }

//    public static void unregisterScope(Class<? extends Annotation> scopeAnnotation) {
//        Iterator<Map.Entry<Class, Object>> iterator = sComponentMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Class, Object> entry = iterator.next();
//            if (entry.getKey().isAnnotationPresent(scopeAnnotation)) {
//                iterator.remove();
//            }
//        }
//    }

    public static <T> T createDaggerComponent(Class<T> componentClass, Object... dependencies) {
        String fqn = componentClass.getName();

        String packageName = componentClass.getPackage().getName();
        // Accounts for inner classes, ie MyApplication$Component
        String simpleName = fqn.substring(packageName.length() + 1);
        String generatedName = (packageName + ".Dagger" + simpleName).replace('$', '_');

        try {
            Class<?> generatedClass = Class.forName(generatedName);
            Object builder = generatedClass.getMethod("builder").invoke(null);

            for (Method method : builder.getClass().getDeclaredMethods()) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 1) {
                    Class<?> dependencyClass = params[0];
                    for (Object dependency : dependencies) {
                        if (dependencyClass.isAssignableFrom(dependency.getClass())) {
                            method.invoke(builder, dependency);
                            break;
                        }
                    }
                }
            }
            //noinspection unchecked
            return (T) builder.getClass().getMethod("build").invoke(builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
