package com.epam.esm.controller;

import com.epam.esm.entity.identifiable.User;
import com.epam.esm.linker.UserControllerLinker;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping(value = "/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;
    private final UserControllerLinker userControllerLinker;

    @Autowired
    public UserController(UserService userService,
                          UserControllerLinker userControllerLinker) {
        this.userService = userService;
        this.userControllerLinker = userControllerLinker;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<User>> findAll(
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "10") @Positive int size) {
        PaginatedIdentifiable<User> paginatedIdentifiable =
                userService.findALl(page, size);
        return userControllerLinker.addLinksFindAll(paginatedIdentifiable, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable @Positive int id) {
        User user = userService.findById(id);
        return userControllerLinker.addLinkOnSelf(user);
    }

}