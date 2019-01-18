-- tutte le stazioni di inizio tratta
SELECT st.*, r.ID from 
  ROUTES r, ROUTES_2_SEGMENTS rs, SEGMENTS sg, STATIONS st
WHERE 
  r.ID = rs.ROUTE_ID
  AND rs.SEGMENT_ID = sg.ID
  AND sg.DEPARTURE_STATION_ID = st.ID
  AND rs.SEQUENCE_NUMBER = (SELECT MIN(SEQUENCE_NUMBER) FROM ROUTES_2_SEGMENTS rs2
                                 WHERE r.ID = rs2.ROUTE_ID)
  AND r.ACTIVE = 1 and r.ID = 2
  
  
--tutte le stazioni intermedie in cui viene effettuata la fermata
SELECT st.*, r.ID from 
  ROUTES r, ROUTES_2_SEGMENTS rs, SEGMENTS sg, STATIONS st
WHERE 
  r.ID = rs.ROUTE_ID
  AND rs.SEGMENT_ID = sg.ID
  AND sg.ARRIVAL_STATION_ID = st.ID
  AND rs.PERFORM_STOP = 1
  AND rs.IS_TERMINAL = 0
  --AND EXISTS (SELECT 1 FROM ROUTES_2_SEGMENTS rs2, SEGMENTS sg2 WHERE rs2.SEGMENT_ID = sg2.ID AND sg2.ARRIVAL_STATION_ID = sg.DEPARTURE_STATION_ID AND rs2.PERFORM_STOP = 1)
  AND r.ACTIVE = 1
    
  AND  r.ID = 2
    
    
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
  
  
  
  
  
  