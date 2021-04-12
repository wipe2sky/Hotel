package com.kurtsevich.hotel.server.util;


import com.kurtsevich.hotel.di.annotation.Singleton;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
public class HibernateConnector {
    private final Logger logger = LoggerFactory.getLogger(HibernateConnector.class);
    @Getter
    private EntityManager entityManager;


    public HibernateConnector() {
        init();
    }

    private void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        this.entityManager = emf.createEntityManager();
    }


    public void closeEntityManager() {
        try {
            entityManager.close();
        } catch (Throwable e) {
            logger.error("Close Session Factory created failed", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public void startTransaction() {
        entityManager.getTransaction().begin();

    }

    public void finishTransaction() {
        entityManager.getTransaction().commit();
    }

    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }
}
