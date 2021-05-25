package com.epam.esm.service.api;

import com.epam.esm.entity.identifiable.Tag;

/**
 * This interface define additional methods for business logic on Tag.
 *
 * @author Siarhei Katuzhenets
 * @since 09-05-2021
 */
public interface TagService extends Service<Tag> {

    /**
     * This method is used to find most widely used tag user with max sum of
     * orders.
     *
     * @return The most widely used Tag user with max sum of orders.
     */
    Tag findMostWidelyUsedTagUserMaxOrderSum();

}