--Timetable for sunday, train 1, route 1
INSERT INTO TIMETABLE (ID, TRAIN_ID, ROUTE_ID, SCHEDULED_DATE, DEPARTURE_DATE, ARRIVAL_DATE, DEPARTURE_PLATFORM, ARRIVAL_PLATFORM) VALUES (TIMETABLE_SEQ.NEXTVAL, 1, 1, TO_TIMESTAMP ('07:12', 'HH24:MI'), NULL, NULL, 1, 2);
--INSERT INTO TIMETABLE (ID, TRAIN_ID, ROUTE_ID, SCHEDULED_DATE, DEPARTURE_DATE, ARRIVAL_DATE, DEPARTURE_PLATFORM, ARRIVAL_PLATFORM) VALUES (TIMETABLE_SEQ.NEXTVAL, 2, 1, TO_TIMESTAMP ('09:12', 'HH24:MI'), NULL, NULL, 1, 2);
INSERT INTO TIMETABLE (ID, TRAIN_ID, ROUTE_ID, SCHEDULED_DATE, DEPARTURE_DATE, ARRIVAL_DATE, DEPARTURE_PLATFORM, ARRIVAL_PLATFORM) VALUES (TIMETABLE_SEQ.NEXTVAL, 2, 2, TO_TIMESTAMP ('19:12', 'HH24:MI'), NULL, NULL, 1, 2);

--INSERT INTO TIMETABLE (ID, TRAIN_ID, ROUTE_ID, SCHEDULED_DATE, DEPARTURE_DATE, ARRIVAL_DATE, DEPARTURE_PLATFORM, ARRIVAL_PLATFORM) VALUES (TIMETABLE_SEQ.NEXTVAL, 2, 3, TO_TIMESTAMP ('19:12', 'HH24:MI'), NULL, NULL, 1, 2);

         