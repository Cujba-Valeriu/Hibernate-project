package com.cedacri.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceManagerTestUtil {
    private static EntityManagerFactory factory;


    public static EntityManager getEntityManger() {
        if (factory == null || !factory.isOpen()) {
            factory = Persistence.createEntityManagerFactory("default-test"); // "default" / "default-test"
        }

        return factory.createEntityManager();
    }

    public static EntityManager beginTransaction() {
        EntityManager entityManager = getEntityManger();
        entityManager.getTransaction().begin();
        return entityManager;
    }

    public static void commitTransaction(EntityManager entityManager) {
        entityManager.getTransaction().commit();
    }

    public static void closeEntityManagerFactory() {
        factory.close();
    }
}
