package com.epam.esm.service.impl;

import com.epam.esm.dao.api.UserDao;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.identifiable.User;
import com.epam.esm.exception.user.UserNotFoundException;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.pagination.PaginationHelper;
import com.epam.esm.pagination.PaginationHelperImpl;
import com.epam.esm.service.api.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private final static User USER_ONE = new User(1, "loginOne", null);
    private final static User USER_TWO = new User(2, "loginTwo", null);
    private final static List<User> USER_LIST = Arrays.asList(USER_ONE, USER_TWO);

    private final UserDao userDao = Mockito.mock(UserDaoImpl.class);
    private final PaginationHelper paginationHelper = Mockito.mock(PaginationHelperImpl.class);
    private final UserService userService =
            new UserServiceImpl(userDao, paginationHelper);

    @Test
    public void testSaveShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> userService.save(null));
    }

    @Test
    public void testUpdateShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> userService.update(null));
    }

    @Test
    public void testRemoveShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> userService.remove(0));
    }

    @Test
    public void testFindAllShouldReturnPaginatedUserList() {
        int numPage = 1;
        int sizePage = 10;
        when(userDao.findCountAll()).thenReturn(2);
        when(paginationHelper.existNumPage(2, numPage, sizePage)).thenReturn(true);
        when(paginationHelper.findStartPosition(numPage, sizePage)).thenReturn(0);
        when(userDao.findAll(0, 10)).thenReturn(USER_LIST);
        when(paginationHelper.findLastPage(2, sizePage)).thenReturn(1);
        PaginatedIdentifiable actual = userService.findALl(1, 10);
        Assertions.assertEquals(new PaginatedIdentifiable(USER_LIST, 1, 1), actual);
    }

    @Test
    public void testFindByIdShouldReturnUser() {
        when(userDao.findById(0)).thenReturn(Optional.of(USER_LIST.get(0)));
        User actual = userService.findById(0);
        Assertions.assertEquals(new User(1, "loginOne", null), actual);
    }

    @Test
    public void testFindByIdShouldThrowUserNotFoundExceptionWhenDatabaseNotContainUserId() {
        when(userDao.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.findById(0));
    }

}