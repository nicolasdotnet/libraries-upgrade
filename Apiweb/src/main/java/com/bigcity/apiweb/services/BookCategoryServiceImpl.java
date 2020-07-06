package com.bigcity.apiweb.services;

import com.bigcity.apiweb.dto.BookCategoryDTO;
import com.bigcity.apiweb.entity.BookCategory;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.services.interfaces.IBookCategoryService;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bigcity.apiweb.dao.IBookCategoryRepository;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class BookCategoryServiceImpl implements IBookCategoryService {

    private static final Logger log = LogManager.getLogger(BookCategoryServiceImpl.class);

    @Autowired
    private IBookCategoryRepository bookCategoryRepository;

    @Override
    public BookCategory register(BookCategoryDTO bookCategoryDTO) throws Exception {

        Optional<BookCategory> bookCategoryFind = bookCategoryRepository.findByLabel(bookCategoryDTO.getLabel());

        if (bookCategoryFind.isPresent()) {

            log.error("La categorie existe déjà !");

            throw new EntityAlreadyExistsException("La categorie existe déjà !");

        }

        BookCategory bookCategory = dtoToEntity(bookCategoryDTO);

        return bookCategoryRepository.save(bookCategory);
    }

    @Override
    public BookCategory edit(BookCategoryDTO bookCategoryDTO) throws Exception {

        Optional<BookCategory> bookCategoryFind = bookCategoryRepository.findByLabel(bookCategoryDTO.getLabel());

        if (!bookCategoryFind.isPresent()) {

            log.error("Modification Impossible ! la catégérie n'existe pas dans la base.");

            throw new EntityNotFoundException("La catégérie n'existe pas !");

        }

        bookCategoryFind.get().setLabel(bookCategoryDTO.getLabel());

        return bookCategoryRepository.saveAndFlush(bookCategoryFind.get());
    }

    @Override
    public List<BookCategory> getAllBookCategories() {
        return bookCategoryRepository.findAll();
    }

    @Override
    public BookCategory getBookCategory(Long id) {

        return bookCategoryRepository.findById(id).get();
    }

    @Override
    public BookCategory getBookCategoryByLabel(String label) {

        return bookCategoryRepository.findByLabel(label).get();
    }

    private BookCategory dtoToEntity(BookCategoryDTO bookCategoryDTO) {

        BookCategory bookCategory = new BookCategory();

        bookCategory.setLabel(bookCategoryDTO.getLabel());

        return bookCategory;
    }

}
