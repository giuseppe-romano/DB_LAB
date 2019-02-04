@./01_create_tables.sql;
@./02_create_sequences.sql;
@./03_create_functions.sql;
@./04_create_views.sql;

--DML
@./20_insert_trains.sql;
@./21_insert_stations.sql;
@./22_insert_segments.sql;
@./23_insert_routes.sql;
@./24_insert_routes_2_segments.sql;
@./25_insert_timetable.sql;

COMMIT;

