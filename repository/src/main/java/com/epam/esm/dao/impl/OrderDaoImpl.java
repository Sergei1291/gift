package com.epam.esm.dao.impl;

import com.epam.esm.dao.api.AbstractDao;
import com.epam.esm.dao.api.OrderDao;
import com.epam.esm.dao.helper.impl.OrderDaoHelper;
import com.epam.esm.entity.identifiable.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    private final static String ENTITY_NAME = "Order";
    private final static String FIND_USER_ORDER_BY_ID =
            "select * from order_certificate o where o.id = :orderId and o.user_id = :userId";
    private final static String FIND_COUNT_ALL_ORDERS_BY_USER_ID =
            "select count(o.id) from order_certificate o where o.user_id = :userId";
    private final static String FIND_ALL_ORDERS_BY_USER_ID =
            "select * from order_certificate o where o.user_id = :userId";
    private final static String USER_ID_PARAMETER = "userId";
    private final static String ORDER_ID_PARAMETER = "orderId";

    @Autowired
    public OrderDaoImpl(OrderDaoHelper orderDaoHelper) {
        super(ENTITY_NAME, orderDaoHelper);
    }

    @Override
    public void update(Order order) {
        throw new UnsupportedOperationException("update operation not supported for orders");
    }

    @Override
    public void remove(Order order) {
        throw new UnsupportedOperationException("remove operation not supported for orders");
    }

    @Override
    public Optional<Order> findUserOrderById(int userId, int orderId) {
        Query query = entityManager.createNativeQuery(FIND_USER_ORDER_BY_ID, Order.class);
        query.setParameter(ORDER_ID_PARAMETER, orderId);
        query.setParameter(USER_ID_PARAMETER, userId);
        List<Order> orderList = query.getResultList();
        if (orderList.isEmpty()) {
            return Optional.empty();
        }
        Order order = orderList.get(0);
        return Optional.of(order);
    }

    @Override
    public int findCountAllOrdersByUserId(int userId) {
        Query query = entityManager.createNativeQuery(FIND_COUNT_ALL_ORDERS_BY_USER_ID);
        query.setParameter(USER_ID_PARAMETER, userId);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
    }

    @Override
    public List<Order> findAllOrdersByUserId(int userId, int startPosition, int quantityElements) {
        Query query = entityManager.createNativeQuery(FIND_ALL_ORDERS_BY_USER_ID, Order.class);
        query.setParameter(USER_ID_PARAMETER, userId);
        query.setFirstResult(startPosition);
        query.setMaxResults(quantityElements);
        return query.getResultList();
    }

}