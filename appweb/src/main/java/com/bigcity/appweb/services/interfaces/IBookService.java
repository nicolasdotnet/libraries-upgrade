package com.bigcity.appweb.services.interfaces;

import com.bigcity.appweb.beans.Book;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookService {

    /**
     * method to get all book
     *
     * @param authentication
     * @return the book list
     * @throws java.net.URISyntaxException
     */
    List<Book> getAllBooks(Authentication authentication)throws URISyntaxException, RestClientException ;

    /**
     * method to get a book
     *
     * @param id
     * @param authentication
     * @return book object find
     * @throws java.net.URISyntaxException
     */
    Book getBook(Long id, Authentication authentication) throws URISyntaxException, RestClientException ;

    /**
     * method to get a book by isbn
     *
     * @param isbn
     * @param authentication
     * @return book object find
     * @throws java.net.URISyntaxException
     */
    Book getBookByIsbn(String isbn, Authentication authentication) throws URISyntaxException, RestClientException ;

    /**
     * method to get all books by criteria
     *
     * @param isbn
     * @param author
     * @param bookTitle
     * @param categoryName
     * @param p number page
     * @param s size page
     * @param authentication
     * @return the book pages
     * @throws java.net.URISyntaxException
     */
    Page<Book> getAllBooksByCriteria(String isbn, String author, String bookTitle, String categoryName, int p, int s, Authentication authentication) throws URISyntaxException, RestClientException ;

}
