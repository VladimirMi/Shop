package ru.mikhalev.vladimir.mvpauth.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.core.base.AsyncTransformer;
import ru.mikhalev.vladimir.mvpauth.data.managers.DataManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Module
public class ModelModule {
    @Provides
    @Singleton
    DataManager provideDataManager() {
        return DataManager.getInstance();
    }

    @Provides
    AsyncTransformer provideAsyncTransformer() {
        return AsyncTransformer.DEFAULT;
    }
}
