package com.bigcity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author nicolasdotnet
 * 
 * BookingCategory is the class to which book can belong.
 * 
 */
@Entity
public class BookCategory implements Serializable {

    @Id
    @GeneratedValue
    private Long bookCategoryId;

    @Column(length = 100, nullable = false)
    private String label;

    @OneToMany(mappedBy = "bookCategory", fetch = FetchType.LAZY)
    @JsonIgnore
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
