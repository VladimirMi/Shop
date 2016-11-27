package ru.mikhalev.vladimir.mvpauth.flow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Screen {
    int value();
}
