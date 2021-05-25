package com.epam.esm.dao.helper;

import com.epam.esm.entity.identifiable.Identifiable;

/**
 * This interface defines helper methods for updating objects in data warehouse.
 *
 * @param <T> This is type of object which can used by this interface.
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface DaoHelper<T extends Identifiable> {

    /**
     * This method is used to get not nullable fields of updatedIdentifiable
     * and update the same value's fields of existingIdentifiable.
     *
     * @param updatedIdentifiable  This is identifiable which contains values for
     *                             updating.
     * @param existingIdentifiable This is identifiable in which need to update fields
     *                             from updatedIdentifiable.
     */
    void update(T updatedIdentifiable, T existingIdentifiable);

}