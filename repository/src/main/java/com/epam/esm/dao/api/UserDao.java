package com.epam.esm.dao.api;

import com.epam.esm.entity.identifiable.User;

/**
 * The interface extends interface Dao. Interface defines additional methods
 * for finding objects type User.
 *
 * @author Siarhei Katuzhenets
 * @since 09-05-2021
 */
public interface UserDao extends Dao<User> {

    /**
     * This method is used to find user with max sum of orders.
     *
     * @return This is founded user.
     */
    User findUserMaxOrdersSum();

}