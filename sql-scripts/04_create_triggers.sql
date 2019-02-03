/*
  This trigger checks if the route path is linked correctly or not.
  The route is composed by several segments where the arrival station of the segment i must be
  an departure station of the segment i + 1.
  
  The trigger works on AFTER INSERT OR UPDATE OR DELETE
*/
CREATE OR REPLACE TRIGGER CHECK_ROUTE_LINKING_AIUD
AFTER INSERT OR UPDATE OR DELETE
   ON ROUTES_2_SEGMENTS
   FOR EACH ROW
DECLARE
    
   -- variable declarations
    arrivalStationId NUMBER;
    departureStationId NUMBER;
    firstRow INTEGER;
    
    lastRouteSegmentId NUMBER;
BEGIN
  
      --Set the active flag to be true
      UPDATE ROUTES
          SET ACTIVE = 1
          WHERE ID = :NEW.ROUTE_ID;
          
      --If the link between the two segments isn't correct than the route will be marked an not active
      IF CHECK_ROUTE_LINKING(:NEW.ROUTE_ID) = 1 THEN
          UPDATE ROUTES
            SET ACTIVE = 0 --false
            WHERE ID = :NEW.ROUTE_ID;
      END IF;              
  
END;