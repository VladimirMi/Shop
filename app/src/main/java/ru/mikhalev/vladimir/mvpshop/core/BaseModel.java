package ru.mikhalev.vladimir.mvpshop.core;

import com.birbit.android.jobqueue.JobManager;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpshop.data.AsyncTransformer;
import ru.mikhalev.vladimir.mvpshop.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpshop.di.components.DaggerModelComponent;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

public abstract class BaseModel {
    @Inject
    protected DataManager mDataManager;

    @Inject
    protected AsyncTransformer mAsyncTransformer;

    @Inject
    protected JobManager mJobManager;

    public BaseModel() {
        DaggerModelComponent.create().inject(this);
    }
}
