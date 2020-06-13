package com.bigcity.services.interfaces;

import com.bigcity.dto.BookDTO;
import com.bigcity.specifications.BookCriteria;
import com.bigcity.entity.Book;
import com.bigcity.entity.BookCategory;
import java.util.List;
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
     * method to get all book
     *
     * @return the book list
     */
    List<Book> getAllBooks();

    /**
     * method to get a book
     *
     * @param id
     * @return book object find
     */
    Book getBook(Long id);

    /**
     * method to get a book list by title
     *
     * @param title
     * @return book object list find by title
     */
    List<Book> getBookByTitle(String title);

    /**
     * method to get a book list by book category
     *
     * @param bookCategory
     * @return book object list find by title
     * @throws java.lang.Exception
     */
    List<Book> getBookByBookCategory(BookCategory bookCategory) throws Exception;

    /**
     * method to get all books by criteria
     *
     * @param bookSearchCriteria
     * @param page
     * @param size
     * @return the book list
     */
    Page<Book> getAllBooksByCriteria(BookCriteria bookSearchCriteria, int page, int size);

}
