/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dao;

import com.bigcity.entity.BookCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nicolasdotnet
 */
public interface BookCategoryRepository extends JpaRepository<BookCategory,Long> {

    Optional<BookCategory> findByLabel(String label);
    
}
