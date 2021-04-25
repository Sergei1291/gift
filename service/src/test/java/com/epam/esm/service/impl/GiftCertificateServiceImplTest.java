package com.epam.esm.service.impl;

import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.exception.certificate.CertificateNameAlreadyExistsException;
import com.epam.esm.exception.certificate.CertificateNotFoundException;
import com.epam.esm.exception.certificate.UnsupportedSearchParamNameCertificateException;
import com.epam.esm.exception.certificate.UnsupportedSortedParamNameCertificateException;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.pagination.PaginationHelper;
import com.epam.esm.service.api.GiftCertificateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GiftCertificateServiceImplTest {

    private final static GiftCertificate GIFT_CERTIFICATE_ONE =
            new GiftCertificate(1, "name1", "description1", 1, 1, null, null, new ArrayList<>());
    private final static GiftCertificate GIFT_CERTIFICATE_TWO =
            new GiftCertificate(2, "name1", "description1", 2, 2, null, null, new ArrayList<>());
    private final static List<GiftCertificate> CERTIFICATE_LIST = Arrays.asList(
            GIFT_CERTIFICATE_ONE, GIFT_CERTIFICATE_TWO);

    private final GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
    private final TagDao tagDao = Mockito.mock(TagDaoImpl.class);
    private final PaginationHelper paginationHelper = Mockito.mock(PaginationHelper.class);

    private final GiftCertificateService giftCertificateService =
            new GiftCertificateServiceImpl(
                    giftCertificateDao, tagDao, paginationHelper);

    @Test
    public void testSearchShouldThrowExceptionWhenSearchParamNameInvalid() {
        Assertions.assertThrows(UnsupportedSearchParamNameCertificateException.class,
                () -> giftCertificateService.search(null, null, "invalid", "id", false, 1, 10));
    }

    @Test
    public void testSearchShouldThrowExceptionWhenSortParamNameInvalid() {
        Assertions.assertThrows(UnsupportedSortedParamNameCertificateException.class,
                () -> giftCertificateService.search(null, null, null, "invalid", false, 1, 10));
    }

    @Test
    public void testSearchShouldReturnPaginatedCertificateList() {
        int numPage = 1;
        int sizePage = 10;
        when(giftCertificateDao.findCountSearch(null, null, null)).thenReturn(2);
        when(paginationHelper.existNumPage(2, numPage, sizePage)).thenReturn(true);
        when(paginationHelper.findStartPosition(numPage, sizePage)).thenReturn(0);
        when(giftCertificateDao.search(null, null, null,
                "id", false, 0, 10)).thenReturn(CERTIFICATE_LIST);
        when(paginationHelper.findLastPage(2, sizePage)).thenReturn(1);
        PaginatedIdentifiable actual = giftCertificateService.search(null, null, null, "id", false, numPage, sizePage);
        Assertions.assertEquals(new PaginatedIdentifiable(CERTIFICATE_LIST, 1, 1), actual);
    }

    @Test
    public void testUpdatePriceShouldThrowExceptionWhenCertificateNotFound() {
        int idCertificate = 1;
        when(giftCertificateDao.findById(idCertificate)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class,
                () -> giftCertificateService.updatePrice(idCertificate, 0));
    }

    @Test
    public void testUpdatePrice() {
        int idCertificate = 1;
        int price = 100;
        GiftCertificate giftCertificate =
                new GiftCertificate(1, "name1", "description1", 0, 0, null, null, new ArrayList<>());
        when(giftCertificateDao.findById(idCertificate)).thenReturn(Optional.of(giftCertificate));
        GiftCertificate actual = giftCertificateService.updatePrice(idCertificate, price);
        GiftCertificate expected =
                new GiftCertificate(1, "name1", "description1", 100, 0, null, null, new ArrayList<>());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveShouldThrowExceptionWhenCertificateNameAlreadyExists() {
        when(giftCertificateDao.findByName("name1")).thenReturn(Optional.of(new GiftCertificate()));
        Assertions.assertThrows(CertificateNameAlreadyExistsException.class,
                () -> giftCertificateService.save(GIFT_CERTIFICATE_ONE));
    }

    @Test
    public void testSaveShouldReturnSavedCertificate() {
        GiftCertificate giftCertificate =
                new GiftCertificate(0, "name1", null, null, null, null, "updateDate", new ArrayList<>());
        when(giftCertificateDao.findByName("name1")).thenReturn(Optional.empty());
        when(giftCertificateDao.save(any())).thenReturn(GIFT_CERTIFICATE_ONE);
        GiftCertificate actual = giftCertificateService.save(giftCertificate);
        GiftCertificate expected =
                new GiftCertificate(1, "name1", "description1", 1, 1, null, null, new ArrayList<>());
        Assertions.assertEquals(expected, actual);
        verify(tagDao, times(0)).findByName(anyString());
        verify(tagDao, times(0)).save(any());
    }

    @Test
    public void testUpdateShouldThrowExceptionWhenCertificateIdNotFound() {
        when(giftCertificateDao.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class,
                () -> giftCertificateService.update(GIFT_CERTIFICATE_ONE));
    }

    @Test
    public void testUpdateShouldThrowExceptionWhenCertificateNameAlreadyExists() {
        when(giftCertificateDao.findById(1)).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        when(giftCertificateDao.findByName("name1")).thenReturn(Optional.of(new GiftCertificate()));
        Assertions.assertThrows(CertificateNameAlreadyExistsException.class,
                () -> giftCertificateService.update(GIFT_CERTIFICATE_ONE));
    }

    @Test
    public void testRemoveShouldThrowExceptionWhenCertificateIdNotFound() {
        when(giftCertificateDao.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class,
                () -> giftCertificateService.remove(1));
    }

    @Test
    public void testRemoveShouldRemove() {
        when(giftCertificateDao.findById(0)).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        giftCertificateService.remove(0);
        verify(giftCertificateDao, times(1)).remove(any());
    }

    @Test
    public void testFindAllShouldReturnPaginatedCertificateList() {
        int numPage = 1;
        int sizePage = 10;
        when(giftCertificateDao.findCountAll()).thenReturn(2);
        when(paginationHelper.existNumPage(2, numPage, sizePage)).thenReturn(true);
        when(paginationHelper.findStartPosition(numPage, sizePage)).thenReturn(0);
        when(giftCertificateDao.findAll(0, 10)).thenReturn(CERTIFICATE_LIST);
        when(paginationHelper.findLastPage(2, sizePage)).thenReturn(1);
        PaginatedIdentifiable actual = giftCertificateService.findALl(1, 10);
        Assertions.assertEquals(new PaginatedIdentifiable(CERTIFICATE_LIST, 1, 1), actual);
    }

    @Test
    public void testFindByIdShouldReturnCertificate() {
        when(giftCertificateDao.findById(0)).thenReturn(Optional.of(CERTIFICATE_LIST.get(0)));
        GiftCertificate actual = giftCertificateService.findById(0);
        GiftCertificate expected =
                new GiftCertificate(1, "name1", "description1", 1, 1, null, null, new ArrayList<>());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindByIdShouldThrowExceptionWhenDatabaseNotContainCertificateId() {
        when(tagDao.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class,
                () -> giftCertificateService.findById(0));
    }

}