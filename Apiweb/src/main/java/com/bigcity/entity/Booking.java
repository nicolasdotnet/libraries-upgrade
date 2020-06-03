/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nicolasdotnet
 */
@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Long bookingId;
    
    @Column(nullable = false)
    private Date bookingStartDate;
    
    @Column(length = 3, nullable = false)
    private String BookingDurationDay; // constance ? param ?
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookingEndDate;
    
    private Date backBookDate;
    
    @Column(length = 2, nullable = false)
    private String counterExtension;
    
    @Column(nullable = false)
    private BookingStatus bookingStatus;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User librarian;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User bookingUser;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Book book;

    public Booking() {
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(Date bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public String getBookingDurationDay() {
        return BookingDurationDay;
    }

    public void setBookingDurationDay(String BookingDurationDay) {
        this.BookingDurationDay = BookingDurationDay;
    }

    public Date getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(Date bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public Date getBackBookDate() {
        return backBookDate;
    }

    public void setBackBookDate(Date backBookDate) {
        this.backBookDate = backBookDate;
    }

    public String getCounterExtension() {
        return counterExtension;
    }

    public void setCounterExtension(String counterExtension) {
        this.counterExtension = counterExtension;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public User getLibrarian() {
        return librarian;
    }

    public void setLibrarian(User librarian) {
        this.librarian = librarian;
    }

    public User getBookingUser() {
        return bookingUser;
    }

    public void setBookingUser(User bookingUser) {
        this.bookingUser = bookingUser;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Booking{" + "bookingId=" + bookingId + ", bookingStartDate=" + bookingStartDate + ", BookingDurationWeek=" + BookingDurationDay + ", bookingEndDate=" + bookingEndDate + ", backBookDate=" + backBookDate + ", counterExtension=" + counterExtension + ", bookingStatus=" + bookingStatus + ", librarian=" + librarian + ", bookingUser=" + bookingUser + ", book=" + book + '}';
    }
    
    

}
