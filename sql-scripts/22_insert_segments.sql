--Da Napoli Centrale a Roma Termini
INSERT INTO SEGMENTS (ID, DEPARTURE_STATION_ID, ARRIVAL_STATION_ID, DISTANCE) VALUES (SEGMENTS_SEQ.NEXTVAL, 1, 7, 200.00);

--Da Roma Termini a Bologna
INSERT INTO SEGMENTS (ID, DEPARTURE_STATION_ID, ARRIVAL_STATION_ID, DISTANCE) VALUES (SEGMENTS_SEQ.NEXTVAL, 7, 10, 432.00);

--Da Bologna a Parma
INSERT INTO SEGMENTS (ID, DEPARTURE_STATION_ID, ARRIVAL_STATION_ID, DISTANCE) VALUES (SEGMENTS_SEQ.NEXTVAL, 10, 13, 132.00);

--Da Parma a Milano Centrale
INSERT INTO SEGMENTS (ID, DEPARTURE_STATION_ID, ARRIVAL_STATION_ID, DISTANCE) VALUES (SEGMENTS_SEQ.NEXTVAL, 13, 15, 310.00);