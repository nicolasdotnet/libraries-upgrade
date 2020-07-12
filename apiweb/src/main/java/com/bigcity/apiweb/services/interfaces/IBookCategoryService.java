package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.BookCategoryDTO;
import com.bigcity.apiweb.entity.BookCategory;
import java.util.List;

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
     * @return bookCategory object find
     */
    BookCategory getBookCategory(Long id);

    /**
     * method to get a bookCategory by label category
     *
     * @param label
     * @return bookCategory object list find by label
     */
    BookCategory getBookCategoryByLabel(String label);

}
