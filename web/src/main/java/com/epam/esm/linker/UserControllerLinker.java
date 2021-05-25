package com.epam.esm.linker;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.entity.identifiable.User;
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
public class UserControllerLinker {

    private final static String FIRST_PAGE = "firstPage";
    private final static String PREVIOUS_PAGE = "previousPage";
    private final static String NEXT_PAGE = "nextPage";
    private final static String LAST_PAGE = "lastPage";
    private final static String ORDERS = "orders";

    private final static int DEFAULT_NUM_PAGE = 1;
    private final static int DEFAULT_SIZE_PAGE = 10;

    private final PaginationHelper paginationHelper;

    @Autowired
    public UserControllerLinker(PaginationHelper paginationHelper) {
        this.paginationHelper = paginationHelper;
    }

    public ResponseEntity<User> addLinkOnSelf(User user) {
        int id = user.getId();
        Link link = linkTo(methodOn(UserController.class)
                .findById(id)).withSelfRel();
        user.add(link);
        Link linkOrders = linkTo(methodOn(OrderController.class)
                .findUserOrders(id, DEFAULT_NUM_PAGE, DEFAULT_SIZE_PAGE)).withRel(ORDERS);
        user.add(linkOrders);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<CollectionModel<User>> addLinksFindAll(
            PaginatedIdentifiable<User> paginatedIdentifiable,
            int page, int size) {
        List<User> userList = paginatedIdentifiable.getIdentifiableList();
        userList.forEach(this::addLinkOnSelf);
        Link allUsersLink = linkTo(methodOn(UserController.class)
                .findAll(page, size))
                .withSelfRel();
        Link firstPageLink = linkTo(methodOn(UserController.class)
                .findAll(1, size))
                .withRel(FIRST_PAGE);
        CollectionModel<User> collectionModel =
                CollectionModel.of(userList, allUsersLink, firstPageLink);
        if (paginationHelper.hasPreviousPage(paginatedIdentifiable)) {
            Link previousPageLink = linkTo(methodOn(UserController.class)
                    .findAll(page - 1, size))
                    .withRel(PREVIOUS_PAGE);
            collectionModel.add(previousPageLink);
        }
        if (paginationHelper.hasNextPage(paginatedIdentifiable)) {
            Link nextPageLink = linkTo(methodOn(UserController.class)
                    .findAll(page + 1, size))
                    .withRel(NEXT_PAGE);
            collectionModel.add(nextPageLink);
        }
        int lastPage = paginatedIdentifiable.getLastPage();
        if (lastPage != 1) {
            Link lastPageLink = linkTo(methodOn(UserController.class)
                    .findAll(lastPage, size))
                    .withRel(LAST_PAGE);
            collectionModel.add(lastPageLink);
        }
        return ResponseEntity.ok(collectionModel);
    }

}