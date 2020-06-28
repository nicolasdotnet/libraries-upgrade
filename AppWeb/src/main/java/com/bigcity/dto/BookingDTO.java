/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dto;

/**
 *
 * @author nicolasdotnet
 */
public class BookingDTO {
    
    private String librarianEmail;
    
    private String bookingUserEmail;
    
    private String bookIsbn;

    public BookingDTO() {
    }

    public String getLibrarianEmail() {
        return librarianEmail;
    }

    public void setLibrarianEmail(String librarianEmail) {
        this.librarianEmail = librarianEmail;
    }

    public String getBookingUserEmail() {
        return bookingUserEmail;
    }

    public void setBookingUserEmail(String bookingUserEmail) {
        this.bookingUserEmail = bookingUserEmail;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    @Override
    public String toString() {
        return "BookingDTO{" + "librarianEmail=" + librarianEmail + ", bookingUserEmail=" + bookingUserEmail + ", bookIsbn=" + bookIsbn + '}';
    }
    
}
