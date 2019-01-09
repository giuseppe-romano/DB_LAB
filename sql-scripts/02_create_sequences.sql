DROP SEQUENCE CUSTOMERS_SEQ;
/
DROP SEQUENCE TRAINS_SEQ;
/
DROP SEQUENCE STATIONS_SEQ;
/
DROP SEQUENCE ROUTE_SEGMENTS_SEQ;
/
DROP SEQUENCE TICKET_DEFINITIONS_SEQ;
/


CREATE SEQUENCE CUSTOMERS_SEQ
 MINVALUE 1
 MAXVALUE 9999999999
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 /
 
-------------------------------------------------------------------------

CREATE SEQUENCE TRAINS_SEQ
 MINVALUE 1
 MAXVALUE 9999999999
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 /
 
-------------------------------------------------------------------------

CREATE SEQUENCE STATIONS_SEQ
 MINVALUE 1
 MAXVALUE 9999999999
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 /
 
-------------------------------------------------------------------------

CREATE SEQUENCE ROUTE_SEGMENTS_SEQ
 MINVALUE 1
 MAXVALUE 9999999999
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
