/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.services;

import com.bigcity.exceptions.BooksNoFoundException;
import com.bigcity.dao.BookRepository;
import com.bigcity.dto.BookDTO;
import com.bigcity.entity.Book;
import com.bigcity.exceptions.BookNoFoundException;
import com.bigcity.services.interfaces.IBookService;
import java.util.ArrayList;
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

    @Override
    public Book register(BookDTO bookDTO) throws Exception {

        Optional<Book> bookFind = bookRepository.findByIsbnAndTitleAndAuthor(bookDTO.getIsbn(), bookDTO.getBookTitle(), bookDTO.getAuthor());

        if (bookFind.isPresent()) {

            log.error("Le livre existe déjà !");

            throw new Exception("Le livre existe déjà !");

        }

        Book book = dtoToEntity(bookDTO);

        return bookRepository.save(book);
    }

    @Override
    public Book edit(BookDTO bookDTO) throws Exception {

        Optional<Book> bookFind = bookRepository.findByIsbn(bookDTO.getIsbn());

        if (!bookFind.isPresent()) {

            log.error("Modification Impossible ! le livre n'existe pas dans la base.");

            throw new Exception("Le livre n'existe pas !");

        }
       

        bookFind.get().setIsbn(bookDTO.getIsbn());
        bookFind.get().setAuthor(bookDTO.getAuthor());
        bookFind.get().setTitle(bookDTO.getBookTitle());
        bookFind.get().setCopiesAvailable(bookDTO.getCopiesAvailable());
        
        return bookRepository.saveAndFlush(bookFind.get());
    }

    @Override
    public List<Book> getAllBooks() throws Exception {

        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) throws Exception {

        Optional<Book> bookFind = bookRepository.findById(id);

        if (!bookFind.isPresent()) {

            log.error("Le livre n'existe pas dans la base.");

            throw new BookNoFoundException("Le livre n'existe pas !");

        }

        return bookFind.get();
    }

    @Override
    public List<Book> getBookByTitle(String title) throws Exception {
        
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public BookDTO entityToDto(Book book) {

        BookDTO bookDTO = new BookDTO();

        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setBookTitle(book.getTitle());
        bookDTO.setCopiesAvailable(book.getCopiesAvailable());

        return bookDTO;

    }

    public Book dtoToEntity(BookDTO bookDTO) {

        Book book = new Book();

        book.setIsbn(bookDTO.getIsbn());
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getBookTitle());
        book.setCopiesAvailable(bookDTO.getCopiesAvailable());

        return book;

    }

}
