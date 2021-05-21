/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services;

import com.bigcity.apiweb.dao.IBookCategoryRepository;
import com.bigcity.apiweb.dto.BookCategoryDTO;
import com.bigcity.apiweb.entity.BookCategory;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author nicolasdotnet
 */
@ExtendWith(MockitoExtension.class)
public class BookCategoryServiceImplTest {

    @InjectMocks
    private BookCategoryServiceImpl instance;

    @Mock
    private IBookCategoryRepository iBookCategoryRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of register method, of class BookCategoryServiceImpl.
     */
    @Test
    public void testRegisterWhenBookAlreadyExist() throws Exception {
        System.out.println("register");
        BookCategoryDTO bookCategoryDTO = new BookCategoryDTO();
        bookCategoryDTO.setLabel("Polar");

        BookCategory expResult = new BookCategory();
        Optional<BookCategory> op = Optional.of(expResult);

        doReturn(op).when(iBookCategoryRepository).findByLabel(bookCategoryDTO.getLabel());

        try {

            instance.register(bookCategoryDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityAlreadyExistsException);
            assertEquals(e.getMessage(), "La categorie existe déjà !");

        }

    }

    /**
     * Test of register method, of class BookCategoryServiceImpl.
     */
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");

        BookCategoryDTO bookCategoryDTO = new BookCategoryDTO();
        bookCategoryDTO.setLabel("Test");

        BookCategory bookCategory = null;
        Optional<BookCategory> op = Optional.empty();

        BookCategory expResult = new BookCategory();
        expResult.setLabel("Test");

        doReturn(op).when(iBookCategoryRepository).findByLabel(bookCategoryDTO.getLabel());
        doReturn(expResult).when(iBookCategoryRepository).save(Mockito.any());

        BookCategory r = instance.register(bookCategoryDTO);

        assertEquals(expResult.getLabel(), r.getLabel());

        Mockito.verify(iBookCategoryRepository).save(Mockito.any());

    }

    /**
     * Test of edit method, of class BookCategoryServiceImpl.
     */
    @Test
    public void testEditWhenBookCategoryIsNotExit() throws Exception {
        System.out.println("edit");

        BookCategoryDTO bookCategoryDTO = new BookCategoryDTO();
        bookCategoryDTO.setLabel("Test");

        Optional<BookCategory> op = Optional.empty();

        doReturn(op).when(iBookCategoryRepository).findByLabel(bookCategoryDTO.getLabel());

        try {

            instance.edit(bookCategoryDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "La catégérie n'existe pas !");

        }

    }

    /**
     * Test of edit method, of class BookCategoryServiceImpl.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");

        BookCategoryDTO bookCategoryDTO = new BookCategoryDTO();
        bookCategoryDTO.setLabel("Test");

        BookCategory bookCategory = new BookCategory();
        bookCategory.setLabel("Test");

        Optional<BookCategory> op = Optional.of(bookCategory);

        doReturn(op).when(iBookCategoryRepository).findByLabel(bookCategoryDTO.getLabel());
        doReturn(bookCategory).when(iBookCategoryRepository).saveAndFlush(Mockito.any());

        BookCategory r = instance.edit(bookCategoryDTO);

        assertEquals(bookCategory.getLabel(), r.getLabel());

        Mockito.verify(iBookCategoryRepository).saveAndFlush(Mockito.any());

    }

    /**
     * Test of getAllBookCategories method, of class BookCategoryServiceImpl.
     */
    @Test
    public void testGetAllBookCategories() {
        System.out.println("getAllBookCategories");

        List<BookCategory> expResult = new ArrayList<>();
        expResult.add(new BookCategory());

        doReturn(expResult).when(iBookCategoryRepository).findAll();

        List<BookCategory> result = instance.getAllBookCategories();
        assertEquals(expResult.get(0), result.get(0));

    }

    /**
     * Test of getBookCategory method, of class BookCategoryServiceImpl.
     */
    @Test
    public void testGetBookCategory() {
        System.out.println("getBookCategory");

        Long id = 1L;

        BookCategory expResult = new BookCategory();
        expResult.setLabel("Test");
        Optional<BookCategory> op = Optional.of(expResult);

        doReturn(op).when(iBookCategoryRepository).findById(id);

        BookCategory result = instance.getBookCategory(id).get();
        assertEquals(expResult.getLabel(), result.getLabel());

        Mockito.verify(iBookCategoryRepository).findById(id);

    }

    /**
     * Test of getBookCategoryByLabel method, of class BookCategoryServiceImpl.
     */
    @Test
    public void testGetBookCategoryByLabel() {
        System.out.println("getBookCategoryByLabel");

        String label = "String";

        BookCategory expResult = new BookCategory();
        expResult.setLabel("String");
        Optional<BookCategory> op = Optional.of(expResult);

        doReturn(op).when(iBookCategoryRepository).findByLabel(label);

        BookCategory result = instance.getBookCategoryByLabel(label).get();
        assertEquals(expResult.getLabel(), result.getLabel());

        Mockito.verify(iBookCategoryRepository).findByLabel(label);

    }

}
