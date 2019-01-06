CREATE OR REPLACE TRIGGER ROUTE_NODES_STATION_BI
BEFORE UPDATE
   ON ROUTE_NODES
   FOR EACH ROW

DECLARE
   -- variable declarations

BEGIN
   -- trigger code
null;

END;

-----------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER TIMETABLE_TIME_OVERLAPPING
BEFORE UPDATE
   ON TIMETABLE
   FOR EACH ROW

DECLARE
   -- variable declarations

BEGIN
   -- trigger code
null;

END;