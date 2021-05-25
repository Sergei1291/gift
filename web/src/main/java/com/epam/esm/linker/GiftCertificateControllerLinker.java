package com.epam.esm.linker;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.pagination.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateControllerLinker {

    private final static String FIRST_PAGE = "firstPage";
    private final static String PREVIOUS_PAGE = "previousPage";
    private final static String NEXT_PAGE = "nextPage";
    private final static String LAST_PAGE = "lastPage";

    private final PaginationHelper paginationHelper;

    @Autowired
    public GiftCertificateControllerLinker(PaginationHelper paginationHelper) {
        this.paginationHelper = paginationHelper;
    }

    public ResponseEntity<GiftCertificate> addLinkOnSelf(GiftCertificate certificate) {
        int id = certificate.getId();
        Link link = linkTo(methodOn(GiftCertificateController.class)
                .findById(id)).withSelfRel();
        certificate.add(link);
        return ResponseEntity.ok(certificate);
    }

    public ResponseEntity<CollectionModel<GiftCertificate>> addLinksFindAll(
            PaginatedIdentifiable<GiftCertificate> paginatedIdentifiable,
            int page, int size) {
        List<GiftCertificate> certificateList = paginatedIdentifiable.getIdentifiableList();
        certificateList.forEach(this::addLinkOnSelf);
        Link allCertificatesLink = linkTo(methodOn(GiftCertificateController.class)
                .findAll(page, size))
                .withSelfRel();
        Link firstPageLink = linkTo(methodOn(GiftCertificateController.class)
                .findAll(1, size))
                .withRel(FIRST_PAGE);
        CollectionModel<GiftCertificate> collectionModel =
                CollectionModel.of(certificateList, allCertificatesLink, firstPageLink);
        if (paginationHelper.hasPreviousPage(paginatedIdentifiable)) {
            Link previousPageLink = linkTo(methodOn(GiftCertificateController.class)
                    .findAll(page - 1, size))
                    .withRel(PREVIOUS_PAGE);
            collectionModel.add(previousPageLink);
        }
        if (paginationHelper.hasNextPage(paginatedIdentifiable)) {
            Link nextPageLink = linkTo(methodOn(GiftCertificateController.class)
                    .findAll(page + 1, size))
                    .withRel(NEXT_PAGE);
            collectionModel.add(nextPageLink);
        }
        int lastPage = paginatedIdentifiable.getLastPage();
        if (lastPage != 1) {
            Link lastPageLink = linkTo(methodOn(GiftCertificateController.class)
                    .findAll(lastPage, size))
                    .withRel(LAST_PAGE);
            collectionModel.add(lastPageLink);
        }
        return ResponseEntity.ok(collectionModel);
    }

    public ResponseEntity<CollectionModel<GiftCertificate>> addLinksSearch(
            PaginatedIdentifiable<GiftCertificate> paginatedIdentifiable,
            List<String> tagNames,
            String part, String nameSearchParam,
            String nameSortParam, boolean orderDesc,
            int page, int size) {
        List<GiftCertificate> certificateList = paginatedIdentifiable.getIdentifiableList();
        certificateList.forEach(this::addLinkOnSelf);
        Link allCertificatesLink = linkTo(methodOn(GiftCertificateController.class)
                .search(tagNames, part, nameSearchParam, nameSortParam, orderDesc, page, size))
                .withSelfRel();
        Link firstPageLink = linkTo(methodOn(GiftCertificateController.class)
                .search(tagNames, part, nameSearchParam, nameSortParam, orderDesc, 1, size))
                .withRel(FIRST_PAGE);
        CollectionModel<GiftCertificate> collectionModel =
                CollectionModel.of(certificateList, allCertificatesLink, firstPageLink);
        if (paginationHelper.hasPreviousPage(paginatedIdentifiable)) {
            Link previousPageLink = linkTo(methodOn(GiftCertificateController.class)
                    .search(tagNames, part, nameSearchParam, nameSortParam, orderDesc, page - 1, size))
                    .withRel(PREVIOUS_PAGE);
            collectionModel.add(previousPageLink);
        }
        if (paginationHelper.hasNextPage(paginatedIdentifiable)) {
            Link nextPageLink = linkTo(methodOn(GiftCertificateController.class)
                    .search(tagNames, part, nameSearchParam, nameSortParam, orderDesc, page + 1, size))
                    .withRel(NEXT_PAGE);
            collectionModel.add(nextPageLink);
        }
        int lastPage = paginatedIdentifiable.getLastPage();
        if (lastPage != 1) {
            Link lastPageLink = linkTo(methodOn(GiftCertificateController.class)
                    .search(tagNames, part, nameSearchParam, nameSortParam, orderDesc, lastPage, size))
                    .withRel(LAST_PAGE);
            collectionModel.add(lastPageLink);
        }
        return ResponseEntity.ok(collectionModel);
    }

}