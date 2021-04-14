package com.kurtsevich.hotel.start;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.kurtsevich.hotel")
@PropertySource("classpath:application.properties")
public class ContextConfig {
}
