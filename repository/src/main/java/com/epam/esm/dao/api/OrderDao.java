package com.epam.esm.dao.api;

import com.epam.esm.entity.identifiable.Order;

import java.util.List;
import java.util.Optional;

/**
 * The interface extends interface Dao. Interface defines additional methods
 * for finding objects type Order.
 *
 * @author Siarhei Katuzhenets
 * @since 09-05-2021
 */
public interface OrderDao extends Dao<Order> {

    /**
     * This method is used to find user's order by order's id.
     *
     * @param userId  This is user's id.
     * @param orderId This is order's id.
     * @return Optional order of user.
     */
    Optional<Order> findUserOrderById(int userId, int orderId);

    /**
     * This method is used to find count of all orders by user.
     *
     * @param userId This is user's id.
     * @return Count of all founded objects.
     */
    int findCountAllOrdersByUserId(int userId);

    /**
     * This method is used to find all orders by user.
     *
     * @param userId           This is user's id.
     * @param startPosition    This is start position from list of all founded
     *                         elements.
     * @param quantityElements This is quantity first founded elements.
     * @return All founded orders of user.
     */
    List<Order> findAllOrdersByUserId(int userId, int startPosition, int quantityElements);

}