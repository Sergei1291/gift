package com.epam.esm.linker;

import com.epam.esm.controller.OrderController;
import com.epam.esm.entity.identifiable.Order;
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
public class OrderControllerLinker {

    private final static String FIRST_PAGE = "firstPage";
    private final static String PREVIOUS_PAGE = "previousPage";
    private final static String NEXT_PAGE = "nextPage";
    private final static String LAST_PAGE = "lastPage";

    private final PaginationHelper paginationHelper;

    @Autowired
    public OrderControllerLinker(PaginationHelper paginationHelper) {
        this.paginationHelper = paginationHelper;
    }

    public ResponseEntity<Order> addLinkOnSelf(Order order) {
        int id = order.getId();
        Link link = linkTo(methodOn(OrderController.class).findById(id))
                .withSelfRel();
        order.add(link);
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<CollectionModel<Order>> addLinksFindAll(
            PaginatedIdentifiable<Order> paginatedIdentifiable,
            int page, int size) {
        List<Order> orderList = paginatedIdentifiable.getIdentifiableList();
        orderList.forEach(this::addLinkOnSelf);
        Link allOrdersLink = linkTo(methodOn(OrderController.class)
                .findAll(page, size))
                .withSelfRel();
        Link firstPageLink = linkTo(methodOn(OrderController.class)
                .findAll(1, size))
                .withRel(FIRST_PAGE);
        CollectionModel<Order> collectionModel =
                CollectionModel.of(orderList, allOrdersLink, firstPageLink);
        if (paginationHelper.hasPreviousPage(paginatedIdentifiable)) {
            Link previousPageLink = linkTo(methodOn(OrderController.class)
                    .findAll(page - 1, size))
                    .withRel(PREVIOUS_PAGE);
            collectionModel.add(previousPageLink);
        }
        if (paginationHelper.hasNextPage(paginatedIdentifiable)) {
            Link nextPageLink = linkTo(methodOn(OrderController.class)
                    .findAll(page + 1, size))
                    .withRel(NEXT_PAGE);
            collectionModel.add(nextPageLink);
        }
        int lastPage = paginatedIdentifiable.getLastPage();
        if (lastPage != 1) {
            Link lastPageLink = linkTo(methodOn(OrderController.class)
                    .findAll(lastPage, size))
                    .withRel(LAST_PAGE);
            collectionModel.add(lastPageLink);
        }
        return ResponseEntity.ok(collectionModel);
    }

    public ResponseEntity<CollectionModel<Order>> addLinksFindUserOrders(
            PaginatedIdentifiable<Order> paginatedIdentifiable,
            int userId,
            int page, int size) {
        List<Order> orderList = paginatedIdentifiable.getIdentifiableList();
        orderList.forEach(this::addLinkOnSelf);
        Link allOrdersLink = linkTo(methodOn(OrderController.class)
                .findUserOrders(userId, page, size))
                .withSelfRel();
        Link firstPageLink = linkTo(methodOn(OrderController.class)
                .findUserOrders(userId, 1, size))
                .withRel(FIRST_PAGE);
        CollectionModel<Order> collectionModel =
                CollectionModel.of(orderList, allOrdersLink, firstPageLink);
        if (paginationHelper.hasPreviousPage(paginatedIdentifiable)) {
            Link previousPageLink = linkTo(methodOn(OrderController.class)
                    .findUserOrders(userId, page - 1, size))
                    .withRel(PREVIOUS_PAGE);
            collectionModel.add(previousPageLink);
        }
        if (paginationHelper.hasNextPage(paginatedIdentifiable)) {
            Link nextPageLink = linkTo(methodOn(OrderController.class)
                    .findUserOrders(userId, page + 1, size))
                    .withRel(NEXT_PAGE);
            collectionModel.add(nextPageLink);
        }
        int lastPage = paginatedIdentifiable.getLastPage();
        if (lastPage != 1) {
            Link lastPageLink = linkTo(methodOn(OrderController.class)
                    .findUserOrders(userId, lastPage, size))
                    .withRel(LAST_PAGE);
            collectionModel.add(lastPageLink);
        }
        return ResponseEntity.ok(collectionModel);
    }

}