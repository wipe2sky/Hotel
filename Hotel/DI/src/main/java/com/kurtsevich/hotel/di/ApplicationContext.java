package com.kurtsevich.hotel.di;

import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.di.configurator.Config;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    @Setter
    private ObjectFactory factory;
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) {

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        if (cache.containsKey(implClass)) {
            return (T) cache.get(implClass);
        }

        T t = factory.createObject(implClass);
        if(implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(implClass, t);
        }

        return  t;
    }
}
