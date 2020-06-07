/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.specifications;

import com.bigcity.entity.Book;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author nicolasdotnet
 */
public class BookSpecification {

    public static Specification<Book> getBookByTitle(String title) {

        return ((root, query, criteriaBuilder) -> {

            String param = "%" + title + "%";

            return criteriaBuilder.like(root.get("title"), param);
        });

    }

    public static Specification<Book> getBookByTitleIsNull() {

        return ((root, query, criteriaBuilder) -> {

            return criteriaBuilder.isNull(root.get("title"));
        });

    }

    public static Specification<Book> getBookByAuthor(String author) {

        return ((root, query, criteriaBuilder) -> {

            String param = "%" + author + "%";

            return criteriaBuilder.like(root.get("author"), param);
        });
    }

    public static Specification<Book> getBookByAuthorIsNull() {

        return ((root, query, criteriaBuilder) -> {

            return criteriaBuilder.isNull(root.get("author"));
        });
    }

    public static Specification<Book> getBookByIsbn(String isbn) {

        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("isbn"), isbn);
        });

    }

    public static Specification<Book> getBookByIsbnIsNull() {

        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.isNull(root.get("isbn"));
        });

    }

    public static Specification<Book> getBookByTitleAndAuthorAndIsbn(String title,
            String author, String isbn) {
        return Specification.where(getBookByAuthorIsNull().or(getBookByTitle(title)))
                .and(getBookByAuthorIsNull().or(getBookByAuthor(author))).and(getBookByIsbnIsNull().or(getBookByIsbn(isbn)));
    }

}
