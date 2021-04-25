package com.epam.esm.pagination;

/**
 * This interface define methods of business logic for help to paginate
 * lists of objects which got from data warehouse.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface PaginationHelper {

    /**
     * This method is used for checking paginatedIdentifiable has or no previous
     * page.
     *
     * @param paginatedIdentifiable This is object for checking.
     * @return True if paginatedIdentifiable has previous page.
     */
    boolean hasPreviousPage(PaginatedIdentifiable paginatedIdentifiable);

    /**
     * This method is used for checking paginatedIdentifiable has or no next
     * page.
     *
     * @param paginatedIdentifiable This is object for checking.
     * @return True if paginatedIdentifiable has next page.
     */
    boolean hasNextPage(PaginatedIdentifiable paginatedIdentifiable);

    /**
     * This method is used for checking exist or no numPage according to
     * other parameters: countAllElements and sizePage.
     *
     * @param countAllElements This is count of all elements.
     * @param numPage          This is number of page of elements.
     * @param sizePage         This is quantity elements on page.
     * @return True if numPage exists.
     */
    boolean existNumPage(int countAllElements, int numPage, int sizePage);

    /**
     * This method is used to find start position of all elements according
     * to numPage and sizePage.
     *
     * @param numPage  This is number of page of elements.
     * @param sizePage This is quantity elements on page.
     * @return This is start position of all elements for page has num equals
     * numPage.
     */
    int findStartPosition(int numPage, int sizePage);

    /**
     * This method is used to find number of last page according to count all
     * elements and size page.
     *
     * @param countAllElements This is count of all elements.
     * @param sizePage         This is quantity elements on page.
     * @return This is num last page.
     */
    int findLastPage(int countAllElements, int sizePage);

}