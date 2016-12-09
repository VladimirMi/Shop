package ru.mikhalev.vladimir.mvpauth.core.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.ModelModule;
import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(BaseModel model);
}
