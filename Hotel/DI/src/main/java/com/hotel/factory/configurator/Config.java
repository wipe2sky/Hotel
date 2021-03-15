package com.hotel.factory.configurator;

import org.reflections.Reflections;

import java.util.Scanner;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> ifc);
    Reflections getScanner();
}
