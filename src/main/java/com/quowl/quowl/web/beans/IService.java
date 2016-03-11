package com.quowl.quowl.web.beans;

import java.io.Serializable;
import java.util.List;


/**
 * Interface for generic CRUD operations on a service for
 * a specific type.
 *
 * @author nllsdfx
 */
public interface IService<T, ID extends Serializable> {

    /**
     * Saves the given object.
     *
     * @param object the entity to save.
     */
    void save(T object);

    /**
     * Deletes the given object.
     *
     * @param object the entity to delete.
     */
    void delete(T object);

    /**
     * Deletes the entity with given id.
     *
     * @param id must not be null.
     */
    void delete(ID id);


    /**
     * Finds all entities.
     *
     * @return list of founded entities.
     */
    List<T> findAll();

    /**
     * Find entity by given id.
     *
     * @param id must not be null.
     * @return the entity with the given id.
     */
    T findOne(ID id);

    /**
     * Checks whether entity with the given id exists.
     *
     * @param id must not be null.
     * @return <code>true</code> if entity exists,
     * <code>false</code> otherwise.
     */
    boolean exists(ID id);
}


