package com.epam.esm.dao.impl;

import com.epam.esm.dao.api.AbstractDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.dao.helper.impl.TagDaoHelper;
import com.epam.esm.entity.identifiable.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {

    private final static String ENTITY_NAME = "Tag";
    private final static String FIND_BY_NAME = "FROM Tag tag where tag.name = :name";
    private final static String NAME_PARAMETER = "name";
    private final static String FIND_MOST_WIDELY_USED_BY_USER = "select * from tag " +
            "join gift_certificate_tag on gift_certificate_tag.tag = tag.id " +
            "inner join order_certificate on order_certificate.certificate_id=gift_certificate_tag.certificate " +
            "where order_certificate.user_id = :userId group by tag.id order by count(tag.id) desc limit 1";
    private final static String USER_ID_PARAMETER = "userId";

    @Autowired
    public TagDaoImpl(TagDaoHelper tagDaoHelper) {
        super(ENTITY_NAME, tagDaoHelper);
    }

    @Override
    public void update(Tag tag) {
        throw new UnsupportedOperationException("update operation not supported for tag");
    }

    @Override
    public Optional<Tag> findByName(String name) {
        TypedQuery<Tag> query = entityManager.createQuery(FIND_BY_NAME, Tag.class);
        query.setParameter(NAME_PARAMETER, name);
        List<Tag> foundedTagList = query.getResultList();
        if (foundedTagList.isEmpty()) {
            return Optional.empty();
        }
        Tag tag = foundedTagList.get(0);
        return Optional.of(tag);
    }

    @Override
    public Tag findMostWidelyUsedByUser(int userId) {
        Query query = entityManager.createNativeQuery(FIND_MOST_WIDELY_USED_BY_USER, Tag.class);
        query.setParameter(USER_ID_PARAMETER, userId);
        return (Tag) query.getSingleResult();
    }

}