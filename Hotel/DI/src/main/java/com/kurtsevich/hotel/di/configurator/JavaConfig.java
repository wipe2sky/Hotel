package com.kurtsevich.hotel.di.configurator;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    private final Logger logger = LoggerFactory.getLogger(JavaConfig.class);
    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
         scanner = new Reflections(packageToScan);
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
      return   ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if(classes.size()!=1){
                logger.error("{} has 0 or more than 1 impl, please update your config", ifc);
                throw new RuntimeException(ifc + " has 0 or more than 1 impl, please update your config");
            }
            return classes.iterator().next();
        });

    }
}
