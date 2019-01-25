CREATE OR REPLACE VIEW BOOKING_VIEW AS
SELECT DISTINCT a.*, LEVEL - 1 as STOPS
  FROM (
  SELECT DISTINCT sg.DEPARTURE_STATION_ID, 
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
                  AND (rs.PERFORM_STOP = 1 OR rs.IS_TERMINAL = 1)
  ) a
   CONNECT BY PRIOR a.ARRIVAL_STATION_ID = a.DEPARTURE_STATION_ID
   ORDER SIBLINGS BY a.SEQUENCE_NUMBER
/
