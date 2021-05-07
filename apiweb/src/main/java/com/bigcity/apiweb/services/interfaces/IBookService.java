package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.BookDTO;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.domain.Page;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookService {

    /**
     * method to register a book
     *
     * @param bookDTO
     * @return book object saved
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Book register(BookDTO bookDTO) throws EntityAlreadyExistsException, EntityNotFoundException;

    /**
     * method to modify a book
     *
     * @param bookDTO
     * @param bookId
     * @return book object modified
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Book edit(BookDTO bookDTO, int bookId) throws EntityNotFoundException;

    /**
     * method to get a book
     *
     * @param id
     * @return book object find
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Book getBook(Long id) throws EntityNotFoundException;

    /**
     * method to get a book by isbn
     *
     * @param isbn
     * @return optional book object find
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Optional<Book> getBookByIsbn(String isbn) throws EntityNotFoundException;

    /**
     * method to get all books by criteria
     *
     * @param isbn
     * @param author
     * @param bookTitle
     * @param categoryName
     * @param page
     * @param size
     * @return the pages books
     */
    Page<Book> getAllBooksByCriteria(String isbn, String author, String bookTitle, String categoryName, int page, int size);

    /**
     * method to update a book
     *
     * @param book
     * @return the book update
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Book updateBook(Book book) throws EntityNotFoundException;

}
