# DB_LAB

# Compilare i sorgenti

## Prerequisiti di sistema

    JDK version 8
    Maven version 3.x

    1. Installare i driver jdbc nel repository maven
        mvn install:install-file -Dfile=jdbc/ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar

    2. Compilare ed impacchettare l'artefatto
        mvn clean install

