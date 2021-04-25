package com.epam.esm.dao.impl;

import com.epam.esm.dao.api.AbstractDao;
import com.epam.esm.dao.api.UserDao;
import com.epam.esm.dao.helper.impl.UserDaoHelper;
import com.epam.esm.entity.identifiable.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private final static String ENTITY_NAME = "User";
    private final static String FIND_USER_MAX_ORDERS_SUM = "select * from user " +
            "inner join (select user_id from order_certificate " +
            "group by user_id order by sum(cost) desc limit 1) user_max_order_sum " +
            "on user_max_order_sum.user_id=user.id";

    @Autowired
    public UserDaoImpl(UserDaoHelper userDaoHelper) {
        super(ENTITY_NAME, userDaoHelper);
    }

    @Override
    public User save(User user) {
        throw new UnsupportedOperationException("save operation not supported for user");
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException("update operation not supported for user");
    }

    @Override
    public void remove(User user) {
        throw new UnsupportedOperationException("remove operation not supported for user");
    }

    @Override
    public User findUserMaxOrdersSum() {
        Query query = entityManager.createNativeQuery(FIND_USER_MAX_ORDERS_SUM, User.class);
        return (User) query.getSingleResult();
    }

}