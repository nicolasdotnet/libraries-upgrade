/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.beans;

import java.util.Date;

/**
 *
 * @author nicolasdotnet
 */
public class Revive {
    
    private String firstname;
    private String lastname;
    private String email;
    private String bookTitle;
    private Date bookingStartDate;
    private Date bookingEndDate;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(Date bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public Date getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(Date bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    @Override
    public String toString() {
        return "Revive{" + "firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", bookTitle=" + bookTitle + ", bookingStartDate=" + bookingStartDate + ", bookingEndDate=" + bookingEndDate + '}';
    }
    
    
    
}
