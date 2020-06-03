/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dao;

import com.bigcity.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nicolasdotnet
 */
public interface BookRepository extends JpaRepository<Book,Long>{

    List<Book> findByTitleContainingIgnoreCase(String bookTitle);

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByIsbnAndTitleAndAuthor(String isbn, String title, String author);
    
}
