package com.bigcity.apiweb.services;

import com.bigcity.apiweb.dto.BookDTO;
import com.bigcity.apiweb.specifications.BookCriteria;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.BookCategory;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.services.interfaces.IBookCategoryService;
import com.bigcity.apiweb.services.interfaces.IBookService;
import com.bigcity.apiweb.specifications.BookSpecification;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bigcity.apiweb.dao.IBookRepository;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class BookServiceImpl implements IBookService {

    private static final Logger log = LogManager.getLogger(BookServiceImpl.class);

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IBookCategoryService iBookCategoryService;

    @Override
    public Book register(BookDTO bookDTO) throws EntityAlreadyExistsException, EntityNotFoundException {

        Optional<Book> bookFind = bookRepository.findByIsbnAndTitleAndAuthor(bookDTO.getIsbn(), bookDTO.getBookTitle(), bookDTO.getAuthor());

        if (bookFind.isPresent()) {

            log.error("Le livre existe déjà !");

            throw new EntityAlreadyExistsException("Le livre existe déjà !");

        }

        BookCategory bookCategoryFind = iBookCategoryService.getBookCategoryByLabel(bookDTO.getBookCategoryLabel());

        if (bookCategoryFind == null) {

            log.error("la catégorie n'existe pas dans la base.");

            throw new EntityNotFoundException("la catégorie n'existe pas !");
        }

        Book book = dtoToEntity(bookDTO, bookCategoryFind);

        return bookRepository.save(book);
    }

    @Override
    public Book edit(BookDTO bookDTO) throws EntityNotFoundException {

        Optional<Book> bookFind = bookRepository.findByIsbn(bookDTO.getIsbn());

        if (!bookFind.isPresent()) {

            log.error("Modification Impossible ! le livre n'existe pas dans la base.");

            throw new EntityNotFoundException("Le livre n'existe pas !");

        }

        BookCategory bookCategoryFind = iBookCategoryService.getBookCategoryByLabel(bookDTO.getBookCategoryLabel());

        if (bookCategoryFind == null) {

            log.error("la catégorie n'existe pas dans la base.");

            throw new EntityNotFoundException("la catégorie n'existe pas !");
        }

        bookFind.get().setIsbn(bookDTO.getIsbn());
        bookFind.get().setAuthor(bookDTO.getAuthor());
        bookFind.get().setTitle(bookDTO.getBookTitle());
        bookFind.get().setCopiesAvailable(bookDTO.getCopiesAvailable());
        bookFind.get().setBookCategory(bookCategoryFind);
        bookFind.get().setSummary(bookDTO.getSummary());

        return bookRepository.saveAndFlush(bookFind.get());
    }

    @Override
    public Book getBook(Long id) throws EntityNotFoundException {

        Optional<Book>  bookFind = bookRepository.findById(id);

        if (!bookFind.isPresent()) {

            log.error("le livre n'existe pas dans la base.");

            throw new EntityNotFoundException("le livre n'existe pas !");

        }

        return bookFind.get();
    }

    @Override
    public Book getBookByIsbn(String isbn) throws EntityNotFoundException {

        Optional<Book> bookFind = bookRepository.findByIsbn(isbn);

        if (!bookFind.isPresent()) {

            log.error("le livre n'existe pas dans la base.");

            throw new EntityNotFoundException("le livre n'existe pas !");

        }

        return bookFind.get();
    }

    @Override
    public Page<Book> getAllBooksByCriteria(String isbn, String author, String bookTitle, String categoryName, int page, int size) {

        BookCriteria bookCriteria = new BookCriteria();

        bookCriteria.setAuthor("".equals(author) ? null : author);
        bookCriteria.setTitle("".equals(bookTitle) ? null : bookTitle);
        bookCriteria.setCategoryName("".equals(categoryName) ? null : categoryName);
        bookCriteria.setIsbn("".equals(isbn) ? null : isbn);

        BookSpecification bookSpecification = new BookSpecification(bookCriteria);

        return bookRepository.findAll(bookSpecification, PageRequest.of(page, size));
    }

    public Book dtoToEntity(BookDTO bookDTO, BookCategory bookCategoryFind) {

        Book book = new Book();

        book.setIsbn(bookDTO.getIsbn());
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getBookTitle());
        book.setCopiesAvailable(bookDTO.getCopiesAvailable());
        book.setBookCategory(bookCategoryFind);
        book.setSummary(bookDTO.getSummary());

        return book;

    }
}
