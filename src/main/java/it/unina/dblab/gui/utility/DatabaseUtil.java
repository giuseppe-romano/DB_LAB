package it.unina.dblab.gui.utility;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.models.JpaEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public abstract class DatabaseUtil {

    private static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("DB_LAB");

    private static EntityManager manager = entityManagerFactory.createEntityManager();

    public static <T> List<T> listEntities(Class<T> clazz) {
        List<T> entities = null;
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Trains
            entities = manager.createQuery("SELECT s FROM " + clazz.getName() + " s",
                    clazz).getResultList();

            // Commit the transaction
            transaction.commit();
        }
        catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        }

        return entities;
    }

    public static void mergeEntity(JpaEntity theEntity) {
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            manager.merge(theEntity);

            // Commit the transaction
            transaction.commit();
        }
        catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();

            throw ex;
        }
    }

    public static void removeEntity(JpaEntity theEntity) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            theEntity = manager.find(theEntity.getClass(), theEntity.getId());
            manager.remove(theEntity);

            // Commit the transaction
            transaction.commit();

        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();

            throw ex;
        }
    }
}