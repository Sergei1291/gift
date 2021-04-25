package com.epam.esm.service.impl;

import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.exception.NumPageNotExistException;
import com.epam.esm.exception.certificate.CertificateNameAlreadyExistsException;
import com.epam.esm.exception.certificate.CertificateNotFoundException;
import com.epam.esm.exception.certificate.UnsupportedSearchParamNameCertificateException;
import com.epam.esm.exception.certificate.UnsupportedSortedParamNameCertificateException;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.pagination.PaginationHelper;
import com.epam.esm.service.api.AbstractService;
import com.epam.esm.service.api.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate> implements GiftCertificateService {

    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String DESCRIPTION = "description";
    private final static String CREATE_DATE = "create_date";

    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagDao tagDao,
                                      PaginationHelper paginationHelper) {
        super(giftCertificateDao, paginationHelper);
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public PaginatedIdentifiable<GiftCertificate> search(List<String> tagNames,
                                                         String searchString,
                                                         String searchParamName,
                                                         String sortParamName,
                                                         boolean orderDesc,
                                                         int page,
                                                         int size) {
        if ((searchParamName != null) && !NAME.equals(searchParamName) &&
                !DESCRIPTION.equals(searchParamName)) {
            throw new UnsupportedSearchParamNameCertificateException(searchParamName);
        }
        if (!ID.equals(sortParamName) && !NAME.equals(sortParamName)
                && !CREATE_DATE.equals(sortParamName)) {
            throw new UnsupportedSortedParamNameCertificateException(sortParamName);
        }
        GiftCertificateDao giftCertificateDao = (GiftCertificateDao) dao;
        int countAllElements = giftCertificateDao.findCountSearch(
                tagNames, searchString, searchParamName);
        if (!paginationHelper.existNumPage(countAllElements, page, size)) {
            throw new NumPageNotExistException("" + page);
        }
        int startPosition = paginationHelper.findStartPosition(page, size);
        List<GiftCertificate> giftCertificateList =
                giftCertificateDao.search(tagNames, searchString, searchParamName,
                        sortParamName, orderDesc, startPosition, size);
        int lastPage = paginationHelper.findLastPage(countAllElements, size);
        return new PaginatedIdentifiable<>(giftCertificateList, page, lastPage);
    }

    @Override
    @Transactional
    public GiftCertificate updatePrice(int idCertificate, int price) {
        GiftCertificateDao giftCertificateDao = (GiftCertificateDao) dao;
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateDao.findById(idCertificate);
        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                () -> new CertificateNotFoundException("" + idCertificate));
        giftCertificate.setPrice(price);
        return giftCertificate;
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        String certificateName = giftCertificate.getName();
        GiftCertificateDao giftCertificateDao = (GiftCertificateDao) dao;
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateDao.findByName(certificateName);
        if (optionalGiftCertificate.isPresent()) {
            throw new CertificateNameAlreadyExistsException(certificateName);
        }
        List<Tag> tags = giftCertificate.getTags();
        List<Tag> savedTags = saveTags(tags);
        giftCertificate.setTags(savedTags);
        return giftCertificateDao.save(giftCertificate);
    }

    private List<Tag> saveTags(List<Tag> tags) {
        if (tags == null) {
            return new ArrayList<>();
        }
        List<Tag> savedTags = new ArrayList<>();
        for (Tag tag : tags) {
            String tagName = tag.getName();
            Optional<Tag> optionalTag = tagDao.findByName(tagName);
            Tag tagSaved = optionalTag.orElseGet(() -> tagDao.save(tag));
            savedTags.add(tagSaved);
        }
        return savedTags;
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate giftCertificate) {
        int idGiftCertificate = giftCertificate.getId();
        GiftCertificateDao giftCertificateDao = (GiftCertificateDao) dao;
        Optional<GiftCertificate> optionalGiftCertificateId =
                giftCertificateDao.findById(idGiftCertificate);
        if (!optionalGiftCertificateId.isPresent()) {
            throw new CertificateNotFoundException("" + idGiftCertificate);
        }
        String certificateName = giftCertificate.getName();
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateDao.findByName(certificateName);
        if (optionalGiftCertificate.isPresent()) {
            throw new CertificateNameAlreadyExistsException(certificateName);
        }
        giftCertificate.setCreateDate(null);
        List<Tag> tags = giftCertificate.getTags();
        List<Tag> savedTags = saveTags(tags);
        giftCertificate.setTags(savedTags);
        giftCertificateDao.update(giftCertificate);
        return giftCertificateDao.findById(idGiftCertificate).orElseThrow(
                () -> new CertificateNotFoundException("" + idGiftCertificate));
    }

    @Override
    protected void throwObjectNotFoundException(int id) {
        throw new CertificateNotFoundException("" + id);
    }

}