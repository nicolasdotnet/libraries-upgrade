package com.bigcity.controllers;

import com.bigcity.beans.Book;
import com.bigcity.services.interfaces.IBookService;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Transactional
public class BookController {

    private final Logger log = LogManager.getLogger(BookController.class);

    @Autowired
    private IBookService iBookService;

    // show book
    @GetMapping("/user/books/{isbn}")
    public String showBook(@PathVariable("isbn") String isbn, Model model) {

        log.debug("showBook() isbn: {}", isbn);

        Book bookFind = null;

        try {
            bookFind = iBookService.getBookByIsbn(isbn);
        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());

            return "book/show";
        }

        model.addAttribute("bookFind", bookFind);

        return "book/show";

    }

    // show all book
    @GetMapping("/user/book/all")
    public String showBooks(Model model) {

        List<Book> bookFind = null;

        try {
            bookFind = iBookService.getAllBooks();
        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());

            return "book/list";
        }

        model.addAttribute("books", bookFind);

        return "book/list";

    }

    // book list page
    @GetMapping("/user/books")
    public String showAllBooks(Model model,
            @RequestParam(name = "isbn", defaultValue = "") String isbn,
            @RequestParam(name = "author", defaultValue = "") String author,
            @RequestParam(name = "title", defaultValue = "") String bookTitle,
            @RequestParam(name = "categoryName", defaultValue = "") String categoryName,
            @RequestParam(name = "page", defaultValue = "0") int p,
            @RequestParam(name = "size", defaultValue = "5") int s) {

        log.debug("showAllBooks()");

        Page<Book> bookPage = null;
        
        try {
            bookPage = iBookService.getAllBooksByCriteria(isbn, author, bookTitle, categoryName, p, s);
        
        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());

        }

        int numberPage = bookPage.getTotalPages();
        
        List<Book> books = bookPage.getContent();
        int[] pages = new int[numberPage];

        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pageCourante", p);
        model.addAttribute("books", books);

        return "book/list";
    }

    // multisearch book
    @GetMapping("/user/book/multisearch")
    public String multisearch(Model model) {

        log.debug("multisearch()");

        return "book/multisearch";
    }
}
