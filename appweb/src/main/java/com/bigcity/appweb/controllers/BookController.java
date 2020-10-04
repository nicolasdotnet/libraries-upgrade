package com.bigcity.appweb.controllers;

import com.bigcity.appweb.beans.Book;
import com.bigcity.appweb.services.Tools;
import com.bigcity.appweb.services.interfaces.IBookService;

import java.net.URISyntaxException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@Transactional
public class BookController {

    private final Logger log = LogManager.getLogger(BookController.class);

    @Autowired
    private IBookService iBookService;

    @GetMapping("/user/books/{isbn}")
    public String showBook(@PathVariable("isbn") String isbn, Model model, Authentication authentication) throws URISyntaxException {

        log.debug("showBook() isbn: {}", isbn);

        Book bookFind = null;

        try {
            bookFind = iBookService.getBookByIsbn(isbn, authentication);
        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "book/show";
        }

        model.addAttribute("bookFind", bookFind);

        return "book/show";

    }

    @GetMapping("/user/book/all")
    public String showBooks(Model model, Authentication authentication) throws URISyntaxException {
        
        log.debug("showBooks()");

        List<Book> bookFind = null;

        try {
            bookFind = iBookService.getAllBooks(authentication);
        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "book/list";
        }

        model.addAttribute("books", bookFind);

        return "book/list";

    }

    @GetMapping("/user/books")
    public String showAllBooks(Model model,
            @RequestParam(name = "isbn", defaultValue = "") String isbn,
            @RequestParam(name = "author", defaultValue = "") String author,
            @RequestParam(name = "title", defaultValue = "") String bookTitle,
            @RequestParam(name = "categoryName", defaultValue = "") String categoryName,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size, Authentication authentication) throws URISyntaxException {

        log.debug("showAllBooks()");

        Page<Book> bookPage = null;

        try {
            bookPage = iBookService.getAllBooksByCriteria(isbn, author, bookTitle, categoryName, page - 1, size, authentication);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

        }

        int totalPages = bookPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("size", size);
        model.addAttribute("bookPage", bookPage);

        return "book/list";
    }

    @GetMapping("/user/book/multisearch")
    public String multisearch(Model model) {

        log.debug("multisearch()");

        return "book/multisearch";
    }
}
