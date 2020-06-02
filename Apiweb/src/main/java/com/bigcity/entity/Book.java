/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.entity;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author nicolasdotnet
 */
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long bookId;
    
    @Column(length = 100, nullable = false)
    private String isbn;

    @Column(length = 100, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private String bookTitle;

    //@Column(length = 2, nullable = false)
    private int copiesAvailable;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Collection<Booking> bookings;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private User librarian;

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

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public Collection<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Collection<Booking> bookings) {
        this.bookings = bookings;
    }

//    public User getLibrarian() {
//        return librarian;
//    }
//
//    public void setLibrarian(User librarian) {
//        this.librarian = librarian;
//    }

    @Override
    public String toString() {
        return "Book{" + "bookId=" + bookId + ", isbn=" + isbn + ", authorFirstname=" + author + ", bookTitle=" + bookTitle + ", copiesAvailable=" + copiesAvailable + '}';
    }


    
    

}
