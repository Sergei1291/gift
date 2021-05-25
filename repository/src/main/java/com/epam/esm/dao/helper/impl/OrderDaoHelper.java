package com.epam.esm.dao.helper.impl;

import com.epam.esm.dao.helper.DaoHelper;
import com.epam.esm.entity.identifiable.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoHelper implements DaoHelper<Order> {

    @Override
    public void update(Order updatedOrder, Order existingOrder) {
        throw new UnsupportedOperationException();
    }

}