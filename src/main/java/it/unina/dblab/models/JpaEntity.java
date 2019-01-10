package it.unina.dblab.models;

public interface JpaEntity<T> {

    Integer getId();

    T copy();
}
