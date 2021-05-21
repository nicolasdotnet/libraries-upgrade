package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.BookCategoryDTO;
import com.bigcity.apiweb.entity.BookCategory;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookCategoryService {

    /**
     * method to register a bookCategory
     *
     * @param bookCategoryDTO
     * @return bookCategory object saved
     * @throws Exception
     */
    BookCategory register(BookCategoryDTO bookCategoryDTO) throws Exception;

    /**
     * method to modify a bookCategory
     *
     * @param bookCategoryDTO
     * @return bookCategory object modified
     * @throws Exception
     */
    BookCategory edit(BookCategoryDTO bookCategoryDTO) throws Exception;

    /**
     * method to get all book Categories
     *
     * @return the bookCategory list
     */
    List<BookCategory> getAllBookCategories();

    /**
     * method to get a bookCategory
     *
     * @param id
     * @return bookCategory Optional object find
     */
    Optional<BookCategory> getBookCategory(Long id);

    /**
     * method to get a bookCategory by label category
     *
     * @param label
     * @return bookCategory Optional object find by label
     */
    Optional<BookCategory> getBookCategoryByLabel(String label);

}
