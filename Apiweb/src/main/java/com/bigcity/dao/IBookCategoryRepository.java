package com.bigcity.dao;

import com.bigcity.entity.BookCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookCategoryRepository extends JpaRepository<BookCategory,Long> {

    Optional<BookCategory> findByLabel(String label);
    
}
