package com.epam.esm.dao.api;

import com.epam.esm.entity.identifiable.Tag;

import java.util.Optional;

/**
 * The interface extends interface Dao. Interface defines additional methods
 * for finding objects type Tag.
 *
 * @author Siarhei Katuzhenets
 * @since 09-05-2021
 */
public interface TagDao extends Dao<Tag> {

    /**
     * This method is used to find Tag object by name from data warehouse.
     * This method will return empty optional, if data warehouse does not contain
     * object with equal name.
     *
     * @param name This is name of searched tag.
     * @return This is founded object type Tag.
     */
    Optional<Tag> findByName(String name);

    /**
     * This method is used to find most widely used tag by user.
     *
     * @param userId This is user's id.
     * @return The most widely used Tag user.
     */
    Tag findMostWidelyUsedByUser(int userId);

}