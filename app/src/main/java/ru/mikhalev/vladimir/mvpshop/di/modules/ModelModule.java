package ru.mikhalev.vladimir.mvpshop.di.modules;

import android.content.Context;

import com.birbit.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.mikhalev.vladimir.mvpshop.data.AsyncTransformer;
import ru.mikhalev.vladimir.mvpshop.data.managers.DataManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Module
public class ModelModule extends FlavorModelModule{
    public ModelModule(Context context) {
        super(context);
    }

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return DataManager.getInstance();
    }

    @Provides
    @Singleton
    AsyncTransformer provideAsyncTransformer() {
        return AsyncTransformer.DEFAULT;
    }
}
