package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.helper.impl.TagDaoHelper;
import com.epam.esm.entity.identifiable.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {
        TagDaoImpl.class,
        TagDaoHelper.class})
@Import(TestConfig.class)
public class TagDaoImplTest {

    @Autowired
    private TagDaoImpl tagDaoImpl;

    @Test
    public void testSaveShouldSaveTagInDatabaseAndReturnSavedTag() {
        Tag tag = new Tag("newTag");
        Tag actualTag = tagDaoImpl.save(tag);
        Assertions.assertEquals(new Tag(4, "newTag", null), actualTag);
    }

    @Test
    public void testUpdateShouldThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> {
                    tagDaoImpl.update(null);
                });
    }

    @Test
    public void testRemoveShouldRemoveTagFromDatabase() {
        Tag tag = new Tag(2, null, null);
        tagDaoImpl.remove(tag);
        List<Tag> tagList = tagDaoImpl.findAll();
        int sizeTagList = tagList.size();
        Assertions.assertEquals(2, sizeTagList);
    }

    @Test
    public void testFindAllShouldReturnDatabaseListTag() {
        List<Tag> tagList = tagDaoImpl.findAll();
        Assertions.assertEquals(3, tagList.size());
    }

    @Test
    public void testFindAllShouldReturnDatabaseListTagByPage() {
        List<Tag> tagList = tagDaoImpl.findAll(2, 1);
        Assertions.assertEquals(3, tagList.get(0).getId());
    }

    @Test
    public void testFindByIdShouldReturnOptionalTagWhenDatabaseContainTagId() {
        Optional<Tag> optionalTag = tagDaoImpl.findById(2);
        Assertions.assertEquals(2, optionalTag.get().getId());
    }

    @Test
    public void testFindByIdShouldReturnOptionalEmptyWhenDatabaseNotContainTagId() {
        Optional<Tag> optionalTag = tagDaoImpl.findById(4);
        Assertions.assertEquals(Optional.empty(), optionalTag);
    }

    @Test
    public void testFindCountAll() {
        int actual = tagDaoImpl.findCountAll();
        Assertions.assertEquals(3, actual);
    }

    @Test
    public void testFindByNameShouldReturnOptionalTagWhenDatabaseContainTagName() {
        Optional<Tag> actual = tagDaoImpl.findByName("one");
        Assertions.assertEquals(1, actual.get().getId());
    }

    @Test
    public void testFindByNameShouldReturnOptionalEmptyWhenDatabaseNotContainTagName() {
        Optional<Tag> actual = tagDaoImpl.findByName("not have name");
        Assertions.assertEquals(Optional.empty(), actual);
    }

}