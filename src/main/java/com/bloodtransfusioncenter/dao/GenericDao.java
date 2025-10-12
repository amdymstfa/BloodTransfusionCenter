package com.bloodtransfusioncenter.dao;

import java.util.List ;
import java.util.Optional ;

/**
 * Generic interface DAO providing basic CRUD operations.
 * @param <T>
 */
interface GenericDao<T> {
    /**
     * Save a new entity in the database
     * @param entity the entity that we save
     */
    void save(T entity);

    /**
     * Update an existing entity in the database
     * @param entity the entity to update
     */
    void update(T entity);

    /**
     * Delete an existing entity in the database
     * @param id the ID of the entity to delete
     */
    void delete(Long id);

    /**
     * Find an entity by his id
     * @param id the ID of entity that we need to find
     * @return an Optional containing the entity if found,empty otherwise
     */
    Optional<T> findById(Long id);

    /**
     * Retrieves all entities
     * @return a List of all entities
     */
    List<T> findAll();

}