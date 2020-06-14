package com.bigcity.beans;

import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 * 
 * Booking is the registration entity of a booking.
 * 
 */
@Component
public class Booking implements Serializable {

    private Long bookingId;
    
    private Date bookingStartDate;
    
    private String BookingDurationDay;
    
    private Date bookingEndDate;
    
    private Date backBookDate;
    
    private String counterExtension;
    
    private BookingStatus bookingStatus;
    
    private User bookingUser;
    
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
