package it.unina.dblab.gui.utility;

import it.unina.dblab.models.JpaEntity;
import it.unina.dblab.models.SearchResult;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public abstract class DatabaseUtil {

    private static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("DB_LAB");

    private static EntityManager manager = entityManagerFactory.createEntityManager();

    public static <T> List<T> listEntities(Class<T> clazz) {
        return listEntities(clazz, null);
    }

    public static <T> List<T> listEntities(Class<T> clazz, String whereClause) {
        List<T> entities = null;
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Trains
            entities = manager.createQuery("SELECT s FROM " + clazz.getName() + " s " + (whereClause != null ? whereClause : ""),
                    clazz).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        }

        return entities;
    }

    public static <T> T loadEntity(Class<T> clazz, Object id) {
        EntityTransaction transaction = null;
        T entity = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a specific entity
            entity = manager.find(clazz, id);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        }
        return entity;
    }

    public static <T> T mergeEntity(T theEntity) {
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            theEntity = manager.merge(theEntity);

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
        return theEntity;
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

    public static List<SearchResult> searchBooking(Integer departureStationId, Integer arrivalStationId, Date startDate, Date endDate) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        List<SearchResult> entities = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            Query query = manager.createNativeQuery("SELECT * FROM BOOKING_VIEW a " +
                    "WHERE " +
                                    "a.SEQUENCE_NUMBER >= (SELECT MIN(b.SEQUENCE_NUMBER) FROM BOOKING_VIEW b " +
                                                            "WHERE b.ROUTE_ID = a.ROUTE_ID AND b.DEPARTURE_STATION_ID = :departureStationId) " +
                                    "AND a.SEQUENCE_NUMBER <= (SELECT MAX(c.SEQUENCE_NUMBER) FROM BOOKING_VIEW c " +
                                                            "WHERE c.ROUTE_ID = a.ROUTE_ID AND c.ARRIVAL_STATION_ID = :arrivalStationId) " +
                                    "AND a.DEPARTURE_DATE BETWEEN :startDate AND :endDate " +
                    "ORDER BY a.ROUTE_ID, a.SEQUENCE_NUMBER ASC", "SearchResult");
            query.setParameter("departureStationId", departureStationId);
            query.setParameter("arrivalStationId", arrivalStationId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            entities = query.getResultList();
            // Commit the transaction
            transaction.commit();

            return entities;

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
