/*
  This function checks if the route path is linked correctly or not.
  The route is composed by several segments where the arrival station of the segment i must be
  an departure station of the segment i + 1.
*/
CREATE OR REPLACE FUNCTION CHECK_ROUTE_LINKING(routeId IN NUMBER)
RETURN NUMBER 
IS
   -- variable declarations
    reachedStationId NUMBER;
    firstRow INTEGER;
    
    ret NUMBER := 0;
BEGIN
              
    firstRow := 1;
    
    FOR rec IN (
        SELECT sg.* FROM ROUTES_2_SEGMENTS rs, SEGMENTS sg
            WHERE rs.SEGMENT_ID = sg.ID
                AND rs.ROUTE_ID = 2
                ORDER BY rs.SEQUENCE_NUMBER ASC)
    LOOP 
        IF firstRow = 0 THEN
            --If the link between the two segments isn't correct than the function returns 1
            IF reachedStationId <> rec.DEPARTURE_STATION_ID THEN
                ret := 1;
                EXIT;
            END IF;
        END IF;
        
        reachedStationId := rec.ARRIVAL_STATION_ID;
        firstRow := 0;

    END LOOP;
    
    RETURN ret;
END;
/

-------------------------------------------------------------------------------------------
/*
  This function computes the foreseen departure date for each path 
*/
create or replace FUNCTION COMPUTE_DEPARTURE_DATE(timeTableId IN NUMBER, departureStationId IN NUMBER)
RETURN TIMESTAMP 
IS
    timetableRec TIMETABLE%ROWTYPE;
    trainSpeed NUMBER;
        
    tmpDate TIMESTAMP;
BEGIN
    --Get the timetable record
    SELECT * INTO timetableRec FROM TIMETABLE
        WHERE ID = timeTableId;
        
    

    --Get the nominal speed of the train for that timetable record
    SELECT t.NOMINAL_SPEED INTO trainSpeed FROM TRAINS t
        WHERE ID = timetableRec.TRAIN_ID;
        
    --Get all the segments of the route for that timetable record
    tmpDate := timetableRec.SCHEDULED_DATE;
    FOR segmentRec IN (
        SELECT sg.* FROM ROUTES_2_SEGMENTS rs, SEGMENTS sg
            WHERE rs.SEGMENT_ID = sg.ID
                AND rs.ROUTE_ID = timetableRec.ROUTE_ID
                AND rs.SEQUENCE_NUMBER < (SELECT rs2.SEQUENCE_NUMBER FROM ROUTES_2_SEGMENTS rs2, SEGMENTS sg2
                                            WHERE rs2.ROUTE_ID = rs.ROUTE_ID AND rs2.SEGMENT_ID = sg2.ID AND sg2.DEPARTURE_STATION_ID = departureStationId) 
                ORDER BY rs.SEQUENCE_NUMBER ASC)
    LOOP
        --This formula adds the time needed for the that distance with that train speed
        tmpDate := tmpDate + (segmentRec.DISTANCE / trainSpeed)/24;
           
    END LOOP;
        
    RETURN tmpDate;
END;
/


-------------------------------------------------------------------------------------------
/*
  This function computes the foreseen arrival date for each path 
*/
create or replace FUNCTION COMPUTE_ARRIVAL_DATE(timeTableId IN NUMBER, arrivalStationId IN NUMBER)
RETURN TIMESTAMP 
IS
    timetableRec TIMETABLE%ROWTYPE;
    trainSpeed NUMBER;
        
    tmpDate TIMESTAMP;
BEGIN
    --Get the timetable record
    SELECT * INTO timetableRec FROM TIMETABLE
        WHERE ID = timeTableId;
        
    

    --Get the nominal speed of the train for that timetable record
    SELECT t.NOMINAL_SPEED INTO trainSpeed FROM TRAINS t
        WHERE ID = timetableRec.TRAIN_ID;
        
    --Get all the segments of the route for that timetable record
    tmpDate := timetableRec.SCHEDULED_DATE;
    FOR segmentRec IN (
        SELECT sg.* FROM ROUTES_2_SEGMENTS rs, SEGMENTS sg
            WHERE rs.SEGMENT_ID = sg.ID
                AND rs.ROUTE_ID = timetableRec.ROUTE_ID
                AND rs.SEQUENCE_NUMBER <= (SELECT rs2.SEQUENCE_NUMBER FROM ROUTES_2_SEGMENTS rs2, SEGMENTS sg2
                                            WHERE rs2.ROUTE_ID = rs.ROUTE_ID AND rs2.SEGMENT_ID = sg2.ID AND sg2.ARRIVAL_STATION_ID = arrivalStationId) 
                ORDER BY rs.SEQUENCE_NUMBER ASC)
    LOOP
        --This formula adds the time needed for the that distance with that train speed
        tmpDate := tmpDate + (segmentRec.DISTANCE / trainSpeed)/24;
           
    END LOOP;
        
    RETURN tmpDate;
END;
/