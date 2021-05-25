package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.helper.impl.GiftCertificateDaoHelper;
import com.epam.esm.entity.identifiable.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {
        GiftCertificateDaoImpl.class,
        GiftCertificateDaoHelper.class})
@Import(TestConfig.class)
public class GiftCertificateImplTest {

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDaoImpl;

    @Test
    public void testFindAllShouldReturnDatabaseListCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateDaoImpl.findAll();
        Assertions.assertEquals(4, giftCertificates.size());
    }

    @Test
    public void testFindAllShouldReturnDatabaseListCertificatesByPage() {
        List<GiftCertificate> giftCertificates =
                giftCertificateDaoImpl.findAll(2, 1);
        Assertions.assertEquals(3, giftCertificates.get(0).getId());
    }

    @Test
    public void testFindByIdShouldReturnOptionalCertificateWhenDatabaseContainCertificateId() {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDaoImpl.findById(2);
        Assertions.assertEquals(2, optionalGiftCertificate.get().getId());
    }

    @Test
    public void testFindByIdShouldReturnOptionalEmptyWhenDatabaseNotContainCertificateId() {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDaoImpl.findById(10);
        Assertions.assertEquals(Optional.empty(), optionalGiftCertificate);
    }

    @Test
    public void testFindCountAll() {
        int actual = giftCertificateDaoImpl.findCountAll();
        Assertions.assertEquals(4, actual);
    }

    @Test
    public void testFindByNameShouldReturnOptionalCertificateWhenDatabaseContainCertificateName() {
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findByName("name one");
        Assertions.assertEquals(1, actual.get().getId());
    }

    @Test
    public void testFindByNameShouldReturnOptionalEmptyWhenDatabaseNotContainCertificatesName() {
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findByName("not have name");
        Assertions.assertEquals(Optional.empty(), actual);
    }

}