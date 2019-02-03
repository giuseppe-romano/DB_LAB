package it.unina.dblab.gui.utility;

import it.unina.dblab.models.JpaEntity;
import it.unina.dblab.models.SearchResult;

import javax.persistence.*;
import java.util.*;

public abstract class DatabaseUtil {

    private static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("DB_LAB");

    private static EntityManager manager;

    public static void setConnectionSettings(Map<String, Object> properties) {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("DB_LAB", properties);

        manager = entityManagerFactory.createEntityManager();
    }

    public static <T> List<T> listEntities(Class<T> clazz) {
        return listEntities(clazz, null);
    }

    public static <T> List<T> listEntities(Class<T> clazz, String whereClause) {
        List<T> entities = new ArrayList<>();
        if(manager == null) {
            return entities;
        }
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

    public static void invokeStoredProcedureCHECK_ROUTE_LINKING(Integer routeId) {
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            manager.
                    createNamedStoredProcedureQuery("CHECK_ROUTE_LINKING")
                    .setParameter("routeId", routeId)
                    .execute();
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
        EntityTransaction transaction = null;
        List<SearchResult> entities;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            Query query = manager.createNativeQuery("SELECT distinct a.*, LEVEL" +
                    "   FROM BOOKING_VIEW a" +
                    "   WHERE a.DEPARTURE_STATION_ID != :arrivalStationId AND a.ARRIVAL_STATION_ID != :departureStationId " +
                    "   START WITH a.DEPARTURE_STATION_ID = :departureStationId AND (a.DEPARTURE_DATE BETWEEN :startDate AND :endDate) " +
                    "   CONNECT BY NOCYCLE PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID " +
                    "   AND PRIOR a.ARRIVAL_DATE BETWEEN a.DEPARTURE_DATE - 3/24 AND a.DEPARTURE_DATE " +
                    "   ORDER SIBLINGS BY a.ROUTE_ID, a.SEQUENCE_NUMBER, a.DEPARTURE_DATE DESC", "SearchResult");

            query.setParameter("departureStationId", departureStationId);
            query.setParameter("arrivalStationId", arrivalStationId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            entities = query.getResultList();

            List<List<SearchResult>> result = new ArrayList<>();

            List<List<SearchResult>> combinations = new ArrayList<>();
            extractCombinations(entities, departureStationId, arrivalStationId, combinations);
            for (List<SearchResult> paths : combinations) {
                if(isValidPath(paths)) {
                    result.add(paths);
                }
            }

            Collections.sort(result, Comparator.comparing((List<SearchResult> e) -> e.get(e.size() - 1).getArrivalDate().getTime() - e.get(0).getDepartureDate().getTime()));

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

    private static boolean isDirect(SearchResult searchResult, int departureStationId, int arrivalStationId) {
        return searchResult != null && searchResult.getDepartureStationId() == departureStationId && searchResult.getArrivalStationId() == arrivalStationId;
    }

    private static boolean isValidPath(List<SearchResult> paths) {
        if(paths.isEmpty()) {
            return false;
        }

        long arrivalTime = -1;
        for (SearchResult path : paths) {
            if(arrivalTime > path.getArrivalDate().getTime()) {
                return false;
            }
            arrivalTime = path.getArrivalDate().getTime();
        }

        return true;
    }

    private static void extractCombinations(List<SearchResult> entities, int departureStationId, int arrivalStationId, final List<List<SearchResult>> result) {
        entities.stream().filter(r -> r.getDepartureStationId().equals(departureStationId))
                .forEach(r -> {
                    if(isDirect(r, departureStationId, arrivalStationId)) {
                        result.add(Arrays.asList(r));
                    }
                    else {
                        extractCombinations(entities, r, arrivalStationId, new ArrayList<>(), result);
                    }
                });
    }

    private static void extractCombinations(List<SearchResult> entities, SearchResult startStation, int arrivalStationId, List<SearchResult> currentList, List<List<SearchResult>> result) {
        currentList.add(startStation);
        entities.stream()
                .filter(adj -> adj.getDepartureStationId().equals(startStation.getArrivalStationId()))
                .forEach(adj -> {
                    extractCombinations(entities, adj, arrivalStationId, new ArrayList<>(currentList), result);

                    if(adj.getArrivalStationId() == arrivalStationId) {
                        List<SearchResult> list = new ArrayList<>();
                        list.addAll(currentList);
                        list.add(adj);
                        result.add(list);
                    }
                });

    }
}
