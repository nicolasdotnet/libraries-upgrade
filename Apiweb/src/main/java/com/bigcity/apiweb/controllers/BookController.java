package com.bigcity.apiweb.controllers;

import com.bigcity.apiweb.dto.BookDTO;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.services.interfaces.IBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "livre créé", response = BookDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookDTO.class),
        @ApiResponse(code = 409, message = "le livre existe déjà dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PostMapping("/api/librarian/books")
    public ResponseEntity<Book> saveBook(@Valid @RequestBody BookDTO bookDto) throws Exception {

        log.debug("saveBook()");

        Book bookSave = iBookService.register(bookDto);
 
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookSave.getBookId())
                .toUri();

        return ResponseEntity.created(location)
                .body(bookSave);

    }

    @ApiOperation("Récupère un livre grâce à son ISBN")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookDTO.class),
        @ApiResponse(code = 404, message = "le livre n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/books/{isbn}")
    public ResponseEntity<Book> showBook(@PathVariable("isbn") String isbn) {

        log.debug("showBook() isbn: {}", isbn);

        return ResponseEntity.ok(iBookService.getBookByIsbn(isbn).get());

    }

    @ApiOperation("Mettre à jour un livre à partir de son ID présent dans la base")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookDTO.class),
        @ApiResponse(code = 404, message = "le livre n'existe pas dans la base"),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/librarian/books/{id}")
    public ResponseEntity updateBook(@PathVariable("id") int id, @Valid
            @RequestBody BookDTO bookDTO) throws Exception {

        log.debug("updateBook()");

        return ResponseEntity.ok().body(iBookService.edit(bookDTO));

    }

    @ApiOperation("Récupère l'ensemble des livres de la base en fonction du titre, de l'auteur et du numero ISBN")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping(value = "/api/user/books")
    public ResponseEntity<Page<Book>> showAllBooksByCriteria(
            @RequestParam(name = "isbn") String isbn,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "bookTitle") String bookTitle,
            @RequestParam(name = "categoryName") String categoryName,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) throws Exception {

        log.debug("showAllBooksByCriteria");

        return ResponseEntity.ok().body(iBookService.getAllBooksByCriteria(isbn, author, bookTitle, categoryName, page, size));
    }
}
