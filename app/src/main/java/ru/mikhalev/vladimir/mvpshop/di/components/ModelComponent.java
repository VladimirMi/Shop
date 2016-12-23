package ru.mikhalev.vladimir.mvpshop.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.mikhalev.vladimir.mvpshop.core.BaseModel;
import ru.mikhalev.vladimir.mvpshop.di.modules.ModelModule;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(BaseModel model);
}
