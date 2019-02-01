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
        List<SearchResult> entities;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            Query query = manager.createNativeQuery("SELECT distinct a.*, :departureStationId || SYS_CONNECT_BY_PATH(a.ARRIVAL_STATION_ID, '-') AS PATHS, LEVEL" +
                                                    "   FROM BOOKING_VIEW a" +
                                                    "   WHERE EXISTS (SELECT 1 FROM ROUTES_2_SEGMENTS rs, " +
                                                                                    "SEGMENTS sg " +
                                                                                "WHERE a.ROUTE_ID = rs.ROUTE_ID " +
                                                                                    "AND sg.ID = rs.SEGMENT_ID " +
                                                                                    "AND (sg.DEPARTURE_STATION_ID = :departureStationId OR sg.ARRIVAL_STATION_ID = :arrivalStationId)) " +
                                                    "   AND a.DEPARTURE_STATION_ID != :arrivalStationId " +
                                                    "   START WITH a.DEPARTURE_STATION_ID = :departureStationId AND (a.DEPARTURE_DATE BETWEEN :startDate AND :endDate) " +
                                                    "   CONNECT BY PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID " +
                                                    "   ORDER SIBLINGS BY a.ROUTE_ID, a.SEQUENCE_NUMBER, a.DEPARTURE_DATE DESC", "SearchResult");

            query.setParameter("departureStationId", departureStationId);
            query.setParameter("arrivalStationId", arrivalStationId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            entities = query.getResultList();

            List<List<SearchResult>> result = new ArrayList<>();

            int factorial = fattorial(entities.size());
            for (int i = 0; i < factorial; i++) {
                result.add(new ArrayList<>());
            }


            int maxLevel = entities.stream()
                    .max(Comparator.comparing(SearchResult::getLevel))
                    .map(e -> e.getLevel())
                    .get();

            for (int level = 1; level <= maxLevel; level++) {
                for (SearchResult entity : entities) {
                    if (entity.getLevel() == level) {
                        for (List<SearchResult> list : result) {
                            list.add(entity);
                        }
                    }
                }
            }

            if(!entities.isEmpty()) {
                Collections.sort(entities, Comparator.comparing((SearchResult e) -> e.getPaths()).reversed());

                for (SearchResult entity : entities) {
                    if(isDirect(entity, departureStationId, arrivalStationId)) {
                        List<SearchResult> list = new ArrayList<>();
                        list.add(entity);
                        result.add(list);

                        entities.remove(entity);
                    }
                }


                for (int level = 1; level <= maxLevel; level++) {
                    List<SearchResult> specificPaths = retrievePaths(entities, level, departureStationId, arrivalStationId);

                    if (!specificPaths.isEmpty()) {
                        List<List<SearchResult>> composePaths = normalize(specificPaths);
                        for (List<SearchResult> paths : composePaths) {
                            if(isValidPath(paths)) {
                                result.add(paths);
                            }
                        }
                    }
                }
            }
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

        int[] duplications = calculateDuplications(entities);
        boolean[] evens = new boolean[duplications.length];

        int res = 0;
        for (int counter : duplications) {
            if(counter > 1) {
                res++;
            }
        }

        int possiblePaths = (int)Math.pow(2, res);
        for (int i = 0; i < possiblePaths; i++) {
            result.add(new ArrayList<>());
        }

        int alternationStep = -1;
        for (SearchResult entity : entities) {
            if(duplications[entity.getLevel() - 1] > 1) {

                boolean go = false;

                boolean even = evens[entity.getLevel() - 1];
                if(even) {
                    go = true;
                }
                else {
                    alternationStep++;
                }
                int slidingWindow = (int)Math.pow(2, alternationStep);

                int counter = 0;
                for (int i = 0; i < result.size(); i++) {
                    if(go) {
                        List<SearchResult> list = result.get(i);
                        list.add(entity);
                    }
                    counter++;

                    if(counter == slidingWindow) {
                        counter = 0;
                        go = !go;
                    }
                }
                evens[entity.getLevel() - 1] = !even;
            }
            else {
                for (List<SearchResult> list : result) {
                    list.add(entity);
                }
            }
        }

        for (List<SearchResult> list : result) {
            Collections.reverse(list);
        }
        return result;
    }

    private static int[] calculateDuplications(List<SearchResult> entities) {
        int []counters = new int[entities.size()];
        for (SearchResult entity : entities) {
            counters[entity.getLevel() - 1] += 1;
        }

        return counters;
    }

    private static List<SearchResult> retrievePaths(List<SearchResult> entities, int level, int departureStationId, int arrivalStationId) {
        List<SearchResult> result = new ArrayList<>();

        String regex = "";
        for (int i = 1; i < level; i++) {
            regex += "(\\d+)-";
        }

        String fullPath = "";
        String matching = departureStationId + "-" + regex + arrivalStationId;
        for (SearchResult entity : entities) {
            if(entity.getLevel() <= level) {
                boolean matches = entity.getPaths().matches(matching);
                if(matches) {
                    fullPath = entity.getPaths();
                    result.add(entity);
                }
                else if(fullPath.contains(entity.getPaths())) {
                    result.add(entity);
                }
            }
        }

        return result;
    }

    private static boolean canBeConsecutives(SearchResult first, SearchResult last) {
        boolean result = true;

        //Dates not consecutives
        if(first.getArrivalDate().getTime() > last.getDepartureDate().getTime()) {
            result = false;
        }
        else if(first.getArrivalStationId() != last.getDepartureStationId()) {
            result = false;
        }
        return result;
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

    private static int fattorial(int n) {
        int f = 1;

        for(int i = 1; i <= n; i++) {
            f=f*i;
        }

        return f;
    }
}
