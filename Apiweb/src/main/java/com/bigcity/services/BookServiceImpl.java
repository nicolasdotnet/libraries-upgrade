/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.services;

import com.bigcity.dao.BookRepository;
import com.bigcity.dto.BookDTO;
import com.bigcity.dto.BookSearchDTO;
import com.bigcity.entity.Book;
import com.bigcity.entity.BookCategory;
import com.bigcity.exceptions.EntityAlreadyExistsException;
import com.bigcity.exceptions.EntityNoFoundException;
import com.bigcity.services.interfaces.IBookCategoryService;
import com.bigcity.services.interfaces.IBookService;
import com.bigcity.specifications.BookSpecification;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class BookServiceImpl implements IBookService {

    private static final Logger log = LogManager.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IBookCategoryService iBookCategoryService;

    @Override
    public Book register(BookDTO bookDTO) throws Exception {

        Optional<Book> bookFind = bookRepository.findByIsbnAndTitleAndAuthor(bookDTO.getIsbn(), bookDTO.getBookTitle(), bookDTO.getAuthor());

        if (bookFind.isPresent()) {

            log.error("Le livre existe déjà !");

            throw new EntityAlreadyExistsException("Le livre existe déjà !");

        }

        System.out.println("bc = " + bookDTO.getBookCategoryLabel());

        BookCategory bookCategoryFind = iBookCategoryService.getBookCategoryByLabel(bookDTO.getBookCategoryLabel());

        if (bookCategoryFind == null) {

            log.error("la catégorie n'existe pas dans la base.");

            throw new EntityNoFoundException("la catégorie n'existe pas !");
        }

        Book book = dtoToEntity(bookDTO, bookCategoryFind);

        return bookRepository.save(book);
    }

    @Override
    public Book edit(BookDTO bookDTO) throws Exception {

        Optional<Book> bookFind = bookRepository.findByIsbn(bookDTO.getIsbn());

        if (!bookFind.isPresent()) {

            log.error("Modification Impossible ! le livre n'existe pas dans la base.");

            throw new EntityNoFoundException("Le livre n'existe pas !");

        }

        BookCategory bookCategoryFind = iBookCategoryService.getBookCategoryByLabel(bookDTO.getBookCategoryLabel());

        if (bookCategoryFind == null) {

            log.error("la catégorie n'existe pas dans la base.");

            throw new EntityNoFoundException("la catégorie n'existe pas !");
        }

        bookFind.get().setIsbn(bookDTO.getIsbn());
        bookFind.get().setAuthor(bookDTO.getAuthor());
        bookFind.get().setTitle(bookDTO.getBookTitle());
        bookFind.get().setCopiesAvailable(bookDTO.getCopiesAvailable());
        bookFind.get().setBookCategory(bookCategoryFind);

        return bookRepository.saveAndFlush(bookFind.get());
    }

    @Override
    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) {

        return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> getBookByTitle(String title) {

        return bookRepository.findAllByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> getBookByBookCategory(BookCategory bookCategory) throws Exception {

        BookCategory bookCategoryFind = iBookCategoryService.getBookCategory(bookCategory.getBookCategoryId());

        if (bookCategoryFind == null) {

            log.error("la catégorie n'existe pas dans la base.");

            throw new EntityNoFoundException("la categorie n'existe pas !");
        }

        return bookRepository.findAllByBookCategory(bookCategoryFind);
    }

    public Book dtoToEntity(BookDTO bookDTO, BookCategory bookCategoryFind) {

        Book book = new Book();

        book.setIsbn(bookDTO.getIsbn());
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getBookTitle());
        book.setCopiesAvailable(bookDTO.getCopiesAvailable());
        book.setBookCategory(bookCategoryFind);

        return book;

    }

    @Override
    public List<Book> getAllBooksV2(BookSearchDTO bsdto) {

        if (bsdto.getAuthor().equals("")) {

            bsdto.setAuthor(null);

        }

        if (bsdto.getBookTitle().equals("")) {

            bsdto.setBookTitle(null);

        }

        if (bsdto.getIsbn().equals("")) {

            bsdto.setIsbn(null);

        }

        System.out.println("sous requete");

        List<Book> a = bookRepository.findAll(BookSpecification.getBookByAuthor(bsdto.getAuthor()));

        if (a.isEmpty()) {

            System.out.println("<<<<<<<<<<<<<<<<pas de resultat sous requete");

        }

        for (Book book : a) {

            System.out.println("<<<<<<<<<<RESULTAT sous requete : " + book.toString());
        }

        return bookRepository.findAll(BookSpecification.getBookByTitleAndAuthorAndIsbn(bsdto.getBookTitle(), bsdto.getAuthor(), bsdto.getIsbn()));
    }

}
