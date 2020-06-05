/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.services.interfaces;

import com.bigcity.dto.BookCategoryDTO;
import com.bigcity.entity.BookCategory;
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
