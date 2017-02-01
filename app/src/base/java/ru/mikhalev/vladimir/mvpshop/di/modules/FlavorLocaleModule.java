package ru.mikhalev.vladimir.mvpshop.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.mikhalev.vladimir.mvpshop.data.managers.RealmManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Module
public class FlavorLocaleModule {

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                        .build());
        return new RealmManager();
    }
}

