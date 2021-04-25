package com.epam.esm.dao.helper.impl;

import com.epam.esm.dao.helper.DaoHelper;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftCertificateDaoHelper implements DaoHelper<GiftCertificate> {

    @Override
    public void update(GiftCertificate updatedCertificate,
                       GiftCertificate existingCertificate) {
        updateName(updatedCertificate, existingCertificate);
        updateDescription(updatedCertificate, existingCertificate);
        updateDuration(updatedCertificate, existingCertificate);
        updatePrice(updatedCertificate, existingCertificate);
        updateTags(updatedCertificate, existingCertificate);
    }

    private void updateName(GiftCertificate updatedCertificate,
                            GiftCertificate existingCertificate) {
        String name = updatedCertificate.getName();
        if (name != null) {
            existingCertificate.setName(name);
        }
    }

    private void updateDescription(GiftCertificate updatedCertificate,
                                   GiftCertificate existingCertificate) {
        String description = updatedCertificate.getDescription();
        if (description != null) {
            existingCertificate.setDescription(description);
        }
    }

    private void updateDuration(GiftCertificate updatedCertificate,
                                GiftCertificate existingCertificate) {
        Integer duration = updatedCertificate.getDuration();
        if (duration != null) {
            existingCertificate.setDuration(duration);
        }
    }

    private void updatePrice(GiftCertificate updatedCertificate,
                             GiftCertificate existingCertificate) {
        Integer price = updatedCertificate.getPrice();
        if (price != null) {
            existingCertificate.setPrice(price);
        }
    }

    private void updateTags(GiftCertificate updatedCertificate,
                            GiftCertificate existingCertificate) {
        List<Tag> tags = updatedCertificate.getTags();
        if (tags != null) {
            existingCertificate.setTags(tags);
        }
    }

}