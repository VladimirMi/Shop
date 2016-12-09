package ru.mikhalev.vladimir.mvpauth.core.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.core.base.AsyncTransformer;
import ru.mikhalev.vladimir.mvpauth.core.managers.DataManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Module
public class ModelModule {
    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager();
    }

    @Provides
    AsyncTransformer provideAsyncTransformer() {
        return AsyncTransformer.DEFAULT;
    }
}
