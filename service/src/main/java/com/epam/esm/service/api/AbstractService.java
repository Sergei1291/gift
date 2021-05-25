package com.epam.esm.service.api;

import com.epam.esm.dao.api.Dao;
import com.epam.esm.entity.identifiable.Identifiable;
import com.epam.esm.exception.NumPageNotExistException;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.pagination.PaginationHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T extends Identifiable> implements Service<T> {

    protected final Dao<T> dao;
    protected final PaginationHelper paginationHelper;

    public AbstractService(Dao<T> dao, PaginationHelper paginationHelper) {
        this.dao = dao;
        this.paginationHelper = paginationHelper;
    }

    protected abstract void throwObjectNotFoundException(int id);

    @Override
    public T save(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T update(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public void remove(int id) {
        Optional<T> optionalT = dao.findById(id);
        if (!optionalT.isPresent()) {
            throwObjectNotFoundException(id);
        }
        T t = optionalT.get();
        dao.remove(t);
    }

    @Override
    @Transactional
    public PaginatedIdentifiable<T> findALl(int page, int size) {
        int countAllElements = dao.findCountAll();
        if (!paginationHelper.existNumPage(countAllElements, page, size)) {
            throw new NumPageNotExistException("" + page);
        }
        int startPosition = paginationHelper.findStartPosition(page, size);
        List<T> list = dao.findAll(startPosition, size);
        int lastPage = paginationHelper.findLastPage(countAllElements, size);
        return new PaginatedIdentifiable<>(list, page, lastPage);
    }

    @Override
    public T findById(int id) {
        Optional<T> optionalT = dao.findById(id);
        if (!optionalT.isPresent()) {
            throwObjectNotFoundException(id);
        }
        return optionalT.get();
    }

}