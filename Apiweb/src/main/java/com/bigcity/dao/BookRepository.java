/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dao;

import com.bigcity.entity.Book;
import com.bigcity.entity.BookCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author nicolasdotnet
 */
public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book>{

    List<Book> findAllByTitleContainingIgnoreCase(String bookTitle);

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByIsbnAndTitleAndAuthor(String isbn, String title, String author);

    List<Book> findAllByBookCategory(BookCategory bookCategoryFind);
    
}
