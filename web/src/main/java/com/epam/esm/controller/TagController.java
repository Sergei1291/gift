package com.epam.esm.controller;

import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.linker.TagControllerLinker;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping(value = "/tags",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;
    private final TagControllerLinker tagControllerLinker;

    @Autowired
    public TagController(TagService tagService,
                         TagControllerLinker tagControllerLinker) {
        this.tagService = tagService;
        this.tagControllerLinker = tagControllerLinker;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tag> save(@RequestBody @Valid Tag tag) {
        Tag savedTag = tagService.save(tag);
        return tagControllerLinker.addLinkOnSelf(savedTag);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable @Positive int id) {
        tagService.remove(id);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Tag>> findAll(
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "10") @Positive int size) {
        PaginatedIdentifiable<Tag> paginatedIdentifiable =
                tagService.findALl(page, size);
        return tagControllerLinker.addLinksFindAll(paginatedIdentifiable, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> findById(@PathVariable @Positive int id) {
        Tag tag = tagService.findById(id);
        return tagControllerLinker.addLinkOnSelf(tag);
    }

    @GetMapping("/mostWidelyUsed")
    public ResponseEntity<Tag> findMostWidelyUsedTagUserMaxOrderSum() {
        Tag tag = tagService.findMostWidelyUsedTagUserMaxOrderSum();
        return tagControllerLinker.addLinkOnSelf(tag);
    }

}