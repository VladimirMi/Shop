package ru.mikhalev.vladimir.mvpauth.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.di.modules.ModelModule;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(BaseModel model);
}
