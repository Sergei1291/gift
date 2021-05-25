package com.epam.esm.service.impl;

import com.epam.esm.dao.api.UserDao;
import com.epam.esm.entity.identifiable.User;
import com.epam.esm.exception.user.UserNotFoundException;
import com.epam.esm.pagination.PaginationHelper;
import com.epam.esm.service.api.AbstractService;
import com.epam.esm.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    public UserServiceImpl(UserDao userDao, PaginationHelper paginationHelper) {
        super(userDao, paginationHelper);
    }

    @Override
    public User save(User user) {
        throw new UnsupportedOperationException("save operation is not supported for user");
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException("update operation is not supported for user");
    }

    @Override
    public void remove(int id) {
        throw new UnsupportedOperationException("remove operation is not supported for user");
    }

    @Override
    protected void throwObjectNotFoundException(int id) {
        throw new UserNotFoundException("" + id);
    }

}