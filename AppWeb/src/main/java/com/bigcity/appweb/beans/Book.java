package com.bigcity.appweb.beans;

import java.io.Serializable;
import java.util.Collection;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 * 
 *  Book is the registration entity of a book.
 * 
 */
@Component
public class Book implements Serializable {

    private Long bookId;

    private String isbn;

    private String author;

    private String title;

    private int copiesAvailable;

    private BookCategory bookCategory;

    private Collection<Booking> bookings;

    public Book() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public Collection<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Collection<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "Book{" + "bookId=" + bookId + ", isbn=" + isbn + ", author=" + author + ", title=" + title + ", copiesAvailable=" + copiesAvailable + 
                ", bookCategory=" + bookCategory.getLabel() + '}';
    }



}
