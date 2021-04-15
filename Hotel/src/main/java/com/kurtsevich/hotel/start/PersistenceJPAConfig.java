package com.kurtsevich.hotel.start;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {
    @Bean
    public LocalEntityManagerFactoryBean emf() {
        LocalEntityManagerFactoryBean result = new LocalEntityManagerFactoryBean();
        result.setPersistenceUnitName("persistence");
        return result;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager result = new JpaTransactionManager();
        result.setEntityManagerFactory(emf().getObject());
        return result;
    }
}
