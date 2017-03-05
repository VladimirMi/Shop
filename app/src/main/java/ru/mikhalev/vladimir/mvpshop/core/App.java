package ru.mikhalev.vladimir.mvpshop.core;


import android.app.Application;

import io.realm.Realm;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import ru.mikhalev.vladimir.mvpshop.BuildConfig;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.components.AppComponent;
import ru.mikhalev.vladimir.mvpshop.di.modules.AppModule;
import ru.mikhalev.vladimir.mvpshop.features.root.RootActivity;
import ru.mikhalev.vladimir.mvpshop.mortar.ScreenScoper;
import timber.log.Timber;

public class App extends Application {
    private static AppComponent sAppComponent;
    private MortarScope mRootScope;
    private MortarScope mRootActivityScope;
    private static RootActivity.Component sRootActivityComponent;

    @Override
    public Object getSystemService(String name) {
        if (mRootScope != null && mRootScope.hasService(name)) {
            return mRootScope.getService(name);
        } else {
            return super.getSystemService(name);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        sAppComponent = DaggerService.createDaggerComponent(AppComponent.class,
                new AppModule(getApplicationContext()));


        mRootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, sAppComponent)
                .build("Root");

        createRootActivityComponent();
        mRootActivityScope = mRootScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, sRootActivityComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(RootActivity.class.getName());

        ScreenScoper.registerScope(mRootScope);
        ScreenScoper.registerScope(mRootActivityScope);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void createRootActivityComponent() {
        sRootActivityComponent = DaggerService.createDaggerComponent(RootActivity.Component.class,
                new RootActivity.Module(), getAppComponent());
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static RootActivity.Component getRootActivityComponent() {
        return sRootActivityComponent;
    }
}
