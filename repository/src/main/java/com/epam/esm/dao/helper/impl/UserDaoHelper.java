package com.epam.esm.dao.helper.impl;

import com.epam.esm.dao.helper.DaoHelper;
import com.epam.esm.entity.identifiable.User;
import org.springframework.stereotype.Component;

@Component
public class UserDaoHelper implements DaoHelper<User> {

    @Override
    public void update(User updatedUser, User existingUser) {
        throw new UnsupportedOperationException();
    }

}