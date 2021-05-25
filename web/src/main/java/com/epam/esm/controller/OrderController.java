package com.epam.esm.controller;

import com.epam.esm.entity.identifiable.Order;
import com.epam.esm.linker.OrderControllerLinker;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping(value = "/orders",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;
    private final OrderControllerLinker orderControllerLinker;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderControllerLinker orderControllerLinker) {
        this.orderService = orderService;
        this.orderControllerLinker = orderControllerLinker;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Order>> findAll(
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "10") @Positive int size) {
        PaginatedIdentifiable<Order> paginatedIdentifiable =
                orderService.findALl(page, size);
        return orderControllerLinker.addLinksFindAll(paginatedIdentifiable, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable @Positive int id) {
        Order order = orderService.findById(id);
        return orderControllerLinker.addLinkOnSelf(order);
    }

    @GetMapping(value = "/{orderId}", params = {"userId"})
    public ResponseEntity<Order> findOrderByUser(@PathVariable @Positive int orderId,
                                                 @RequestParam @Positive int userId) {
        Order order = orderService.findUserOrderById(userId, orderId);
        return orderControllerLinker.addLinkOnSelf(order);
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity<CollectionModel<Order>> findUserOrders(
            @RequestParam @Positive int userId,
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "10") @Positive int size) {
        PaginatedIdentifiable<Order> paginatedIdentifiable =
                orderService.findAllOrdersByUserId(userId, page, size);
        return orderControllerLinker.addLinksFindUserOrders(paginatedIdentifiable, userId, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Order> makeOrderCertificateByUser(
            @RequestParam @Positive int userId,
            @RequestParam @Positive int certificateId) {
        Order order = orderService.makeOrderByUserIdCertificateId(userId, certificateId);
        return orderControllerLinker.addLinkOnSelf(order);
    }

}