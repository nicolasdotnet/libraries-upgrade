/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.controllers;

import com.bigcity.dto.BookCategoryDTO;
import com.bigcity.entity.BookCategory;
import com.bigcity.services.interfaces.IBookCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author nicolasdotnet
 */
@Api(tags = "API pour les opérations CRUD sur les catégories de livre par un bibliothécaire.")
@RestController
public class BookCategoryController {

    private static final Logger log = LogManager.getLogger(BookCategoryController.class);

    @Autowired
    private IBookCategoryService iBookCategoryService;

    @ApiOperation("Enregister une nouvelle catégorie de livre")
    @PostMapping("/api/categories")
    @ApiResponses(value = {
        @ApiResponse(code = SC_OK, message = "ok", response = BookCategoryDTO.class),
        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookCategoryDTO.class),
        @ApiResponse(code = SC_CONFLICT, message = "la categorie existe déjà dans la base"),
        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
    })
    public ResponseEntity saveBookCategory(@Valid @RequestBody BookCategoryDTO bookCategoryDto) throws Exception {

        log.debug("saveBookCategory()");

        // TODO ajouter securité
        BookCategory bookCategorySave = iBookCategoryService.register(bookCategoryDto);

//code 201, ajouter l'URI 
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookCategorySave.getBookCategoryId())
                .toUri();

        return ResponseEntity.created(location)
                .body(bookCategorySave);

    }

    @ApiOperation("Récupère une catégorie de livre grâce à son ID à condition que celui-ci soit inscrit !")
    @GetMapping("/api/category/{id}")
        @ApiResponses(value = {
        @ApiResponse(code = SC_OK, message = "ok", response = BookCategoryDTO.class),
        @ApiResponse(code = SC_NOT_FOUND, message = "la catégorie n'existe pas dans la base"),
        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookCategoryDTO.class),
        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
    })
    public ResponseEntity showBookCategory(@PathVariable("id") int id) {

        log.debug("showBooking() id: {}", id);

        return ResponseEntity.ok(iBookCategoryService.getBookCategory(Long.valueOf(id)));

    }

    @ApiOperation("Récupère l'ensemble catégories de livre de la base ou récupèrer une liste de livre a partir d'un mot clé sur le titre")
    @GetMapping("/api/categories")
        @ApiResponses(value = {
        @ApiResponse(code = SC_OK, message = "ok", response = BookCategoryDTO.class),
        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookCategoryDTO.class),
        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
    })
    public ResponseEntity showAllBookCategories(@RequestParam(defaultValue = " ") String label) throws Exception {

        // RequestBody DTO Search
        log.debug("showAllBookCategories()", label);

        List<BookCategory> bookCategories = null;

        if (label.equals(" ")) {

            bookCategories = iBookCategoryService.getAllBookCategories();

            return ResponseEntity.ok(bookCategories);

        }

        BookCategory bookCategorie = iBookCategoryService.getBookCategoryByLabel(label);

        return ResponseEntity.ok(bookCategorie);

    }

    @ApiOperation("Mettre à jour la catégorie d'un livre à partir de son ID présent dans la base")
    @PutMapping("/api/category/{id}")
        @ApiResponses(value = {
        @ApiResponse(code = SC_OK, message = "ok", response = BookCategoryDTO.class),
        @ApiResponse(code = SC_NOT_FOUND, message = "la catégorie n'existe pas dans la base"),
        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookCategoryDTO.class),
        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
    })
    public ResponseEntity updateBookCategory(@PathVariable("id") int id, @Valid
            @RequestBody BookCategoryDTO bookCategoryDTO) throws Exception {

        log.debug("updateBookCategory()");

        return ResponseEntity.ok().body(iBookCategoryService.edit(bookCategoryDTO));

    }
}
