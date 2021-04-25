package com.epam.esm.pagination;

import com.epam.esm.entity.identifiable.Identifiable;

import java.util.List;
import java.util.Objects;

public class PaginatedIdentifiable<T extends Identifiable> {

    private List<T> identifiableList;
    private int currentPage;
    private int lastPage;

    public PaginatedIdentifiable() {
    }

    public PaginatedIdentifiable(List<T> identifiableList, int currentPage, int lastPage) {
        this.identifiableList = identifiableList;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
    }

    public List<T> getIdentifiableList() {
        return identifiableList;
    }

    public void setIdentifiableList(List<T> identifiableList) {
        this.identifiableList = identifiableList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaginatedIdentifiable<?> that = (PaginatedIdentifiable<?>) o;
        return currentPage == that.currentPage && lastPage == that.lastPage && Objects.equals(identifiableList, that.identifiableList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiableList, currentPage, lastPage);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "identifiableList=" + identifiableList +
                ", currentPage=" + currentPage +
                ", lastPage=" + lastPage +
                '}';
    }

}