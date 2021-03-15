package com.hotel.factory.configurator;

import com.hotel.factory.ApplicationContext;

public interface ObjectConfigurator  {
    void configure(Object t, ApplicationContext context);
}
