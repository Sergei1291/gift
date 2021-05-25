package com.epam.esm.service.api;

import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.pagination.PaginatedIdentifiable;

import java.util.List;

/**
 * This interface define additional methods for business logic on GiftCertificate.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface GiftCertificateService extends Service<GiftCertificate> {

    /**
     * This method is used to find list of all GiftCertificate objects
     * by several params: tag's names, part of searched param. Also list of
     * objects can be sorted by sortParamName by order equal param orderDesc.
     * Some of params of method can be absent.
     *
     * @param tagNames        These are names of tags for searching.
     * @param searchString    This is search string.
     * @param searchParamName This is name search param.
     * @param sortParamName   This is name param for sorting.
     * @param orderDesc       This is direction for sorting.
     * @param page            This is num page for finding objects.
     * @param size            This is max object's quantity for one page.
     * @return Object which contains all founded objects for page with additional
     * information for pagination operation.
     */
    PaginatedIdentifiable<GiftCertificate> search(List<String> tagNames,
                                                  String searchString,
                                                  String searchParamName,
                                                  String sortParamName,
                                                  boolean orderDesc,
                                                  int page,
                                                  int size);

    /**
     * This method is used to update only one field of GiftCertificate object
     * is price. Update operation do for GiftCertificate by idCertificate.
     *
     * @param idCertificate This is id for updating GiftCertificate.
     * @param price         This is new price for GiftCertificate.
     * @return Updated GiftCertificate.
     */
    GiftCertificate updatePrice(int idCertificate, int price);

}