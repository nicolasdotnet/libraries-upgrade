/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services;

import com.bigcity.apiweb.dao.IBookRepository;
import com.bigcity.apiweb.dto.BookDTO;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.BookCategory;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.services.interfaces.IBookCategoryService;
import java.util.Optional;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
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
public class BookServiceImplUTest {

    @InjectMocks
    private BookServiceImpl instance;

    @Mock
    private IBookRepository iBookRepository;

    @Mock
    private IBookCategoryService iBookCategoryService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of register method, of class BookServiceImpl.
     */
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookCategoryLabel("BookCategoryLabel");
        bookDTO.setAuthor("author");
        bookDTO.setBookTitle("title");
        bookDTO.setCopiesAvailable(2);
        bookDTO.setIsbn("0000");
        bookDTO.setNumberOfCopies(2);
        bookDTO.setSummary("summery");

        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setCopiesAvailable(2);
        book.setIsbn("0000");
        book.setNumberOfCopies(2);
        book.setSummary("summery");

        Optional<Book> op = Optional.empty();

        doReturn(op).when(iBookRepository).findByIsbnAndTitleAndAuthor(bookDTO.getIsbn(), bookDTO.getBookTitle(), bookDTO.getAuthor());

        BookCategory category = new BookCategory();
        doReturn(category).when(iBookCategoryService).getBookCategoryByLabel(bookDTO.getBookCategoryLabel());

        doReturn(book).when(iBookRepository).save(Mockito.any());

        Book r = instance.register(bookDTO);

        assertEquals("title", r.getTitle());

        Mockito.verify(iBookRepository).save(Mockito.any());

    }

    /**
     * Test of register method, of class BookServiceImpl.
     */
    @Test
    public void testRegisterWhenBookAlreadyExist() throws Exception {

        System.out.println("register");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookCategoryLabel("BookCategoryLabel");
        bookDTO.setAuthor("author");
        bookDTO.setBookTitle("title");
        bookDTO.setCopiesAvailable(2);
        bookDTO.setIsbn("0000");
        bookDTO.setNumberOfCopies(2);
        bookDTO.setSummary("summery");

        Book book = new Book();
        Optional<Book> op = Optional.of(book);

        doReturn(op).when(iBookRepository).findByIsbnAndTitleAndAuthor(bookDTO.getIsbn(), bookDTO.getBookTitle(), bookDTO.getAuthor());

        try {

            instance.register(bookDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityAlreadyExistsException);
            assertEquals(e.getMessage(), "Le livre existe déjà !");

        }

    }

    /**
     * Test of register method, of class BookServiceImpl.
     */
    @Test
    public void testRegisterWhenBookCategoryNotExist() throws Exception {

        System.out.println("register");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookCategoryLabel("BookCategoryLabel");
        bookDTO.setAuthor("author");
        bookDTO.setBookTitle("title");
        bookDTO.setCopiesAvailable(2);
        bookDTO.setIsbn("0000");
        bookDTO.setNumberOfCopies(2);
        bookDTO.setSummary("summery");

        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setCopiesAvailable(2);
        book.setIsbn("0000");
        book.setNumberOfCopies(2);
        book.setSummary("summery");

        Optional<Book> op = Optional.empty();

        doReturn(op).when(iBookRepository).findByIsbnAndTitleAndAuthor(bookDTO.getIsbn(), bookDTO.getBookTitle(), bookDTO.getAuthor());

        BookCategory category = null;
        doReturn(category).when(iBookCategoryService).getBookCategoryByLabel(bookDTO.getBookCategoryLabel());

        try {

            instance.register(bookDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "la catégorie n'existe pas !");

        }

    }

    /**
     * Test of edit method, of class BookServiceImpl.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookCategoryLabel("BookCategoryLabel");
        bookDTO.setAuthor("author");
        bookDTO.setBookTitle("title");
        bookDTO.setCopiesAvailable(2);
        bookDTO.setIsbn("0000");
        bookDTO.setNumberOfCopies(2);
        bookDTO.setSummary("summery");

        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setCopiesAvailable(2);
        book.setIsbn("0000");
        book.setNumberOfCopies(2);
        book.setSummary("summery");
        Optional<Book> op = Optional.of(book);

        int bookId = 1;

        doReturn(op).when(iBookRepository).findById(Long.valueOf(bookId));

        BookCategory category = new BookCategory();
        doReturn(category).when(iBookCategoryService).getBookCategoryByLabel(bookDTO.getBookCategoryLabel());

        doReturn(book).when(iBookRepository).saveAndFlush(Mockito.any());

        Book r = instance.edit(bookDTO, bookId);

        assertEquals("title", r.getTitle());

        Mockito.verify(iBookRepository).saveAndFlush(Mockito.any());

    }

    /**
     * Test of edit method, of class BookServiceImpl.
     */
    @Test
    public void testEditWhenBookIsNotExit() throws Exception {
        System.out.println("edit");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn("0000");

        Optional<Book> op = Optional.empty();

        int bookId = 1;

        doReturn(op).when(iBookRepository).findById(Long.valueOf(bookId));

        try {

            instance.edit(bookDTO, bookId);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "Le livre n'existe pas !");

        }

    }

    /**
     * Test of edit method, of class BookServiceImpl.
     */
    @Test
    public void testEditWhenBookCategoryIsNotExit() throws Exception {
        System.out.println("edit");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookCategoryLabel("BookCategoryLabel");

        Optional<Book> op = Optional.of(new Book());
        int bookId = 1;

        doReturn(op).when(iBookRepository).findById(Long.valueOf(bookId));

        BookCategory category = null;
        doReturn(category).when(iBookCategoryService).getBookCategoryByLabel(bookDTO.getBookCategoryLabel());

        try {

            instance.edit(bookDTO, bookId);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "la catégorie n'existe pas !");

        }

    }

    /**
     * Test of updateBook method, of class BookServiceImpl.
     */
    @Test
    public void testUpdateBookWhenBookIsNotExit() throws Exception {
        System.out.println("updateBook");

        Book book = new Book();
        book.setIsbn("1234");

        Optional<Book> op = Optional.empty();

        doReturn(op).when(iBookRepository).findByIsbn(book.getIsbn());

        try {

            instance.updateBook(book);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "Le livre n'existe pas !");

        }

    }

    /**
     * Test of updateBook method, of class BookServiceImpl.
     */
    @Test
    public void testUpdateBook() throws Exception {
        System.out.println("updateBook");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("author");
        bookDTO.setBookTitle("title");
        bookDTO.setCopiesAvailable(2);
        bookDTO.setIsbn("0000");
        bookDTO.setNumberOfCopies(2);
        bookDTO.setSummary("summery");

        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setCopiesAvailable(2);
        book.setIsbn("0000");
        book.setNumberOfCopies(2);
        book.setSummary("summery");
        Optional<Book> op = Optional.of(book);

        doReturn(op).when(iBookRepository).findByIsbn(bookDTO.getIsbn());
        doReturn(book).when(iBookRepository).saveAndFlush(Mockito.any());

        Book r = instance.updateBook(book);
        assertEquals("title", r.getTitle());
        Mockito.verify(iBookRepository).saveAndFlush(Mockito.any());

    }

    /**
     * Test of getBook method, of class BookServiceImpl.
     */
    @Test
    public void testGetBookWhenBookIsNotExit() throws Exception {

        System.out.println("getBook");

        Long id = 00L;

        Optional<Book> op = Optional.empty();

        doReturn(op).when(iBookRepository).findById(id);

        try {

            instance.getBook(id);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "le livre n'existe pas !");

        }

    }

    /**
     * Test of getBookByIsbn method, of class BookServiceImpl.
     */
    @Test
    public void testGetBookByIsbnWhenBookIsNotExit() throws Exception {

        System.out.println("getBookByIsbn");

        String isbn = "0000";

        Optional<Book> op = Optional.empty();

        doReturn(op).when(iBookRepository).findByIsbn(isbn);

        try {

            instance.getBookByIsbn(isbn);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "le livre n'existe pas !");

        }

    }

    /**
     * Test of getBook method, of class BookServiceImpl.
     */
    @Test
    public void testGetBook() throws EntityNotFoundException {

        System.out.println("getBook");

        Long id = 00L;

        Book book = new Book();
        book.setTitle("title");

        Optional<Book> op = Optional.of(book);

        doReturn(op).when(iBookRepository).findById(id);

        Book r = instance.getBook(id);

        assertEquals("title", r.getTitle());

    }

    /**
     * Test of getBookByIsbn method, of class BookServiceImpl.
     */
    @Test
    public void testGetBookByIsbn() throws Exception {

        System.out.println("getBookByIsbn");

        String isbn = "0000";

        Book book = new Book();
        book.setTitle("title");
        Optional<Book> op = Optional.of(book);

        doReturn(op).when(iBookRepository).findByIsbn(isbn);

        Optional<Book> r = instance.getBookByIsbn(isbn);

        assertEquals("title", r.get().getTitle());

    }

}
