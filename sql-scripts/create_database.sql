@./01_create_tables.sql;
@./02_create_sequences.sql;
@./03_create_triggers.sql;

--DML
@./20_insert_trains.sql;
@./21_insert_stations.sql;
@./22_insert_customers.sql;
@./23_insert_route_nodes.sql;
@./24_insert_timetable.sql;

COMMIT;
