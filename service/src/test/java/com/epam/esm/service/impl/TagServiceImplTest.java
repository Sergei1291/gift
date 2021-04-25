package com.epam.esm.service.impl;

import com.epam.esm.dao.api.TagDao;
import com.epam.esm.dao.api.UserDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.entity.identifiable.User;
import com.epam.esm.exception.tag.TagNameAlreadyExistsException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.pagination.PaginationHelper;
import com.epam.esm.pagination.PaginationHelperImpl;
import com.epam.esm.service.api.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TagServiceImplTest {

    private final static Tag TAG_ZERO = new Tag(0, "oneTag", null);
    private final static Tag TAG_ONE = new Tag(1, "oneTag", null);
    private final static Tag TAG_TWO = new Tag(2, "twoTag", null);
    private final static List<Tag> TAG_LIST = Arrays.asList(TAG_ONE, TAG_TWO);

    private TagDao tagDao = Mockito.mock(TagDaoImpl.class);
    private UserDao userDao = Mockito.mock(UserDaoImpl.class);
    private PaginationHelper paginationHelper = Mockito.mock(PaginationHelperImpl.class);
    private TagService tagService =
            new TagServiceImpl(tagDao, userDao, paginationHelper);

    @Test
    public void testSaveShouldSaveTagToDatabaseAndReturnSavedTag() {
        when(tagDao.findByName("oneTag")).thenReturn(Optional.empty());
        when(tagDao.save(TAG_ZERO)).thenReturn(TAG_ONE);
        Tag actual = tagService.save(new Tag(0, "oneTag", null));
        Assertions.assertEquals(TAG_ONE, actual);
    }

    @Test
    public void testSaveShouldThrowTagNameAlreadyExistsExceptionWhenDatabaseContainTagName() {
        when(tagDao.findByName("oneTag")).thenReturn(Optional.of(new Tag()));
        Assertions.assertThrows(TagNameAlreadyExistsException.class,
                () -> tagService.save(TAG_ZERO));
        verify(tagDao, times(0)).save(any());
    }

    @Test
    public void testUpdateShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> tagService.update(null));
    }

    @Test
    public void testRemoveShouldRemoveTagWhenTagFoundedInDataBase() {
        when(tagDao.findById(1)).thenReturn(Optional.of(new Tag()));
        tagService.remove(1);
        verify(tagDao, times(1)).remove(any());
    }

    @Test
    public void testRemoveShouldThrowTagNotFoundExceptionWhenDatabaseNotContainTagId() {
        when(tagDao.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class,
                () -> tagService.remove(1));
        verify(tagDao, times(0)).remove(any());
    }

    @Test
    public void testFindAllShouldReturnPaginatedTagList() {
        int numPage = 1;
        int sizePage = 10;
        when(tagDao.findCountAll()).thenReturn(2);
        when(paginationHelper.existNumPage(2, numPage, sizePage)).thenReturn(true);
        when(paginationHelper.findStartPosition(numPage, sizePage)).thenReturn(0);
        when(tagDao.findAll(0, 10)).thenReturn(TAG_LIST);
        when(paginationHelper.findLastPage(2, sizePage)).thenReturn(1);
        PaginatedIdentifiable actual = tagService.findALl(1, 10);
        Assertions.assertEquals(new PaginatedIdentifiable(TAG_LIST, 1, 1), actual);
    }

    @Test
    public void testFindByIdShouldReturnTag() {
        when(tagDao.findById(0)).thenReturn(Optional.of(TAG_LIST.get(0)));
        Tag actual = tagService.findById(0);
        Assertions.assertEquals(new Tag(1, "oneTag", null), actual);
    }

    @Test
    public void testFindByIdShouldThrowTagNotFoundExceptionWhenDatabaseNotContainTagId() {
        when(tagDao.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class,
                () -> tagService.findById(0));
    }

    @Test
    public void testFindMostWidelyUsedTagUserMaxOrderSumShouldReturnTag() {
        when(userDao.findUserMaxOrdersSum()).thenReturn(new User());
        when(tagDao.findMostWidelyUsedByUser(0)).thenReturn(TAG_ZERO);
        Tag actual = tagService.findMostWidelyUsedTagUserMaxOrderSum();
        Assertions.assertEquals(TAG_ZERO, actual);
    }

}