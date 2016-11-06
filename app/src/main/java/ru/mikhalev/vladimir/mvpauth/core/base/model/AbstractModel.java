package ru.mikhalev.vladimir.mvpauth.core.base.model;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.core.managers.DataManager;
import ru.mikhalev.vladimir.mvpauth.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.di.components.ModelComponent;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

public abstract class AbstractModel {
    @Inject
    protected DataManager mDataManager;

    public AbstractModel() {
        DaggerService.getComponent(ModelComponent.class).inject(this );
    }
}
