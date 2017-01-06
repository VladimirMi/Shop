package ru.mikhalev.vladimir.mvpshop.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Developer Vladimir Mikhalev 04.01.2017
 */

@Scope
@Retention(RetentionPolicy.SOURCE)
public @interface DaggerScope {
    Class<?> value();
}
