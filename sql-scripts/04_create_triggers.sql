/*
  This trigger checks if the route path is linked correctly or not.
  The route is composed by several segments where the arrival station of the segment i must be
  an departure station of the segment i + 1.
  
  The trigger works on AFTER INSERT OR UPDATE OR DELETE
*/
CREATE OR REPLACE TRIGGER CHECK_ROUTE_LINKING_AIUD
AFTER INSERT OR UPDATE OR DELETE
   ON ROUTES_2_SEGMENTS
DECLARE
   -- variable declarations
    arrivalStationId NUMBER;
    departureStationId NUMBER;
    firstRow INTEGER;
    
    lastRouteSegmentId NUMBER;
BEGIN
    --Loops on each distinct route_id
    FOR routeSeg IN (SELECT DISTINCT ROUTE_ID FROM ROUTES_2_SEGMENTS)
      LOOP
          --Set the active flag to be true
          UPDATE ROUTES
              SET ACTIVE = 1
              WHERE ID = routeSeg.ROUTE_ID;
              
          --If the link between the two segments isn't correct than the route will be marked an not active
          IF CHECK_ROUTE_LINKING(routeSeg.ROUTE_ID) = 1 THEN
              UPDATE ROUTES
                SET ACTIVE = 0 --false
                WHERE ID = routeSeg.ROUTE_ID;
          END IF;              
    END LOOP;  
END;