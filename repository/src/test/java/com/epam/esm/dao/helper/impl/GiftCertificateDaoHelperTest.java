package com.epam.esm.dao.helper.impl;

import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = GiftCertificateDaoHelper.class)
public class GiftCertificateDaoHelperTest {

    private final static Tag TAG_ONE = new Tag("tag1");
    private final static Tag TAG_TWO = new Tag("tag2");
    private final static List<Tag> TAG_LIST = Arrays.asList(TAG_ONE, TAG_TWO);
    private final static GiftCertificate GIFT_CERTIFICATE_NULLABLE =
            new GiftCertificate(0, null, null, null, null, null, null, null);
    private final static GiftCertificate GIFT_CERTIFICATE_NOT_NULLABLE =
            new GiftCertificate(0, "null", "null", 10, 20, null, "null", Arrays.asList());

    @Autowired
    private GiftCertificateDaoHelper giftCertificateDaoHelper;

    @Test
    public void testUpdateShouldNotUpdateFields() {
        GiftCertificate giftCertificate = new GiftCertificate(
                1, "name", "description", 1, 2, "createDate", "updateDate", TAG_LIST);
        giftCertificateDaoHelper.update(GIFT_CERTIFICATE_NULLABLE, giftCertificate);
        Assertions.assertEquals(new GiftCertificate(1, "name", "description",
                1, 2, "createDate", "updateDate", TAG_LIST), giftCertificate);
    }

    @Test
    public void testUpdateShouldUpdateNotNullableFields() {
        GiftCertificate giftCertificate = new GiftCertificate(
                1, "name", "description", 1, 2, "createDate", "updateDate", TAG_LIST);
        giftCertificateDaoHelper.update(GIFT_CERTIFICATE_NOT_NULLABLE, giftCertificate);
        Assertions.assertEquals(new GiftCertificate(1, "null", "null", 10, 20, "createDate",
                "updateDate", Arrays.asList()), giftCertificate);
    }

}