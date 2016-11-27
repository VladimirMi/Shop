package ru.mikhalev.vladimir.mvpauth.core;


import android.app.Application;

import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.AppComponent;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.AppModule;

public class App extends Application {
    private static AppComponent sAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerService.getComponent(AppComponent.class,
                new AppModule(getApplicationContext()));
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
