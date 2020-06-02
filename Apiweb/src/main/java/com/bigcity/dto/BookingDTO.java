/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dto;

import javax.validation.constraints.NotEmpty;

/**
 *
 * @author nicolasdotnet
 */
public class BookingDTO {

    
    // doc swager
    @NotEmpty(message = "la réference bibliothécaire n'est pas renseigné.")
    private Long librarianId;
    @NotEmpty(message = "la réference usagé n'est pas renseigné.")
    private Long bookingUserId;
    @NotEmpty(message = "la réference livre n'est pas renseigné.")
    private Long bookId;

    public BookingDTO() {
    }

    public Long getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(Long librarianId) {
        this.librarianId = librarianId;
    }

    public Long getBookingUserId() {
        return bookingUserId;
    }

    public void setBookingUserId(Long bookingUserId) {
        this.bookingUserId = bookingUserId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "BookingDTO{" + "librarianId=" + librarianId + ", bookingUserId=" + bookingUserId + ", bookId=" + bookId + '}';
    }
    
    
}
