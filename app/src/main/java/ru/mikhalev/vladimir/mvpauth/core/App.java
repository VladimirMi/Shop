package ru.mikhalev.vladimir.mvpauth.core;


import android.app.Application;

import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.AppComponent;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.AppModule;
import ru.mikhalev.vladimir.mvpauth.mortar.ScreenScoper;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;

public class App extends Application {
    private static AppComponent sAppComponent;
    private MortarScope mRootScope;
    private MortarScope mRootActivityScope;
    private RootActivity.Component mRootActivityComponent;

    @Override
    public Object getSystemService(String name) {
        return mRootScope.hasService(name)
                ? mRootScope.getService(name)
                : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerService.getComponent(AppComponent.class,
                new AppModule(getApplicationContext()));

        mRootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, sAppComponent)
                .build("Root");

        createRootActivityComponent();
        mRootActivityScope = mRootScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, mRootActivityComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(RootActivity.class.getName());

        ScreenScoper.registerScope(mRootScope);
        ScreenScoper.registerScope(mRootActivityScope);
    }

    private void createRootActivityComponent() {
        mRootActivityComponent = DaggerService.getComponent(RootActivity.Component.class,
                new RootActivity.Module(), getAppComponent());
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
