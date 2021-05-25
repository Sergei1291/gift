package com.epam.esm.controller;

import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.linker.GiftCertificateControllerLinker;
import com.epam.esm.pagination.PaginatedIdentifiable;
import com.epam.esm.service.api.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/certificates",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateControllerLinker giftCertificateControllerLinker;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateControllerLinker giftCertificateControllerLinker) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateControllerLinker = giftCertificateControllerLinker;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GiftCertificate> save(@RequestBody @Valid GiftCertificate giftCertificate) {
        GiftCertificate savedGiftCertificate = giftCertificateService.save(giftCertificate);
        return giftCertificateControllerLinker.addLinkOnSelf(savedGiftCertificate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificate> update(
            @PathVariable @Positive int id,
            @RequestBody GiftCertificate giftCertificate) {
        giftCertificate.setId(id);
        GiftCertificate updatedGiftCertificate = giftCertificateService.update(giftCertificate);
        return giftCertificateControllerLinker.addLinkOnSelf(updatedGiftCertificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable @Positive int id) {
        giftCertificateService.remove(id);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<GiftCertificate>> findAll(
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "10") @Positive int size) {
        PaginatedIdentifiable<GiftCertificate> paginatedIdentifiable =
                giftCertificateService.findALl(page, size);
        return giftCertificateControllerLinker.addLinksFindAll(paginatedIdentifiable, page, size);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GiftCertificate> findById(@PathVariable @Positive int id) {
        GiftCertificate giftCertificate = giftCertificateService.findById(id);
        return giftCertificateControllerLinker.addLinkOnSelf(giftCertificate);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<CollectionModel<GiftCertificate>> search(
            @RequestParam(required = false, defaultValue = "") List<String> tagNames,
            @RequestParam(required = false, defaultValue = "") String part,
            @RequestParam(required = false, defaultValue = "name") String nameSearchParam,
            @RequestParam(required = false, defaultValue = "id") String nameSortParam,
            @RequestParam(required = false, defaultValue = "false") boolean orderDesc,
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "10") @Positive int size) {
        PaginatedIdentifiable<GiftCertificate> paginatedIdentifiable = giftCertificateService.search(
                tagNames, part, nameSearchParam, nameSortParam, orderDesc, page, size);
        return giftCertificateControllerLinker.addLinksSearch(paginatedIdentifiable, tagNames,
                part, nameSearchParam, nameSortParam, orderDesc, page, size);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<GiftCertificate> updatePrice(@PathVariable @Positive int id,
                                                       @RequestParam @PositiveOrZero int price) {
        GiftCertificate giftCertificate = giftCertificateService.updatePrice(id, price);
        return giftCertificateControllerLinker.addLinkOnSelf(giftCertificate);
    }

}