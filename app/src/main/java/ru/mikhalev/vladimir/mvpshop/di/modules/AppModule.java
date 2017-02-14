package ru.mikhalev.vladimir.mvpshop.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.mikhalev.vladimir.mvpshop.core.App;

/**
 * Developer Vladimir Mikhalev 05.11.2016
 */

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }


    @Provides
    Context provideContext() {
        return mContext;
    }
}
