/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.specifications;

import com.bigcity.entity.Book;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author nicolasdotnet
 * 
 * This class is used to search Books specifying the criteria
 * 
 */
public class BookSpecification implements Specification<Book> {

    private BookCriteria filter;

    public BookSpecification(BookCriteria filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getAuthor() != null) {
            
            String param = "%" + filter.getAuthor() + "%";
            
            predicate.getExpressions().add(criteriaBuilder.like(root.get("author"), param));
        }

        if (filter.getTitle() != null) {
            
            String param = "%" + filter.getTitle() + "%";
            
            predicate.getExpressions().add(criteriaBuilder.like(root.get("title"), param));
        }

        if (filter.getIsbn() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("isbn"), filter.getIsbn()));
        }

        if (filter.getCategoryName() != null) {
            predicate.getExpressions().add(criteriaBuilder.like(root.join("bookCategory").get("label"), filter.getCategoryName()));
        }

        return criteriaBuilder.and(predicate);

    }

}
