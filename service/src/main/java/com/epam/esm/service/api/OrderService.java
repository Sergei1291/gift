package com.epam.esm.service.api;

import com.epam.esm.entity.identifiable.Order;
import com.epam.esm.pagination.PaginatedIdentifiable;

/**
 * This interface define additional methods for business logic on Order of
 * the gift certificate.
 *
 * @author Siarhei Katuzhenets
 * @since 09-05-2021
 */
public interface OrderService extends Service<Order> {

    /**
     * This method is used to find all orders by user.
     *
     * @param userId This is user's id for finding objects.
     * @param page   This is num page for finding objects.
     * @param size   This is max object's quantity for one page.
     * @return Object which contains all founded objects for page with additional
     * information for pagination operation.
     */
    PaginatedIdentifiable<Order> findAllOrdersByUserId(int userId, int page, int size);

    /**
     * This method is used to find Order with orderId and belonged to User with
     * id equal userId.
     *
     * @param userId  This is user's id for finding Order.
     * @param orderId This is order's id.
     * @return Founded Order whit userId and orderId.
     */
    Order findUserOrderById(int userId, int orderId);

    /**
     * This method is used to make order of certificate by certificateId for
     * user by userId.
     *
     * @param userId        This is user's id for making Order.
     * @param certificateId This is certificate's id for making Order.
     * @return Certificate's Order by userId and certificateId.
     */
    Order makeOrderByUserIdCertificateId(int userId, int certificateId);

}