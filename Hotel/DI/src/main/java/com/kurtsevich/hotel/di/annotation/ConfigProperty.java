package com.kurtsevich.hotel.di.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigProperty {
    String configName() default "./HotelServer/src/main/resources/application.properties";
    String propertyName() default "";
    String type() default "";
}
