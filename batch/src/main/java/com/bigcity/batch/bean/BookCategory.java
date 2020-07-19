package com.bigcity.batch.bean;

import java.io.Serializable;
import java.util.Collection;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 * 
 * BookingCategory is the class to which book can belong.
 * 
 */
@Component
public class BookCategory implements Serializable {

    private Long bookCategoryId;

    private String label;

    private Collection<Book> books;

    public BookCategory() {
    }

    public Long getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(Long bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookCategory{" + "bookCategoryId=" + bookCategoryId + ", label=" + label + '}';
    }

}
