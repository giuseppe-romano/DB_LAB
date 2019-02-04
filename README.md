# Heaven's Rail 
Progettazione e sviluppo di una base di dati relazionale per la gestione di un sistema ferroviario.

## Installare lo schema DB
    Gli script sql per la creazione del DB sono organizzati nella directory sql-scripts.
    
    1. Utilizzando sqlplus a riga di comando o Sql Developer, eseguire lo script:
        > ./sql-scripts/create_database.sql
        
        Verificare che non vi siano errori.
     

## Compilare i sorgenti con maven

### 1. Prerequisiti di sistema 

    # JDK version 8
    # Maven version 3.x

### 2. Passi da eseguire

    1. Installare i driver jdbc nel repository locale di maven
        > mvn install:install-file -Dfile=jdbc/ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar

    2. Compilare il progetto
        > mvn clean install
        
    3. Avviare l'applicazione
        > java -jar target/heaven-rail-1.0-jar-with-dependencies.jar


## Compilare i sorgenti con IDE (Netbeans, IntelliJ, etc)

### 1. Prerequisiti di sistema 

    # JDK version 8
    
    1. Aprire il progetto selezionando il file pom.xml
    
    2. Configurare il classpath con il driver jdbc (nella directory /jdbc)
    
    3. Eseguire l'applicazione con il pulsante "Run" 