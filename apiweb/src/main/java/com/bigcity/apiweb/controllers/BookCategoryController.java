/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.controllers;

import com.bigcity.apiweb.dto.BookCategoryDTO;
import com.bigcity.apiweb.entity.BookCategory;
import com.bigcity.apiweb.services.interfaces.IBookCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author nicolasdotnet
 */
@Api(tags = "API pour les opérations CRUD sur les catégories de livre.")
@RestController
public class BookCategoryController {

    private static final Logger log = LogManager.getLogger(BookCategoryController.class);

    @Autowired
    private IBookCategoryService iBookCategoryService;

    @ApiOperation("Enregister une nouvelle catégorie de livre")
    @PostMapping("/api/librarian/category")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "catégorie de livre créée", response = BookCategoryDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookCategoryDTO.class),
        @ApiResponse(code = 409, message = "la categorie existe déjà dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    public ResponseEntity saveBookCategory(@Valid @RequestBody BookCategoryDTO bookCategoryDto) throws Exception {

        log.debug("saveBookCategory()");

        BookCategory bookCategorySave = iBookCategoryService.register(bookCategoryDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookCategorySave.getBookCategoryId())
                .toUri();

        return ResponseEntity.created(location)
                .body(bookCategorySave);

    }

    @ApiOperation("Récupère une catégorie de livre grâce à son ID")
    @GetMapping("/api/librarian/category/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookCategoryDTO.class),
        @ApiResponse(code = 404, message = "la catégorie n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    public ResponseEntity showBookCategory(@PathVariable("id") int id) {

        log.debug("showBookCategory() id: {}", id);

        return ResponseEntity.ok(iBookCategoryService.getBookCategory(Long.valueOf(id)));

    }

    @ApiOperation("Mettre à jour la catégorie d'un livre")
    @PutMapping("/api/librarian/category/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookCategoryDTO.class),
        @ApiResponse(code = 404, message = "la catégorie n'existe pas dans la base"),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookCategoryDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    public ResponseEntity updateBookCategory(@PathVariable("id") int id, @Valid
            @RequestBody BookCategoryDTO bookCategoryDTO) throws Exception {

        log.debug("updateBookCategory()");

        return ResponseEntity.ok().body(iBookCategoryService.edit(bookCategoryDTO));

    }
}
