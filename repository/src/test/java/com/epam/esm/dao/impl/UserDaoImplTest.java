package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.helper.impl.UserDaoHelper;
import com.epam.esm.entity.identifiable.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {
        UserDaoImpl.class,
        UserDaoHelper.class})
@Import(TestConfig.class)
public class UserDaoImplTest {

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Test
    public void testSaveShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> {
                    userDaoImpl.save(null);
                });
    }

    @Test
    public void testUpdateShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> {
                    userDaoImpl.update(null);
                });
    }

    @Test
    public void testRemoveShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> {
                    userDaoImpl.remove(null);
                });
    }

    @Test
    public void testFindAllShouldReturnDatabaseListUser() {
        List<User> userList = userDaoImpl.findAll();
        Assertions.assertEquals(2, userList.size());
    }

    @Test
    public void testFindAllShouldReturnDatabaseListTagByPage() {
        List<User> userList = userDaoImpl.findAll(1, 1);
        Assertions.assertEquals(2, userList.get(0).getId());
    }

    @Test
    public void testFindByIdShouldReturnOptionalUserWhenDatabaseContainUserId() {
        Optional<User> optionalUser = userDaoImpl.findById(2);
        Assertions.assertTrue(optionalUser.isPresent());
    }

    @Test
    public void testFindByIdShouldReturnOptionalEmptyWhenDatabaseNotContainUserId() {
        Optional<User> optionalUser = userDaoImpl.findById(4);
        Assertions.assertEquals(Optional.empty(), optionalUser);
    }

    @Test
    public void testFindCountAll() {
        int actual = userDaoImpl.findCountAll();
        Assertions.assertEquals(2, actual);
    }

    @Test
    public void testFindUserMaxOrdersSumShouldReturnUser() {
        User actual = userDaoImpl.findUserMaxOrdersSum();
        Assertions.assertEquals(2, actual.getId());
    }

}