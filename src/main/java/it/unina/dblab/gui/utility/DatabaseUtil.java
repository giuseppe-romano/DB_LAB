package it.unina.dblab.gui.utility;

import it.unina.dblab.models.JpaEntity;
import it.unina.dblab.models.SearchResult;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
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

    public static List<List<SearchResult>> searchBooking(Integer departureStationId, Integer arrivalStationId, Date startDate, Date endDate) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        List<SearchResult> entities = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            Query query = manager.createNativeQuery("SELECT a.*, LEVEL " +
                                                    "FROM BOOKING_VIEW a " +
                                                        "START WITH a.DEPARTURE_STATION_ID = :departureStationId " +
                                                        "CONNECT BY NOCYCLE PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID " +
                                                        "AND PRIOR a.ARRIVAL_STATION_ID != :arrivalStationId AND (LEVEL > 1 OR (a.DEPARTURE_DATE BETWEEN :startDate AND :endDate)) " +
                                                    "ORDER SIBLINGS BY ROUTE_ID, SEQUENCE_NUMBER, DEPARTURE_DATE", "SearchResult");
            query.setParameter("departureStationId", departureStationId);
            query.setParameter("arrivalStationId", arrivalStationId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            entities = query.getResultList();

            List<List<SearchResult>> result = normalize(entities);
            // Commit the transaction
            transaction.commit();

            return result;

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

    private static List<List<SearchResult>> normalize(List<SearchResult> entities) {
        List<List<SearchResult>> result = new ArrayList<>();
        List<SearchResult> list = null;

        for (SearchResult entity : entities) {
            if(entity.getLevel().equals(1)) {
                if(list != null) {
                    result.add(list);
                }
                list = new ArrayList<>();
            }
            list.add(entity);
        }

        if(list != null) {
            result.add(list);
        }

        List<List<SearchResult>> flattenResult = new ArrayList<>();
        for (List<SearchResult> resultList : result) {
            Collections.reverse(resultList);

            int pathLength = resultList.get(0).getLevel();

            while (!resultList.isEmpty()) {
                SearchResult tail = resultList.remove(0);
                int level = tail.getLevel();

                if(level == pathLength) {

                    List<SearchResult> flatList = new ArrayList<>();
                    flatList.add(tail);

                    level--;
                    for (SearchResult entity : resultList) {

                        if (level == entity.getLevel()) {
                            flatList.add(entity);
                            level--;
                        }
                    }
                    if (flatList.size() == pathLength) {
                        Collections.reverse(flatList);
                        flattenResult.add(flatList);
                    }
                }
            }
        }
        return flattenResult;
    }

    public static void main(String [] args) {
        SearchResult entity1 = new SearchResult(1, 1, 7, 1, 1, 200, new Date(0), new Date(), 1, 2, 1, true, 1);
        SearchResult entity2 = new SearchResult(1, 1, 7, 1, 2, 200, new Date(0), new Date(), 1, 2, 1, true, 2);
        SearchResult entity3 = new SearchResult(1, 7, 10, 1, 2, 200, new Date(0), new Date(), 1, 2, 1, true, 2);
        SearchResult entity4 = new SearchResult(1, 1, 10, 1, 3, 200, new Date(0), new Date(), 1, 2, 1, true, 1);
        SearchResult entity5 = new SearchResult(1, 1, 7, 1, 4, 200, new Date(0), new Date(), 1, 2, 1, true, 2);
        SearchResult entity6 = new SearchResult(1, 7, 10, 1, 4, 200, new Date(0), new Date(), 1, 2, 1, true, 2);

        List<SearchResult> entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
        entities.add(entity4);
        entities.add(entity5);
        entities.add(entity6);

        normalize(entities);
    }
}
