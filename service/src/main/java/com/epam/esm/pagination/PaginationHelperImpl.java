package com.epam.esm.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaginationHelperImpl implements PaginationHelper {

    @Autowired
    public PaginationHelperImpl() {
    }

    @Override
    public boolean hasPreviousPage(PaginatedIdentifiable paginatedIdentifiable) {
        long currentPage = paginatedIdentifiable.getCurrentPage();
        return currentPage > 1;
    }

    @Override
    public boolean hasNextPage(PaginatedIdentifiable paginatedIdentifiable) {
        int lastPage = paginatedIdentifiable.getLastPage();
        int currentPage = paginatedIdentifiable.getCurrentPage();
        return lastPage > currentPage;
    }

    @Override
    public boolean existNumPage(int countAllElements, int numPage, int sizePage) {
        boolean result;
        if (numPage == 1) {
            result = true;
        } else if (countAllElements >= numPage * sizePage) {
            result = true;
        } else {
            result = countAllElements > (numPage - 1) * sizePage;
        }
        return result;
    }

    @Override
    public int findStartPosition(int numPage, int sizePage) {
        return (numPage - 1) * sizePage;
    }

    @Override
    public int findLastPage(int countAllElements, int sizePage) {
        int lastPage;
        if (countAllElements % sizePage == 0) {
            lastPage = countAllElements / sizePage;
        } else {
            lastPage = countAllElements / sizePage + 1;
        }
        if (lastPage == 0) {
            lastPage = 1;
        }
        return lastPage;
    }

}