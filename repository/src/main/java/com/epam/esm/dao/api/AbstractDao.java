package com.epam.esm.dao.api;

import com.epam.esm.dao.helper.DaoHelper;
import com.epam.esm.entity.identifiable.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {

    private final static String FIND_ALL_QUERY = "FROM %s order by id asc";
    private final static String FIND_BY_ID_QUERY = "FROM %s t where t.id = :id";
    private final static String ID_PARAMETER = "id";
    private final static String FIND_COUNT_ALL_QUERY = "SELECT COUNT(e) FROM %s e";

    @PersistenceContext
    protected EntityManager entityManager;
    private final String entityName;
    private final DaoHelper<T> daoHelper;

    public AbstractDao(String entityName, DaoHelper<T> daoHelper) {
        this.entityName = entityName;
        this.daoHelper = daoHelper;
    }

    @Override
    @Transactional
    public T save(T identifiable) {
        entityManager.persist(identifiable);
        return identifiable;
    }

    @Override
    @Transactional
    public void update(T identifiable) {
        int idIdentifiable = identifiable.getId();
        T existingIdentifiable = (T) entityManager.find(identifiable.getClass(), idIdentifiable);
        daoHelper.update(identifiable, existingIdentifiable);
    }

    @Override
    @Transactional
    public void remove(T identifiable) {
        int idIdentifiable = identifiable.getId();
        T entity = (T) entityManager.find(identifiable.getClass(), idIdentifiable);
        entityManager.remove(entity);
    }

    @Override
    public List<T> findAll() {
        String queryString = String.format(FIND_ALL_QUERY, entityName);
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

    @Override
    public List<T> findAll(int startPosition, int quantityElements) {
        String queryString = String.format(FIND_ALL_QUERY, entityName);
        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(startPosition);
        query.setMaxResults(quantityElements);
        return query.getResultList();
    }

    @Override
    public Optional<T> findById(int id) {
        String queryString = String.format(FIND_BY_ID_QUERY, entityName);
        Query query = entityManager.createQuery(queryString);
        query.setParameter(ID_PARAMETER, id);
        List<T> foundedObjectList = query.getResultList();
        if (foundedObjectList.isEmpty()) {
            return Optional.empty();
        }
        T foundedObject = foundedObjectList.get(0);
        return Optional.of(foundedObject);
    }

    @Override
    public int findCountAll() {
        String query = String.format(FIND_COUNT_ALL_QUERY, entityName);
        long countAll = (long) entityManager.createQuery(query)
                .getSingleResult();
        return (int) countAll;
    }

}