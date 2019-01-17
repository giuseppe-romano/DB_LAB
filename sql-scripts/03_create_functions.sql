/*
  This function checks if the route path is linked correctly or not.
  The route is composed by several segments where the arrival station of the segment i must be
  an departure station of the segment i + 1.
*/
CREATE OR REPLACE FUNCTION CHECK_ROUTE_LINKING(routeId IN NUMBER)
RETURN NUMBER 
IS
   -- variable declarations
    arrivalStationId NUMBER;
    departureStationId NUMBER;
    firstRow INTEGER;
    
    lastRouteSegmentId NUMBER;
    
    ret NUMBER := 0;
BEGIN
              
    firstRow := 1;
    
    FOR rec IN (
        SELECT * FROM ROUTES_2_SEGMENTS a
          WHERE a.ROUTE_ID = routeId
          ORDER BY a.SEQUENCE_NUMBER ASC)
    LOOP
        lastRouteSegmentId := rec.SEGMENT_ID;
        
        IF firstRow = 1 THEN
            SELECT r.ARRIVAL_STATION_ID INTO arrivalStationId 
                FROM SEGMENTS r
                WHERE r.ID = rec.SEGMENT_ID;
            
            firstRow := 0;
        ELSE
            SELECT r.DEPARTURE_STATION_ID INTO departureStationId 
                FROM SEGMENTS r
                WHERE r.ID = rec.SEGMENT_ID;
                
            --If the link between the two segments isn't correct than the function returns 1
            IF arrivalStationId <> departureStationId THEN
                ret := 1;
            END IF;
        END IF;
    END LOOP;
    
    RETURN ret;
END;
