package com.bigcity.apiweb.dao;

import com.bigcity.apiweb.entity.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book>{

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByIsbnAndTitleAndAuthor(String isbn, String title, String author);
    
}
