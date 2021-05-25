package com.epam.esm.dao.impl;

import com.epam.esm.dao.api.AbstractDao;
import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.helper.impl.GiftCertificateDaoHelper;
import com.epam.esm.entity.identifiable.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate>
        implements GiftCertificateDao {

    private final static String ENTITY_NAME = "GiftCertificate";
    private final static String FIND_BY_NAME =
            "FROM GiftCertificate certificate where certificate.name = :name";
    private final static String NAME_PARAMETER = "name";
    private final static String TAG_NAME_PARAMETER = "tagName";
    private final static String SEARCH_STRING_PARAMETER = "searchString";

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateDaoHelper giftCertificateDaoHelper) {
        super(ENTITY_NAME, giftCertificateDaoHelper);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        TypedQuery<GiftCertificate> query =
                entityManager.createQuery(FIND_BY_NAME, GiftCertificate.class);
        query.setParameter(NAME_PARAMETER, name);
        List<GiftCertificate> foundedObjectList = query.getResultList();
        if (foundedObjectList.isEmpty()) {
            return Optional.empty();
        }
        GiftCertificate foundedObject = foundedObjectList.get(0);
        return Optional.of(foundedObject);
    }

    @Override
    public List<GiftCertificate> search(List<String> tagNames,
                                        String searchString,
                                        String searchParamName,
                                        String sortParamName,
                                        boolean orderDesc,
                                        int startPosition,
                                        int quantityElements) {
        String querySearch = createQuerySearch(tagNames, searchString,
                searchParamName, sortParamName, orderDesc);
        Query query = entityManager.createNativeQuery(querySearch, GiftCertificate.class);
        if (tagNames != null) {
            for (int i = 0; i < tagNames.size(); i++) {
                query.setParameter(TAG_NAME_PARAMETER + i, tagNames.get(i));
            }
        }
        if ((searchParamName != null) && (searchString != null)) {
            query.setParameter(SEARCH_STRING_PARAMETER, searchString);
        }
        query.setFirstResult(startPosition);
        query.setMaxResults(quantityElements);
        return query.getResultList();
    }

    private String createQuerySearch(List<String> tagNames,
                                     String searchString,
                                     String searchParamName,
                                     String sortParamName,
                                     boolean orderDesc) {
        StringBuilder builder = new StringBuilder("select * from gift_certificate c where c.id > 0 ");
        if (tagNames != null) {
            for (int i = 0; i < tagNames.size(); i++) {
                builder.append("and c.id in(select gift_certificate.id from gift_certificate " +
                        "inner join gift_certificate_tag on gift_certificate_tag.certificate=gift_certificate.id " +
                        "inner join tag on tag.id=gift_certificate_tag.tag where tag.name = :tagName")
                        .append(i)
                        .append(") ");
            }
        }
        if ((searchParamName != null) && (searchString != null)) {
            builder.append("and c.id in(select gift_certificate.id from gift_certificate where gift_certificate.")
                    .append(searchParamName)
                    .append(" like concat('%', :searchString,'%')) ");
        }
        builder.append("order by ")
                .append(sortParamName);
        if (orderDesc) {
            builder.append(" desc");
        } else {
            builder.append(" asc");
        }
        return new String(builder);
    }

    @Override
    public int findCountSearch(List<String> tagNames,
                               String searchString,
                               String searchParamName) {
        String queryString = createQueryFindCountSearch(tagNames, searchString, searchParamName);
        Query query = entityManager.createNativeQuery(queryString);
        if (tagNames != null) {
            for (int i = 0; i < tagNames.size(); i++) {
                query.setParameter(TAG_NAME_PARAMETER + i, tagNames.get(i));
            }
        }
        if ((searchParamName != null) && (searchString != null)) {
            query.setParameter(SEARCH_STRING_PARAMETER, searchString);
        }
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
    }

    private String createQueryFindCountSearch(List<String> tagNames,
                                              String searchString,
                                              String searchParamName) {
        StringBuilder builder = new StringBuilder("select count(c.id) from gift_certificate c where c.id > 0 ");
        if (tagNames != null) {
            for (int i = 0; i < tagNames.size(); i++) {
                builder.append("and c.id in(select gift_certificate.id from gift_certificate " +
                        "inner join gift_certificate_tag on gift_certificate_tag.certificate=gift_certificate.id " +
                        "inner join tag on tag.id=gift_certificate_tag.tag where tag.name = :tagName")
                        .append(i)
                        .append(") ");
            }
        }
        if ((searchParamName != null) && (searchString != null)) {
            builder.append("and c.id in(select gift_certificate.id from gift_certificate where gift_certificate.")
                    .append(searchParamName)
                    .append(" like concat('%', :searchString,'%')) ");
        }
        return new String(builder);
    }

}