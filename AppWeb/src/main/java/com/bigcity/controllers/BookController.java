package com.bigcity.controllers;

import com.bigcity.beans.Book;
import com.bigcity.services.interfaces.IBookService;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;

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
    @GetMapping("/book/{id}")
    public String showBook(@PathVariable("id") Long id, Model model) {

        log.debug("showBook() id: {}", id);

        Book bookFind = null;

        try {
            bookFind = iBookService.getBook(id);
        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());

            return "book/show";
        }

        model.addAttribute("bookFind", bookFind);

        return "book/show";

    }

    // show all book
    @GetMapping("/book/all")
    public String showBook(Model model) {
        
        Object bookFind = null;

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
    @GetMapping("/books")
    public String showAllBooks(Model model,
            @RequestParam(name = "page", defaultValue = "0") int p,
            @RequestParam(name = "size", defaultValue = "5") int s,
            @RequestParam("isbn") String isbn,
            @RequestParam("author") String author,
            @RequestParam("bookTitle") String bookTitle,
            @RequestParam("categoryName") String categoryName) {

        log.debug("showAllBooks()");

        Page<Book> bookPage = null;
        try {
            bookPage = iBookService.getAllBooksByCriteria(isbn, author, bookTitle, categoryName, p, s);
        } catch (URISyntaxException e) {

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
    @GetMapping("/Book/multisearch")
    public String multisearch(Model model) {

        log.debug("multisearch()");

        return "book/multisearch";
    }
}
