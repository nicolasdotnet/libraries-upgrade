package com.bigcity.apiweb.dao;

import com.bigcity.apiweb.entity.BookCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookCategoryRepository extends JpaRepository<BookCategory,Long> {

    Optional<BookCategory> findByLabel(String label);
    
}
