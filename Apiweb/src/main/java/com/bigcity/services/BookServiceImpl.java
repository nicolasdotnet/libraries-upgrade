package com.bigcity.services;

import com.bigcity.dto.BookDTO;
import com.bigcity.specifications.BookCriteria;
import com.bigcity.entity.Book;
import com.bigcity.entity.BookCategory;
import com.bigcity.exceptions.EntityAlreadyExistsException;
import com.bigcity.exceptions.EntityNotFoundException;
import com.bigcity.services.interfaces.IBookCategoryService;
import com.bigcity.services.interfaces.IBookService;
import com.bigcity.specifications.BookSpecification;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bigcity.dao.IBookRepository;

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

            throw new EntityNotFoundException("la catégorie n'existe pas !");
        }

        Book book = dtoToEntity(bookDTO, bookCategoryFind);

        return bookRepository.save(book);
    }

    @Override
    public Book edit(BookDTO bookDTO) throws Exception {

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
    public Optional<Book> getBookByIsbn(String isbn) {

        return bookRepository.findByIsbn(isbn);
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

            throw new EntityNotFoundException("la categorie n'existe pas !");
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

//    @Override
//    public Page<Book> getAllBooksByCriteria(BookCriteria bookCriteria, int page, int size) {
//
//        bookCriteria.setAuthor("".equals(bookCriteria.getAuthor()) ? null : bookCriteria.getAuthor());
//        bookCriteria.setBookTitle("".equals(bookCriteria.getBookTitle()) ? null : bookCriteria.getBookTitle());
//        bookCriteria.setCategoryName("".equals(bookCriteria.getCategoryName()) ? null : bookCriteria.getCategoryName());
//        bookCriteria.setIsbn("".equals(bookCriteria.getIsbn()) ? null : bookCriteria.getIsbn());
//
//        BookSpecification bookSpecification = new BookSpecification(bookCriteria);
//
//        return bookRepository.findAll(bookSpecification, PageRequest.of(page, size));
//
//    }
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

}
