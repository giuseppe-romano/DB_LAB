DROP SEQUENCE CUSTOMERS_SEQ;
/
DROP SEQUENCE TRAINS_SEQ;
/
DROP SEQUENCE STATIONS_SEQ;
/
DROP SEQUENCE ROUTE_DEFINITIONS_SEQ;
/
DROP SEQUENCE TICKET_DEFINITIONS_SEQ;
/


CREATE SEQUENCE CUSTOMERS_SEQ
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 /
 
-------------------------------------------------------------------------

CREATE SEQUENCE TRAINS_SEQ
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 /
 
-------------------------------------------------------------------------

CREATE SEQUENCE STATIONS_SEQ
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 /
 
-------------------------------------------------------------------------

CREATE SEQUENCE ROUTE_DEFINITIONS_SEQ
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 /
 
-------------------------------------------------------------------------

CREATE SEQUENCE TICKET_DEFINITIONS_SEQ
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 /
 
-------------------------------------------------------------------------
