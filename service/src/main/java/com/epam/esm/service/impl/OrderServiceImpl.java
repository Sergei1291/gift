package com.epam.esm.service.impl;

import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.api.OrderDao;
import com.epam.esm.dao.api.UserDao;
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
import com.epam.esm.service.api.AbstractService;
import com.epam.esm.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {

    private final GiftCertificateDao giftCertificateDao;
    private final UserDao userDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            GiftCertificateDao giftCertificateDao,
                            UserDao userDao,
                            PaginationHelper paginationHelper) {
        super(orderDao, paginationHelper);
        this.giftCertificateDao = giftCertificateDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public PaginatedIdentifiable<Order> findAllOrdersByUserId(int userId, int page, int size) {
        OrderDao orderDao = (OrderDao) dao;
        int countAllElements = orderDao.findCountAllOrdersByUserId(userId);
        if (!paginationHelper.existNumPage(countAllElements, page, size)) {
            throw new NumPageNotExistException("" + page);
        }
        int startPosition = paginationHelper.findStartPosition(page, size);
        List<Order> orderList = orderDao.findAllOrdersByUserId(userId, startPosition, size);
        int lastPage = paginationHelper.findLastPage(countAllElements, size);
        return new PaginatedIdentifiable<>(orderList, page, lastPage);
    }

    @Override
    public Order findUserOrderById(int userId, int orderId) {
        OrderDao orderDao = (OrderDao) dao;
        Optional<Order> optionalOrder = orderDao.findUserOrderById(userId, orderId);
        return optionalOrder.orElseThrow(
                () -> new OrderByUserNotFoundException(userId, orderId));
    }

    @Override
    @Transactional
    public Order makeOrderByUserIdCertificateId(int userId, int certificateId) {
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateDao.findById(certificateId);
        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                () -> new CertificateNotFoundException("" + certificateId));
        Optional<User> optionalUser = userDao.findById(userId);
        User user = optionalUser.orElseThrow(
                () -> new UserNotFoundException("" + userId));
        Order createdOrder = createOrder(user, giftCertificate);
        OrderDao orderDao = (OrderDao) dao;
        return orderDao.save(createdOrder);
    }

    private Order createOrder(User user, GiftCertificate giftCertificate) {
        Order order = new Order();
        int cost = giftCertificate.getPrice();
        order.setCost(cost);
        order.setGiftCertificate(giftCertificate);
        order.setUser(user);
        return order;
    }

    @Override
    public Order save(Order order) {
        throw new UnsupportedOperationException("save operation is not supported for order");
    }

    @Override
    public Order update(Order order) {
        throw new UnsupportedOperationException("update operation is not supported for order");
    }

    @Override
    public void remove(int id) {
        throw new UnsupportedOperationException("remove operation is not supported for order");
    }

    @Override
    protected void throwObjectNotFoundException(int id) {
        throw new OrderNotFoundException("" + id);
    }

}