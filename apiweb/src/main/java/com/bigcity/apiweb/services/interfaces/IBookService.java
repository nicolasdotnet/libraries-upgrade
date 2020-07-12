package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.BookDTO;
import com.bigcity.apiweb.entity.Book;
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
     * @throws Exception
     */
    Book register(BookDTO bookDTO) throws Exception;

    /**
     * method to modify a book
     *
     * @param bookDTO
     * @return book object modified
     * @throws Exception
     */
    Book edit(BookDTO bookDTO) throws Exception;

    /**
     * method to get a book
     *
     * @param id
     * @return book object find
     */
    Book getBook(Long id);

    /**
     * method to get a book by isbn
     *
     * @param isbn
     * @return optional book object find
     */
    Optional<Book> getBookByIsbn(String isbn);

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

}
