package com.bigcity.apiweb.entity;

import java.io.Serializable;
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
 * 
 * Booking is the registration entity of a booking.
 * 
 */
@Entity
public class Booking implements Serializable {

    @Id
    @GeneratedValue
    private Long bookingId;
    
    @Column(nullable = false)
    private Date bookingStartDate;
    
    @Column(length = 3, nullable = false)
    private String BookingDurationDay;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookingEndDate;
    
    private Date backBookDate;
    
    @Column(length = 2, nullable = false)
    private String counterExtension;
    
    @Column(nullable = false)
    private String bookingStatus;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private User bookingUser;
    
    @ManyToOne
    @JoinColumn(nullable = false)
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

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
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
        return "Booking{" + "bookingId=" + bookingId + ", bookingStartDate=" + bookingStartDate +
                ", BookingDurationWeek=" + BookingDurationDay + ", bookingEndDate=" + bookingEndDate + ", backBookDate="
                + backBookDate + ", counterExtension=" + counterExtension + ", bookingStatus=" +
                bookingStatus + ", bookingUser=" + bookingUser + ", book=" + book + '}';
    }
    
    

}
