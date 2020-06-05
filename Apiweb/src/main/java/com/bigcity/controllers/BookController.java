/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.controllers;

import com.bigcity.dto.BookDTO;
import com.bigcity.entity.Book;
import com.bigcity.services.interfaces.IBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
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
@Api(tags = "API pour les opérations CRUD sur les livres par un bibliothécaire.")
@RestController
public class BookController {

    private static final Logger log = LogManager.getLogger(BookController.class);

    @Autowired
    private IBookService iBookService;

    @ApiOperation("Enregister un nouveau livre")
    @PostMapping("/api/books")
    public ResponseEntity saveBook(@Valid @RequestBody BookDTO bookDto) throws Exception {

        log.debug("saveBook()");

        // TODO ajouter securité
        Book bookSave = iBookService.register(bookDto);

//code 201, ajouter l'URI 
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookSave.getBookId())
                .toUri();

        return ResponseEntity.created(location)
                .body(bookSave);

    }

    @ApiOperation("Récupère un livre grâce à son ID à condition que celui-ci soit inscrit !")
    @GetMapping("/api/book/{id}")
    public ResponseEntity showBook(@PathVariable("id") int id) {

        log.debug("showBook() id: {}", id);

        return ResponseEntity.ok(iBookService.getBook(Long.valueOf(id)));

    }

    @ApiOperation("Récupère l'ensemble des livres de la base ou récupèrer une liste de livre a partir d'un mot clé sur le titre")
    @GetMapping("/api/books")
    public ResponseEntity showAllBooks(@RequestParam(defaultValue = " ") String title) throws Exception {

        // RequestBody DTO Search
        log.debug("showAllBooks()", title);

        List<Book> books = null;

        if (title.equals(" ")) {

            books = iBookService.getAllBooks();

            return ResponseEntity.ok(books);

        }

        books = iBookService.getBookByTitle(title);

        return ResponseEntity.ok(books);

    }

    @ApiOperation("Mettre à jour un livre à partir de son ID présent dans la base")
    @PutMapping("/api/book/{id}")
    public ResponseEntity updateBook(@PathVariable("id") int id, @Valid
            @RequestBody BookDTO bookDTO) throws Exception {

        log.debug("updateBook()");

        return ResponseEntity.ok().body(iBookService.edit(bookDTO));

    }

}
