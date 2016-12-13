package ru.mikhalev.vladimir.mvpauth.core.layers.model;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.core.base.AsyncTransformer;
import ru.mikhalev.vladimir.mvpauth.core.di.components.DaggerModelComponent;
import ru.mikhalev.vladimir.mvpauth.data.managers.DataManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

public abstract class BaseModel {
    @Inject
    protected DataManager mDataManager;

    @Inject
    protected AsyncTransformer mAsyncTransformer;


    public BaseModel() {
        DaggerModelComponent.create().inject(this);
    }
}
