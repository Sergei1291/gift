package com.epam.esm.dao.api;

import com.epam.esm.entity.identifiable.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * The interface extends interface Dao. Interface defines additional methods
 * for finding and sorting objects type GiftCertificate.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface GiftCertificateDao extends Dao<GiftCertificate> {

    /**
     * This method is used to find Gift Certificate from data source by name.
     *
     * @param name This is name of searched certificate.
     * @return This is founded gift certificate from data source.
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * This method is used to find list of all GiftCertificate objects
     * by several params: tag's names, part of searched param. Also list of
     * objects can be sorted by sortParamName by order equal param orderDesc.
     * Some of params of method can be absent.
     *
     * @param tagNames         These are names of tags.
     * @param searchString     This is search string.
     * @param searchParamName  This is name search param.
     * @param sortParamName    This is name param for sorting.
     * @param orderDesc        This is direction for sorting.
     * @param startPosition    This is start position from list of all founded
     *                         elements.
     * @param quantityElements This is quantity first founded elements.
     * @return All founded objects according to params of searching.
     */
    List<GiftCertificate> search(List<String> tagNames,
                                 String searchString,
                                 String searchParamName,
                                 String sortParamName,
                                 boolean orderDesc,
                                 int startPosition,
                                 int quantityElements);

    /**
     * This method is used to find count of all searched objects according to
     * several params.
     *
     * @param tagNames        These are names of tags.
     * @param searchString    This is search string.
     * @param searchParamName This is name search param.
     * @return Count of all searched objects according to params of searching.
     */
    int findCountSearch(List<String> tagNames,
                        String searchString,
                        String searchParamName);

}