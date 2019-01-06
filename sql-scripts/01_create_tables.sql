DROP TABLE BOOKINGS CASCADE CONSTRAINTS;
/
DROP TABLE TIMETABLE CASCADE CONSTRAINTS;
/
DROP TABLE TICKET_DEFINITIONS CASCADE CONSTRAINTS;
/
DROP TABLE ROUTE_NODES CASCADE CONSTRAINTS;
/
DROP TABLE TRAINS;
/
DROP TABLE STATIONS;
/
DROP TABLE CUSTOMERS;
/


CREATE TABLE TRAINS (
     ID                 NUMBER PRIMARY KEY,
     CATEGORY           VARCHAR2(256 BYTE) NOT NULL,
     CODE           	VARCHAR2(64 BYTE) NOT NULL,
     NOMINAL_SPEED      NUMBER(5, 2),
     CARRIAGES          NUMBER(2)
);
/
COMMENT ON TABLE TRAINS IS 'The table modelling all the trains belonging to the railway company';
COMMENT ON COLUMN TRAINS.ID IS 'The primary key of the table';
COMMENT ON COLUMN TRAINS.CATEGORY IS 'The train''s category like ''Frecciarossa'', ''Frecciargento'', ''Intercity'' etc etc';
COMMENT ON COLUMN TRAINS.CODE IS 'The identification code of the train';
COMMENT ON COLUMN TRAINS.NOMINAL_SPEED IS 'The nominal speeed of the train. It is expressed in kilometers/hour';
COMMENT ON COLUMN TRAINS.CARRIAGES IS 'The number of carriages attached to the train';
/
---------------------------------------------------------------------------------------------------------


CREATE TABLE STATIONS (
     ID                 NUMBER PRIMARY KEY,
     NAME               VARCHAR2(256 BYTE) NOT NULL,
     ADDRESS            VARCHAR2(256 BYTE) NOT NULL,
     TELEPHONE          VARCHAR2(32 BYTE),
     DISABLED_ACCESS    NUMBER(1) DEFAULT 0,
     NUM_PLATFORMS      NUMBER(2),
     RESTAURANT         NUMBER(1) DEFAULT 0,
     TAXI_SERVICE       NUMBER(1) DEFAULT 0,
     
     CONSTRAINT STATION_NAME_UK
        UNIQUE (NAME)
);
/
COMMENT ON TABLE STATIONS IS 'The table modelling all the stations on which the railway company performs routes';
COMMENT ON COLUMN STATIONS.ID IS 'The primary key of the table';
COMMENT ON COLUMN STATIONS.NAME IS 'The station''s name';
COMMENT ON COLUMN STATIONS.ADDRESS IS 'The station''s address';
COMMENT ON COLUMN STATIONS.TELEPHONE IS 'The station''s telephone number';
COMMENT ON COLUMN STATIONS.DISABLED_ACCESS IS 'The flag indicating if the station is compliant to the accessibility rules';
COMMENT ON COLUMN STATIONS.NUM_PLATFORMS IS 'The number of platforms provided by the station';
COMMENT ON COLUMN STATIONS.RESTAURANT IS 'The flag indicating if the station is equipped with restaurants';
COMMENT ON COLUMN STATIONS.TAXI_SERVICE IS 'The flag indicating if there is a taxi station near the station';
/
---------------------------------------------------------------------------------------------------------


CREATE TABLE ROUTE_NODES (
     ID                     NUMBER PRIMARY KEY,
     DEPARTURE_STATION_ID   NUMBER NOT NULL,
     ARRIVAL_STATION_ID     NUMBER NOT NULL,
     DISTANCE               NUMBER(5),
     NEXT_ID				NUMBER,
     
     CONSTRAINT ROUTE_NODES_2_DEP_STATION
        FOREIGN KEY (DEPARTURE_STATION_ID)
        REFERENCES STATIONS (ID),
        
     CONSTRAINT ROUTE_NODES_2_ARR_STATION
        FOREIGN KEY (ARRIVAL_STATION_ID)
        REFERENCES STATIONS (ID),
		
	 CONSTRAINT ROUTE_NODES_2_NEXT_NODE
        FOREIGN KEY (NEXT_ID)
        REFERENCES ROUTE_NODES (ID),
		
	 CONSTRAINT ROUTE_NODES_CK_ID_EQ_NEXT_ID CHECK (ID <> NEXT_ID),
   CONSTRAINT ROUTE_NODES_CK_DEP_ARR_ST CHECK (DEPARTURE_STATION_ID <> ARRIVAL_STATION_ID),
	 
	 CONSTRAINT ROUTE_NODES_UK
        UNIQUE (DEPARTURE_STATION_ID, ARRIVAL_STATION_ID, NEXT_ID)
);
/
COMMENT ON TABLE ROUTE_NODES IS 'The table modelling all the routes covered by the railway company. Each route node is a segment connecting two single stations.';
COMMENT ON COLUMN ROUTE_NODES.ID IS 'The primary key of the table.';
COMMENT ON COLUMN ROUTE_NODES.DEPARTURE_STATION_ID IS 'The departure station id. It is the starting point of the route node.';
COMMENT ON COLUMN ROUTE_NODES.ARRIVAL_STATION_ID IS 'The arrival station id. It is the end point of the route node.';
COMMENT ON COLUMN ROUTE_NODES.DISTANCE IS 'The distance between the two stations. It is expressed in kilometers.';
COMMENT ON COLUMN ROUTE_NODES.NEXT_ID IS 'The id of the next route.';
/
---------------------------------------------------------------------------------------------------------


CREATE TABLE TIMETABLE (
     TRAIN_ID               NUMBER NOT NULL,
     ROUTE_NODE_ID          NUMBER NOT NULL,
     DEPARTURE_TIME         TIMESTAMP NOT NULL,
     ARRIVAL_TIME           TIMESTAMP NOT NULL,
     WEEK_DAY               NUMBER(1),
     
     CONSTRAINT TIMETABLE_2_TRAINS
        FOREIGN KEY (TRAIN_ID)
        REFERENCES TRAINS (ID),
        
     CONSTRAINT TIMETABLE_2_ROUTE_NODES
        FOREIGN KEY (ROUTE_NODE_ID)
        REFERENCES ROUTE_NODES (ID),
		
	 CONSTRAINT TIMETABLE_WEEK_DAY CHECK (WEEK_DAY IN (1, 7))
);
/
COMMENT ON TABLE TIMETABLE IS 'The table modelling all the routes covered by the railway company';
/

---------------------------------------------------------------------------------------------------------



CREATE TABLE CUSTOMERS (
     ID                 NUMBER PRIMARY KEY,
     FIRST_NAME         VARCHAR2(64 BYTE) NOT NULL,
     LAST_NAME          VARCHAR2(64 BYTE) NOT NULL,
     BORN_DATE          DATE,
     EMAIL              VARCHAR2(128 BYTE),
	 PASSWORD 			VARCHAR2(128 BYTE),
     
     CONSTRAINT CUSTOMER_EMAIL_UK
        UNIQUE (EMAIL)
);
/
COMMENT ON TABLE CUSTOMERS IS 'The table modelling all the customers registered';
COMMENT ON COLUMN CUSTOMERS.ID IS 'The primary key of the table';
COMMENT ON COLUMN CUSTOMERS.FIRST_NAME IS 'The customer''s first name';
COMMENT ON COLUMN CUSTOMERS.LAST_NAME IS 'The customer''s last name';
COMMENT ON COLUMN CUSTOMERS.BORN_DATE IS 'The customer''s born date';
COMMENT ON COLUMN CUSTOMERS.EMAIL IS 'The customer''s email';
COMMENT ON COLUMN CUSTOMERS.PASSWORD IS 'The customer''s MD5 encrypted password';
/
---------------------------------------------------------------------------------------------------------


CREATE TABLE TICKET_DEFINITIONS (
     ID                     NUMBER PRIMARY KEY,
     ROUTE_ID               NUMBER NOT NULL,
     TRAVEL_CLASS           VARCHAR2(16 BYTE),
     PRICE                  NUMBER(3, 2),
     
     CONSTRAINT TICKET_DEF_2_ROUTE_DEFS
        FOREIGN KEY (ROUTE_ID)
        REFERENCES ROUTE_NODES (ID)
);
/
COMMENT ON TABLE TICKET_DEFINITIONS IS 'The table modelling all the routes covered by the railway company';
/

---------------------------------------------------------------------------------------------------------

CREATE TABLE BOOKINGS (
     ID                     NUMBER PRIMARY KEY,
     TICKET_DEFINITION_ID   NUMBER NOT NULL,
     CUSTOMER_ID            NUMBER NOT NULL,
     PURCHASE_DATE          DATE,
     
     CONSTRAINT BOOKINGS_2_TICKET_DEFS
        FOREIGN KEY (TICKET_DEFINITION_ID)
        REFERENCES TICKET_DEFINITIONS (ID),
        
     CONSTRAINT BOOKINGS_2_CUSTOMERS
        FOREIGN KEY (CUSTOMER_ID)
        REFERENCES CUSTOMERS (ID)
);
/
COMMENT ON TABLE BOOKINGS IS 'The table modelling all the routes covered by the railway company';
/
