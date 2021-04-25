package com.epam.esm.linker;

import com.epam.esm.controller.TagController;
import com.epam.esm.entity.identifiable.Tag;
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
public class TagControllerLinker {

    private final static String FIRST_PAGE = "firstPage";
    private final static String PREVIOUS_PAGE = "previousPage";
    private final static String NEXT_PAGE = "nextPage";
    private final static String LAST_PAGE = "lastPage";

    private final PaginationHelper paginationHelper;

    @Autowired
    public TagControllerLinker(PaginationHelper paginationHelper) {
        this.paginationHelper = paginationHelper;
    }

    public ResponseEntity<Tag> addLinkOnSelf(Tag tag) {
        int id = tag.getId();
        Link link = linkTo(methodOn(TagController.class).findById(id))
                .withSelfRel();
        tag.add(link);
        return ResponseEntity.ok(tag);
    }

    public ResponseEntity<CollectionModel<Tag>> addLinksFindAll(
            PaginatedIdentifiable<Tag> paginatedIdentifiable,
            int page, int size) {
        List<Tag> tagList = paginatedIdentifiable.getIdentifiableList();
        tagList.forEach(this::addLinkOnSelf);
        Link allTagsLink = linkTo(methodOn(TagController.class)
                .findAll(page, size))
                .withSelfRel();
        Link firstPageLink = linkTo(methodOn(TagController.class)
                .findAll(1, size))
                .withRel(FIRST_PAGE);
        CollectionModel<Tag> collectionModel =
                CollectionModel.of(tagList, allTagsLink, firstPageLink);
        if (paginationHelper.hasPreviousPage(paginatedIdentifiable)) {
            Link previousPageLink = linkTo(methodOn(TagController.class)
                    .findAll(page - 1, size))
                    .withRel(PREVIOUS_PAGE);
            collectionModel.add(previousPageLink);
        }
        if (paginationHelper.hasNextPage(paginatedIdentifiable)) {
            Link nextPageLink = linkTo(methodOn(TagController.class)
                    .findAll(page + 1, size))
                    .withRel(NEXT_PAGE);
            collectionModel.add(nextPageLink);
        }
        int lastPage = paginatedIdentifiable.getLastPage();
        if (lastPage != 1) {
            Link lastPageLink = linkTo(methodOn(TagController.class)
                    .findAll(lastPage, size))
                    .withRel(LAST_PAGE);
            collectionModel.add(lastPageLink);
        }
        return ResponseEntity.ok(collectionModel);
    }

}