package com.kurtsevich.hotel.di.configurator;

import com.kurtsevich.hotel.di.ApplicationContext;

public interface ObjectConfigurator  {
    void configure(Object t, ApplicationContext context);
}
