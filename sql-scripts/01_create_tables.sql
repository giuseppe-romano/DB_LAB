DROP TABLE TIMETABLE CASCADE CONSTRAINTS;
/
DROP TABLE ROUTES_2_SEGMENTS CASCADE CONSTRAINTS;
/
DROP TABLE ROUTES CASCADE CONSTRAINTS;
/
DROP TABLE SEGMENTS CASCADE CONSTRAINTS;
/
DROP TABLE TRAINS;
/
DROP TABLE STATIONS;
/


CREATE TABLE TRAINS (
     ID                 NUMBER PRIMARY KEY,
     CATEGORY           VARCHAR2(256 BYTE) NOT NULL,
     CODE           	  VARCHAR2(64 BYTE) NOT NULL,
     NOMINAL_SPEED      NUMBER(3),
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
COMMENT ON TABLE STATIONS IS 'The table modelling all the stations on which the railway company performs segments';
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


CREATE TABLE SEGMENTS (
     ID                     NUMBER PRIMARY KEY,
     DEPARTURE_STATION_ID   NUMBER NOT NULL,
     ARRIVAL_STATION_ID     NUMBER NOT NULL,
     DISTANCE               NUMBER(5),
     
     CONSTRAINT SEGMENTS_2_DEP_STATION
        FOREIGN KEY (DEPARTURE_STATION_ID)
        REFERENCES STATIONS (ID),
        
     CONSTRAINT SEGMENTS_2_ARR_STATION
        FOREIGN KEY (ARRIVAL_STATION_ID)
        REFERENCES STATIONS (ID),
				
     CONSTRAINT SEGMENTS_CK_DEP_ARR_ST CHECK (DEPARTURE_STATION_ID <> ARRIVAL_STATION_ID),
     CONSTRAINT SEGMENTS_UK UNIQUE (DEPARTURE_STATION_ID, ARRIVAL_STATION_ID)
);
/
COMMENT ON TABLE SEGMENTS IS 'The table modelling a segment connecting two single stations in one way (one direction only).';
COMMENT ON COLUMN SEGMENTS.ID IS 'The primary key of the table.';
COMMENT ON COLUMN SEGMENTS.DEPARTURE_STATION_ID IS 'The departure station id. It is the starting point of the route node.';
COMMENT ON COLUMN SEGMENTS.ARRIVAL_STATION_ID IS 'The arrival station id. It is the end point of the route node.';
COMMENT ON COLUMN SEGMENTS.DISTANCE IS 'The distance between the two stations. It is expressed in kilometers.';
/

---------------------------------------------------------------------------------------------------------

CREATE TABLE ROUTES (
     ID                     NUMBER PRIMARY KEY,
     NAME                   VARCHAR2(256 BYTE) NOT NULL,
     ACTIVE                 NUMBER(1) DEFAULT 0
);
/
COMMENT ON TABLE ROUTES IS 'The table modelling a route composed by several segments linked together';
COMMENT ON COLUMN ROUTES.ID IS 'The primary key of the table.';
COMMENT ON COLUMN ROUTES.NAME IS 'The mnemonic name of the route';
COMMENT ON COLUMN ROUTES.ACTIVE IS 'The flag indicating wether the route is well linked between segments or not';
/
---------------------------------------------------------------------------------------------------------

CREATE TABLE ROUTES_2_SEGMENTS (
     ROUTE_ID               NUMBER NOT NULL,
     SEGMENT_ID             NUMBER NOT NULL,
     PERFORM_STOP           NUMBER(1) DEFAULT 0,
     SEQUENCE_NUMBER        NUMBER,
     IS_TERMINAL            NUMBER(1) DEFAULT 0,
     
     CONSTRAINT FK_TO_ROUTES
        FOREIGN KEY (ROUTE_ID)
        REFERENCES ROUTES (ID),
        
     CONSTRAINT FK_TO_SEGMENTS
        FOREIGN KEY (SEGMENT_ID)
        REFERENCES SEGMENTS (ID),
        
     CONSTRAINT ROUTES_2_SEGMENTS_UK UNIQUE (ROUTE_ID, SEGMENT_ID)
);
/
COMMENT ON TABLE ROUTES_2_SEGMENTS IS 'The table modelling the many-to-many relationship between the segments and the routes';
COMMENT ON COLUMN ROUTES_2_SEGMENTS.ROUTE_ID IS 'The foreign key pointing to route segments';
COMMENT ON COLUMN ROUTES_2_SEGMENTS.SEGMENT_ID IS 'The foreign key pointing to routes';
COMMENT ON COLUMN ROUTES_2_SEGMENTS.PERFORM_STOP IS 'The flag indicating wether the stop will be performed at the arrival station of that segment';
COMMENT ON COLUMN ROUTES_2_SEGMENTS.SEQUENCE_NUMBER IS 'The sequence indicating the order of the segments which compose the route';
COMMENT ON COLUMN ROUTES_2_SEGMENTS.IS_TERMINAL IS 'The flag indicating that the route end here. In other word, the segment is the last of the route';
/

----------------------------------------------------------------------------------------------------------------

CREATE TABLE TIMETABLE (
     ID                     NUMBER PRIMARY KEY,
     TRAIN_ID               NUMBER NOT NULL,
     ROUTE_ID               NUMBER NOT NULL,
     SCHEDULED_DATE         TIMESTAMP NOT NULL,
     DEPARTURE_DATE         TIMESTAMP,
     ARRIVAL_DATE           TIMESTAMP,
     DEPARTURE_PLATFORM     NUMBER,
     ARRIVAL_PLATFORM       NUMBER,
     
     CONSTRAINT TIMETABLE_2_TRAINS
        FOREIGN KEY (TRAIN_ID)
        REFERENCES TRAINS (ID),
        
     CONSTRAINT TIMETABLE_2_ROUTES
        FOREIGN KEY (ROUTE_ID)
        REFERENCES ROUTES (ID)
);
/
COMMENT ON TABLE TIMETABLE IS 'The table modelling all the scheduled routes at specific date/time';
COMMENT ON COLUMN TIMETABLE.ID IS 'The primary key of the table';
COMMENT ON COLUMN TIMETABLE.TRAIN_ID IS 'The foreign key pointing to trains';
COMMENT ON COLUMN TIMETABLE.ROUTE_ID IS 'The foreign key pointing to routes';
COMMENT ON COLUMN TIMETABLE.SCHEDULED_DATE IS 'The scheduled departure date/time';
COMMENT ON COLUMN TIMETABLE.DEPARTURE_DATE IS 'The real departure date/time';
COMMENT ON COLUMN TIMETABLE.ARRIVAL_DATE IS 'The real arrival date/time';
COMMENT ON COLUMN TIMETABLE.DEPARTURE_PLATFORM IS 'The departure platform number';
COMMENT ON COLUMN TIMETABLE.ARRIVAL_PLATFORM IS 'The arrival platform number';
/
