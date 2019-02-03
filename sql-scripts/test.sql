-- tutte le stazioni di partenza
SELECT * FROM 
(SELECT st.*, rs.*, TO_CHAR(COMPUTE_TIME(tt.ID, sg.DEPARTURE_STATION_ID, sg.ARRIVAL_STATION_ID), 'YYYY-MM-DD HH24:MI:SS') TIME_NEEDED from 
  ROUTES r, ROUTES_2_SEGMENTS rs, SEGMENTS sg, STATIONS st, TIMETABLE tt
WHERE 
  r.ID = rs.ROUTE_ID
  AND tt.ROUTE_ID = r.id
  AND rs.SEGMENT_ID = sg.ID
  AND (
            (sg.DEPARTURE_STATION_ID = st.ID 
                AND 
             rs.SEQUENCE_NUMBER = (SELECT MIN(SEQUENCE_NUMBER) FROM ROUTES_2_SEGMENTS rs2
                                       WHERE r.ID = rs2.ROUTE_ID)) 
        OR 
            (sg.ARRIVAL_STATION_ID = st.ID AND rs.PERFORM_STOP = 1 AND rs.IS_TERMINAL = 0)
      )
  AND r.ACTIVE = 1 AND r.ID = 2
) departure_st
  

-- tutte le stazioni di arrivo
SELECT st.*, r.ID AS ROUTE_ID from 
  ROUTES r, ROUTES_2_SEGMENTS rs, SEGMENTS sg, STATIONS st
WHERE 
  r.ID = rs.ROUTE_ID
  AND rs.SEGMENT_ID = sg.ID
  AND sg.ARRIVAL_STATION_ID = st.ID AND (rs.PERFORM_STOP = 1 OR rs.IS_TERMINAL = 1)
  AND r.ACTIVE = 1
)




SELECT * FROM TIMETABLE tt






    
    SELECT * from (
    --Stazioni di inizio tratta
SELECT st.* from 
  ROUTES r, ROUTES_2_SEGMENTS rs, SEGMENTS sg, STATIONS st
WHERE 
  r.ID = rs.ROUTE_ID
  AND rs.SEGMENT_ID = sg.ID
  AND sg.DEPARTURE_STATION_ID = st.ID
  AND rs.SEQUENCE_NUMBER = (SELECT MIN(SEQUENCE_NUMBER) FROM ROUTES_2_SEGMENTS rs2
                                 WHERE r.ID = rs2.ROUTE_ID)
  AND r.ACTIVE = 1
  
  UNION
  --Stazioni intermedie 
SELECT st2.* from 
  ROUTES r2, ROUTES_2_SEGMENTS rs2, SEGMENTS sg2, STATIONS st2
WHERE 
  r2.ID = rs2.ROUTE_ID
  AND rs2.SEGMENT_ID = sg2.ID
  AND sg2.ARRIVAL_STATION_ID = st2.ID
  AND rs2.PERFORM_STOP = 1
  AND rs2.IS_TERMINAL = 0
  AND r2.ACTIVE = 1)
  
  
  
  
-------------- Arrivo
  --Stazioni intermedie 
SELECT st2.* from 
  ROUTES r2, ROUTES_2_SEGMENTS rs2, SEGMENTS sg2, STATIONS st2
WHERE 
  r2.ID = rs2.ROUTE_ID
  AND rs2.SEGMENT_ID = sg2.ID
  AND sg2.ARRIVAL_STATION_ID = st2.ID
  AND (rs2.PERFORM_STOP = 1 OR rs2.IS_TERMINAL = 1)
  AND r2.ACTIVE = 1
  
  
  
  
  
  
  
SELECT a.*, LEVEL
   FROM BOOKING_DEPARTURES a
   WHERE a.ROUTE_ID = 2
   START WITH a.DEPARTURE_STATION_ID = 1
   CONNECT BY a.DEPARTURE_STATION_ID = a.ARRIVAL_STATION_ID
   ORDER SIBLINGS BY SEQUENCE_NUMBER
  
  
  
--OLD VIEW
SELECT sg.DEPARTURE_STATION_ID, 
       sg.ARRIVAL_STATION_ID,
       sg.DISTANCE,
       COMPUTE_DEPARTURE_DATE(tt.ID, sg.DEPARTURE_STATION_ID) DEPARTURE_DATE,
       COMPUTE_ARRIVAL_DATE(tt.ID, sg.ARRIVAL_STATION_ID) ARRIVAL_DATE,
       tt.DEPARTURE_PLATFORM,
       tt.ARRIVAL_PLATFORM,
       rs.ROUTE_ID,
       rs.PERFORM_STOP,
       rs.IS_TERMINAL, 
       tt.TRAIN_ID,
       rs.SEQUENCE_NUMBER
       FROM ROUTES_2_SEGMENTS rs, SEGMENTS sg, TIMETABLE tt
            WHERE rs.SEGMENT_ID = sg.ID
                AND tt.ROUTE_ID = rs.ROUTE_ID
                AND rs.SEQUENCE_NUMBER >= (SELECT MIN(rs2.SEQUENCE_NUMBER) FROM ROUTES_2_SEGMENTS rs2, SEGMENTS sg2
                                            WHERE rs2.ROUTE_ID = rs.ROUTE_ID AND rs2.SEGMENT_ID = sg2.ID)-- AND sg2.DEPARTURE_STATION_ID = 1) 
                AND rs.SEQUENCE_NUMBER <= (SELECT MAX(rs2.SEQUENCE_NUMBER) FROM ROUTES_2_SEGMENTS rs2, SEGMENTS sg2
                                            WHERE rs2.ROUTE_ID = rs.ROUTE_ID AND rs2.SEGMENT_ID = sg2.ID)-- AND sg2.ARRIVAL_STATION_ID = 15) 
                ORDER BY rs.ROUTE_ID, rs.SEQUENCE_NUMBER, tt.SCHEDULED_DATE ASC
  
  
  
  

SELECT DISTINCT a.*
  FROM BOOKING_VIEW a
 WHERE EXISTS (SELECT 1 FROM BOOKING_VIEW b WHERE a.ROUTE_ID = b.ROUTE_ID AND b.ARRIVAL_STATION_ID = 15 AND (b.PERFORM_STOP = 1 OR b.IS_TERMINAL = 1) AND b.SEQUENCE_NUMBER >= a.SEQUENCE_NUMBER)
 AND a.DEPARTURE_DATE BETWEEN TO_DATE('01/01/2018 21:22', 'DD/MM/YYYY HH24:MI') AND TO_DATE('01/01/2020 21:22', 'DD/MM/YYYY HH24:MI')
  START WITH a.DEPARTURE_STATION_ID = 1
   CONNECT BY PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID
   ORDER SIBLINGS BY a.SEQUENCE_NUMBER
   
   SELECT * FROM BOOKING_VIEW a
                    WHERE 
                           a.SEQUENCE_NUMBER >= (SELECT MIN(b.SEQUENCE_NUMBER) FROM BOOKING_VIEW b
                                                    WHERE b.ROUTE_ID = a.ROUTE_ID AND b.DEPARTURE_STATION_ID = 1)
               --         AND a.SEQUENCE_NUMBER <= (SELECT MAX(c.SEQUENCE_NUMBER) FROM BOOKING_VIEW c
                 --                               WHERE c.ROUTE_ID = a.ROUTE_ID AND c.ARRIVAL_STATION_ID = 15)
                     --   AND (a.PERFORM_STOP = 1 OR a.IS_TERMINAL = 1)
                       -- AND a.DEPARTURE_DATE BETWEEN :startDate AND :endDate
                    ORDER BY a.ROUTE_ID, a.SEQUENCE_NUMBER ASC
  
  
  


SELECT distinct a.*, SYS_CONNECT_BY_PATH(a.ARRIVAL_STATION_ID, '/')
   FROM BOOKING_VIEW a
   START WITH a.DEPARTURE_STATION_ID = 1 AND a.DEPARTURE_DATE BETWEEN TO_DATE('01/01/2019 12:12', 'DD/MM/YYYY HH24:MI') AND TO_DATE('01/01/2019 22:22', 'DD/MM/YYYY HH24:MI')
   CONNECT BY a.DEPARTURE_STATION_ID = PRIOR a.ARRIVAL_STATION_ID
  -- AND PRIOR a.ARRIVAL_STATION_ID = 15 --AND (LEVEL > 1 OR (a.DEPARTURE_DATE BETWEEN TO_DATE('01/01/2019 12:12', 'DD/MM/YYYY HH24:MI') AND TO_DATE('01/01/2019 22:22', 'DD/MM/YYYY HH24:MI')))
   ORDER SIBLINGS BY ROUTE_ID, SEQUENCE_NUMBER, DEPARTURE_DATE DESC
   
   
   

SELECT a.*, LEVEL
   FROM BOOKING_VIEW a
   START WITH a.DEPARTURE_STATION_ID = 7
   CONNECT BY a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID 
 --  AND a.ARRIVAL_STATION_ID = 13-- AND a.DEPARTURE_DATE BETWEEN TO_DATE('01/01/2019 12:22', 'DD/MM/YYYY HH24:MI') AND TO_DATE('01/01/2020 21:22', 'DD/MM/YYYY HH24:MI')
   ORDER SIBLINGS BY ROUTE_ID, SEQUENCE_NUMBER, DEPARTURE_DATE
   
   
SELECT distinct a.*, 1 || SYS_CONNECT_BY_PATH(a.ARRIVAL_STATION_ID, '-') AS PATHS, LEVEL
   FROM BOOKING_VIEW a
   WHERE EXISTS (SELECT 1 FROM ROUTES_2_SEGMENTS rs, SEGMENTS sg WHERE a.ROUTE_ID = rs.ROUTE_ID AND sg.ID = rs.SEGMENT_ID AND (sg.DEPARTURE_STATION_ID = 1 OR sg.ARRIVAL_STATION_ID = 15))
   START WITH a.DEPARTURE_STATION_ID = 1 AND (LEVEL > 1 OR (a.DEPARTURE_DATE BETWEEN TO_DATE('01/01/2018 7:12', 'DD/MM/YYYY HH24:MI') AND TO_DATE('01/01/2029 20:22', 'DD/MM/YYYY HH24:MI')))
   CONNECT BY PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID
   AND PRIOR a.DEPARTURE_STATION_ID != 15 AND PRIOR a.ARRIVAL_DATE = a.DEPARTURE_DATE
   ORDER SIBLINGS BY a.ROUTE_ID, a.SEQUENCE_NUMBER, a.DEPARTURE_DATE DESC
   
   
   
   
SELECT distinct a.*, 7 || SYS_CONNECT_BY_PATH(a.ARRIVAL_STATION_ID, '-') AS PATHS, LEVEL
   FROM BOOKING_VIEW a
   WHERE EXISTS (SELECT 1 FROM ROUTES_2_SEGMENTS rs, SEGMENTS sg WHERE a.ROUTE_ID = rs.ROUTE_ID AND sg.ID = rs.SEGMENT_ID AND (sg.DEPARTURE_STATION_ID = 7 OR sg.ARRIVAL_STATION_ID = 15))
   START WITH a.DEPARTURE_STATION_ID = 7 AND (a.DEPARTURE_DATE BETWEEN TO_DATE('01/01/2019 07:53', 'DD/MM/YYYY HH24:MI') AND TO_DATE('01/01/2019 17:22', 'DD/MM/YYYY HH24:MI'))
   CONNECT BY PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID
   AND PRIOR a.DEPARTURE_STATION_ID != 15 AND PRIOR a.DEPARTURE_DATE <= a.ARRIVAL_DATE
   ORDER SIBLINGS BY a.DEPARTURE_DATE ASC
   
   
   
   
  
   
   
SELECT distinct a.*, 1 || SYS_CONNECT_BY_PATH(a.ARRIVAL_STATION_ID, '-') AS PATHS, LEVEL
                                                    FROM BOOKING_VIEW a
                                                    WHERE EXISTS (SELECT 1 FROM ROUTES_2_SEGMENTS rs, 
                                                                                    SEGMENTS sg
                                                                                WHERE a.ROUTE_ID = rs.ROUTE_ID 
                                                                                    AND sg.ID = rs.SEGMENT_ID 
                                                                                    AND (sg.DEPARTURE_STATION_ID = 1 OR sg.ARRIVAL_STATION_ID = 15)) 
                                                    AND a.DEPARTURE_STATION_ID != 15 
                                                    START WITH a.DEPARTURE_STATION_ID = 1 AND (a.DEPARTURE_DATE BETWEEN TO_DATE('01/01/2018 12:12', 'DD/MM/YYYY HH24:MI') AND TO_DATE('01/01/2028 12:12', 'DD/MM/YYYY HH24:MI'))
                                                    CONNECT BY PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID 
                                            --        AND PRIOR a.ARRIVAL_DATE <= a.DEPARTURE_DATE 
                                                  --  ORDER SIBLINGS BY a.ROUTE_ID, a.SEQUENCE_NUMBER, a.DEPARTURE_DATE DESC
                                                  
                                                  
                                                  

SELECT DISTINCT a.*, LEVEL
       FROM BOOKING_VIEW a
WHERE a.DEPARTURE_STATION_ID != :arrivalStationId AND a.ARRIVAL_STATION_ID != :departureStationId
       START WITH a.DEPARTURE_STATION_ID = :departureStationId AND (a.DEPARTURE_DATE BETWEEN TO_DATE('01/01/2018 02:12', 'DD/MM/YYYY HH24:MI') AND TO_DATE('01/01/2028 12:12', 'DD/MM/YYYY HH24:MI'))
       CONNECT BY NOCYCLE PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID 
       AND PRIOR a.ARRIVAL_DATE BETWEEN (a.DEPARTURE_DATE - 3/24) AND a.DEPARTURE_DATE
       ORDER SIBLINGS BY a.ROUTE_ID, a.SEQUENCE_NUMBER, a.DEPARTURE_DATE DESC                                                  
   
   
   
  
  