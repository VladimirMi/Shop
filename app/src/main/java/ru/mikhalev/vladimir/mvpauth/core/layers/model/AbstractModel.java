package ru.mikhalev.vladimir.mvpauth.core.layers.model;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.ModelComponent;
import ru.mikhalev.vladimir.mvpauth.core.managers.DataManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

public abstract class AbstractModel {
    @Inject
    protected DataManager mDataManager;

    public AbstractModel() {
        DaggerService.createDaggerComponent(ModelComponent.class).inject(this);
    }
}
