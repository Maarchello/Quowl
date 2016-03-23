package com.quowl.quowl.web.beans.system;

/**
 * Interface should be implemented by any
 * classes that are WebBeans or DTO objects.
 *
 * @param <D> the type of entity.
 */
public interface WebBean<D> {

    /**
     * Copies all data from the given entity
     * to DTO object.
     *
     * @param obj the entity object to copy from.
     */
    void copyDataFromDomain(D obj);

    /**
     * Copies all data to the given entity.
     *
     * @param obj the entity object to copy in.
     */
    void copyDataToDomain(D obj);


}
