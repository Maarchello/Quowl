package com.quowl.quowl.web.beans.system;

/**
 * Interface should be implemented by any
 * classes that are WebBeans or DTO object.
 * Unlike classes that implementing the WebBean interface
 * these classes are only for data transfer
 * and display.
 *
 * @see WebBean
 * @param <D>
 */

public interface ReadOnlyWebBean<D> {

    /**
     * Copies all data from the given entity
     * to DTO object.
     *
     * @param obj the entity object to copy from.
     */
    void copyDataFromDomain(D obj);

}
