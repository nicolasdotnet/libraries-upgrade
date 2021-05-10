package com.bigcity.appweb.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 * 
 *  Book is the registration bean of a book.
 * 
 */
@Component
public class Book implements Serializable {

    private Long bookId;

    private String isbn;

    private String author;

    private String title;
    
    private String summary;

    private Date returnDateBook;
    
    private String numberCurrentReservations;
    
    private int copiesAvailable;
    
    private int numberOfCopies;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
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

    public Date getReturnDateBook() {
        return returnDateBook;
    }

    public void setReturnDateBook(Date returnDateBook) {
        this.returnDateBook = returnDateBook;
    }

    public String getNumberCurrentReservations() {
        return numberCurrentReservations;
    }

    public void setNumberCurrentReservations(String numberCurrentReservations) {
        this.numberCurrentReservations = numberCurrentReservations;
    }

    @Override
    public String toString() {
        return "Book{" + "bookId=" + bookId + ", isbn=" + isbn + ", author=" + author + ", title=" + title + ", summary=" + summary + ", copiesAvailable=" + copiesAvailable + ", bookCategory=" + bookCategory + '}';
    }

}
