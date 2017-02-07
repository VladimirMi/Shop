package ru.mikhalev.vladimir.mvpshop.di.modules;

import android.content.Context;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;

import dagger.Module;
import dagger.Provides;
import ru.mikhalev.vladimir.mvpshop.utils.AppConfig;

/**
 * Developer Vladimir Mikhalev, 07.02.2017.
 */

@Module
public class FlavorModelModule {
    Context mContext;

    public FlavorModelModule(Context context) {
        mContext = context;
    }

    @Provides
    JobManager provideJobManager() {
        Configuration configuration = new Configuration.Builder(mContext)
                .minConsumerCount(AppConfig.MIN_CONSUMER_COUNT)
                .maxConsumerCount(AppConfig.MAX_CONSUMER_COUNT)
                .loadFactor(AppConfig.LOAD_FACTOR)
                .consumerKeepAlive(AppConfig.CONSUMER_KEEP_ALIVE)
                .build();

        return new JobManager(configuration);
    }
}
