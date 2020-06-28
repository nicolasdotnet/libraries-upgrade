package com.bigcity.services.interfaces;

import com.bigcity.beans.Book;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookService {

    /**
     * method to get all book
     *
     * @param principal
     * @return the book list
     * @throws java.net.URISyntaxException
     */
    List<Book> getAllBooks() throws Exception;

    /**
     * method to get a book
     *
     * @param id
     * @return book object find
     * @throws java.net.URISyntaxException
     */
    Book getBook(Long id) throws Exception;

    /**
     * method to get a book by isbn
     *
     * @param isbn
     * @return book object find
     * @throws java.net.URISyntaxException
     */
    Book getBookByIsbn(String isbn) throws Exception;

    /**
     * method to get all books by criteria
     *
     * @param isbn
     * @param author
     * @param bookTitle
     * @param categoryName
     * @param p number page
     * @param s size page
     * @return the book pages
     */
    Page<Book> getAllBooksByCriteria(String isbn, String author, String bookTitle, String categoryName, int p, int s) throws Exception;

}
