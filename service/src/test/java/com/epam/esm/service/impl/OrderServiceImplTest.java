package com.epam.esm.service.impl;

import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.api.OrderDao;
import com.epam.esm.dao.api.UserDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Order;
import com.epam.esm.entity.identifiable.User;
import com.epam.esm.exception.NumPageNotExistException;
import com.epam.esm.exception.certificate.CertificateNotFoundException;
import com.epam.esm.exception.order.OrderByUserNotFoundException;
import com.epam.esm.exception.order.OrderNotFoundException;
import com.epam.esm.exception.user.UserNotFoundException;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.pagination.PaginationHelper;
import com.epam.esm.pagination.PaginationHelperImpl;
import com.epam.esm.service.api.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    private final static User USER_ONE = new User(1, "login1", null);
    private final static User USER_TWO = new User(2, "login2", null);
    private final static GiftCertificate GIFT_CERTIFICATE_ONE =
            new GiftCertificate(1, "name1", "description1", 1, 1, "createDate1", null, null);
    private final static GiftCertificate GIFT_CERTIFICATE_TWO =
            new GiftCertificate(2, "name2", "description2", 2, 2, "createDate2", null, null);
    private final static Order ORDER_ONE =
            new Order(1, 1, "time1", GIFT_CERTIFICATE_ONE, USER_ONE);
    private final static Order ORDER_TWO =
            new Order(2, 2, "time2", GIFT_CERTIFICATE_TWO, USER_TWO);
    private final static List<Order> ORDER_LIST = Arrays.asList(ORDER_ONE, ORDER_TWO);

    private final OrderDao orderDao = Mockito.mock(OrderDaoImpl.class);
    private final GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
    private final UserDao userDao = Mockito.mock(UserDaoImpl.class);
    private final PaginationHelper paginationHelper = Mockito.mock(PaginationHelperImpl.class);
    private final OrderService orderService =
            new OrderServiceImpl(orderDao, giftCertificateDao, userDao, paginationHelper);

    @Test
    public void testFindAllOrdersByUserIdShouldReturnPaginatedOrders() {
        int numPage = 1;
        int sizePage = 10;
        int userId = 1;
        when(orderDao.findCountAllOrdersByUserId(userId)).thenReturn(2);
        when(paginationHelper.existNumPage(2, numPage, sizePage)).thenReturn(true);
        when(paginationHelper.findStartPosition(numPage, sizePage)).thenReturn(0);
        when(orderDao.findAllOrdersByUserId(1, 0, 10)).thenReturn(ORDER_LIST);
        when(paginationHelper.findLastPage(2, sizePage)).thenReturn(1);
        PaginatedIdentifiable actual = orderService.findAllOrdersByUserId(userId, numPage, sizePage);
        Assertions.assertEquals(new PaginatedIdentifiable(ORDER_LIST, 1, 1), actual);
    }

    @Test
    public void testFindAllOrdersByUserIdShouldThrowNumPageNotExistException() {
        int numPage = 1;
        int sizePage = 10;
        int userId = 1;
        when(orderDao.findCountAllOrdersByUserId(userId)).thenReturn(2);
        when(paginationHelper.existNumPage(2, numPage, sizePage)).thenReturn(false);
        Assertions.assertThrows(NumPageNotExistException.class,
                () -> orderService.findAllOrdersByUserId(userId, numPage, sizePage));
    }

    @Test
    public void testFindUserOrderByIdShouldReturnOrderWhenOrderFounded() {
        when(orderDao.findUserOrderById(1, 1)).thenReturn(Optional.of(ORDER_ONE));
        Order actual = orderService.findUserOrderById(1, 1);
        Assertions.assertEquals(ORDER_ONE, actual);
    }

    @Test
    public void testFindUserOrderByIdShouldThrowExceptionWhenOrderNotFound() {
        when(orderDao.findUserOrderById(1, 1)).thenReturn(Optional.empty());
        Assertions.assertThrows(OrderByUserNotFoundException.class,
                () -> orderService.findUserOrderById(1, 1));
    }

    @Test
    public void testMakeOrderByUserIdCertificateIdShouldThrowExceptionWhenCertificateNotFound() {
        int idCertificate = 1;
        when(giftCertificateDao.findById(idCertificate)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class,
                () -> orderService.makeOrderByUserIdCertificateId(0, idCertificate));
    }

    @Test
    public void testMakeOrderByUserIdCertificateIdShouldThrowExceptionWhenUserNotFound() {
        int userId = 1;
        int idCertificate = 1;
        when(giftCertificateDao.findById(idCertificate)).thenReturn(Optional.of(new GiftCertificate()));
        when(userDao.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,
                () -> orderService.makeOrderByUserIdCertificateId(userId, idCertificate));
    }

    @Test
    public void testSaveShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> orderService.save(null));
    }

    @Test
    public void testUpdateShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> orderService.update(null));
    }

    @Test
    public void testRemoveShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> orderService.remove(0));
    }

    @Test
    public void testFindAllShouldReturnPaginatedUserList() {
        int numPage = 1;
        int sizePage = 10;
        when(orderDao.findCountAll()).thenReturn(2);
        when(paginationHelper.existNumPage(2, numPage, sizePage)).thenReturn(true);
        when(paginationHelper.findStartPosition(numPage, sizePage)).thenReturn(0);
        when(orderDao.findAll(0, 10)).thenReturn(ORDER_LIST);
        when(paginationHelper.findLastPage(2, sizePage)).thenReturn(1);
        PaginatedIdentifiable actual = orderService.findALl(1, 10);
        Assertions.assertEquals(new PaginatedIdentifiable(ORDER_LIST, 1, 1), actual);
    }

    @Test
    public void testFindByIdShouldReturnUser() {
        when(orderDao.findById(0)).thenReturn(Optional.of(ORDER_LIST.get(0)));
        Order actual = orderService.findById(0);
        Assertions.assertEquals(new Order(1, 1, "time1", GIFT_CERTIFICATE_ONE, USER_ONE),
                actual);
    }

    @Test
    public void testFindByIdShouldThrowUserNotFoundExceptionWhenDatabaseNotContainUserId() {
        when(userDao.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(OrderNotFoundException.class,
                () -> orderService.findById(0));
    }

}