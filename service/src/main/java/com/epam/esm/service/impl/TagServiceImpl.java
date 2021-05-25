package com.epam.esm.service.impl;

import com.epam.esm.dao.api.TagDao;
import com.epam.esm.dao.api.UserDao;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.entity.identifiable.User;
import com.epam.esm.exception.tag.TagNameAlreadyExistsException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.pagination.PaginationHelper;
import com.epam.esm.service.api.AbstractService;
import com.epam.esm.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {

    private final UserDao userDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao,
                          UserDao userDao,
                          PaginationHelper paginationHelper) {
        super(tagDao, paginationHelper);
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Tag save(Tag tag) {
        String tagName = tag.getName();
        TagDao tagDao = (TagDao) dao;
        Optional<Tag> optionalTag = tagDao.findByName(tagName);
        if (optionalTag.isPresent()) {
            throw new TagNameAlreadyExistsException(tagName);
        }
        return tagDao.save(tag);
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("update operation is not supported for tag");
    }

    @Override
    protected void throwObjectNotFoundException(int id) {
        throw new TagNotFoundException("" + id);
    }

    @Override
    public Tag findMostWidelyUsedTagUserMaxOrderSum() {
        User user = userDao.findUserMaxOrdersSum();
        int userId = user.getId();
        TagDao tagDao = (TagDao) dao;
        return tagDao.findMostWidelyUsedByUser(userId);
    }

}