package com.epam.esm.dao.helper.impl;

import com.epam.esm.dao.helper.DaoHelper;
import com.epam.esm.entity.identifiable.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDaoHelper implements DaoHelper<Tag> {

    @Override
    public void update(Tag updatedTag, Tag existingTag) {
        throw new UnsupportedOperationException();
    }

}