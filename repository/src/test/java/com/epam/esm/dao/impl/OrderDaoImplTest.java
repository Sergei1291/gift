package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.helper.impl.OrderDaoHelper;
import com.epam.esm.entity.identifiable.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {
        OrderDaoImpl.class,
        OrderDaoHelper.class})
@Import(TestConfig.class)
public class OrderDaoImplTest {

    @Autowired
    private OrderDaoImpl orderDaoImpl;

    @Test
    public void testUpdateShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> {
                    orderDaoImpl.update(null);
                });
    }

    @Test
    public void testRemoveShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> {
                    orderDaoImpl.remove(null);
                });
    }

    @Test
    public void testFindAllShouldReturnDatabaseListOrder() {
        List<Order> orderList = orderDaoImpl.findAll();
        Assertions.assertEquals(4, orderList.size());
    }

    @Test
    public void testFindAllShouldReturnDatabaseListOrderByPage() {
        List<Order> orderList = orderDaoImpl.findAll(2, 1);
        Assertions.assertEquals(3, orderList.get(0).getId());
    }

    @Test
    public void testFindByIdShouldReturnOptionalOrderWhenDatabaseContainOrderId() {
        Optional<Order> optionalOrder = orderDaoImpl.findById(2);
        Assertions.assertEquals(2, optionalOrder.get().getId());
    }

    @Test
    public void testFindByIdShouldReturnOptionalEmptyWhenDatabaseNotContainOrderId() {
        Optional<Order> optionalOrder = orderDaoImpl.findById(5);
        Assertions.assertEquals(Optional.empty(), optionalOrder);
    }

    @Test
    public void testFindCountAll() {
        int actual = orderDaoImpl.findCountAll();
        Assertions.assertEquals(4, actual);
    }

    @Test
    public void testFindUserOrderByIdShouldReturnUserWhenUserAndOrderExist() {
        int userId = 1;
        int orderId = 1;
        Optional<Order> optionalOrder = orderDaoImpl.findUserOrderById(userId, orderId);
        Assertions.assertEquals(userId, optionalOrder.get().getUser().getId());
        Assertions.assertEquals(orderId, optionalOrder.get().getId());
    }

    @Test
    public void testFindUserOrderByIdShouldReturnOptionalEmptyWhenUserNotFound() {
        int userId = 4;
        int orderId = 1;
        Optional<Order> optionalOrder = orderDaoImpl.findUserOrderById(userId, orderId);
        Assertions.assertFalse(optionalOrder.isPresent());
    }

    @Test
    public void testFindCountAllOrdersByUserIdShouldCalculateUserOrders() {
        int userId = 1;
        int actual = orderDaoImpl.findCountAllOrdersByUserId(userId);
        Assertions.assertEquals(2, actual);
    }

    @Test
    public void testFindAllOrdersByUserIdShouldReturnOrder() {
        int userId = 2;
        List<Order> orderList = orderDaoImpl.findAllOrdersByUserId(userId, 1, 1);
        Assertions.assertEquals(4, orderList.get(0).getId());
    }

}