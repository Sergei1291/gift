package com.epam.esm.pagination;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PaginationHelperImpl.class)
public class PaginationHelperImplTest {

    @Autowired
    private PaginationHelper paginationHelper;

    @Test
    public void testHasPreviousPageShouldFalseWhenCurrentPageEqualOne() {
        int currentPage = 1;
        PaginatedIdentifiable paginatedIdentifiable = new PaginatedIdentifiable(null, currentPage, 0);
        boolean actual = paginationHelper.hasPreviousPage(paginatedIdentifiable);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testHasPreviousPageShouldTrueWhenCurrentPageMoreOne() {
        int currentPage = 2;
        PaginatedIdentifiable paginatedIdentifiable = new PaginatedIdentifiable(null, currentPage, 0);
        boolean actual = paginationHelper.hasPreviousPage(paginatedIdentifiable);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testHasNextPageShouldFalseWhenCurrentPageEqualLastPage() {
        int currentPage = 2;
        int lastPage = 2;
        PaginatedIdentifiable paginatedIdentifiable = new PaginatedIdentifiable(null, currentPage, lastPage);
        boolean actual = paginationHelper.hasNextPage(paginatedIdentifiable);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testHasNextPageShouldTrueWhenCurrentPageLessLastPage() {
        int currentPage = 1;
        int lastPage = 2;
        PaginatedIdentifiable paginatedIdentifiable = new PaginatedIdentifiable(null, currentPage, lastPage);
        boolean actual = paginationHelper.hasNextPage(paginatedIdentifiable);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testExistNumPageShouldTrueWhenNumEqualOne() {
        int countAllElements = 0;
        int numPage = 1;
        int sizePage = 0;
        boolean actual = paginationHelper.existNumPage(countAllElements, numPage, sizePage);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testExistNumPageShouldTrueWhenCountMoreNumPageSize() {
        int countAllElements = 10;
        int numPage = 2;
        int sizePage = 3;
        boolean actual = paginationHelper.existNumPage(countAllElements, numPage, sizePage);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testExistNumPageShouldTrueWhenCountLessOnOneNumPageSize() {
        int countAllElements = 10;
        int numPage = 4;
        int sizePage = 3;
        boolean actual = paginationHelper.existNumPage(countAllElements, numPage, sizePage);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testExistNumPageShouldFalseWhenCountLessMoreOneNumPageSize() {
        int countAllElements = 10;
        int numPage = 5;
        int sizePage = 3;
        boolean actual = paginationHelper.existNumPage(countAllElements, numPage, sizePage);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testFindStartPosition() {
        int numPage = 3;
        int size = 20;
        int actual = paginationHelper.findStartPosition(numPage, size);
        Assertions.assertEquals(40, actual);
    }

    @Test
    public void testFindLastPageShouldTwoWhenNumbersDividedWithoutRemainder() {
        int countAllElements = 40;
        int sizePage = 20;
        int actual = paginationHelper.findLastPage(countAllElements, sizePage);
        Assertions.assertEquals(2, actual);
    }

    @Test
    public void testFindLastPageShouldThreeWhenNumbersDividedWithRemainder() {
        int countAllElements = 41;
        int sizePage = 20;
        int actual = paginationHelper.findLastPage(countAllElements, sizePage);
        Assertions.assertEquals(3, actual);
    }

    @Test
    public void testFindLastPageShouldOneWhenCountElementsZero() {
        int countAllElements = 0;
        int sizePage = 20;
        int actual = paginationHelper.findLastPage(countAllElements, sizePage);
        Assertions.assertEquals(1, actual);
    }

}