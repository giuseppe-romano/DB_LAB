package it.unina.dblab.models;

public interface JpaEntity<T> {

    Object getId();

    T copy();
}
