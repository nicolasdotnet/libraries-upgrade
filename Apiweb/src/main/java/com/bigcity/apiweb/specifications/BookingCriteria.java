/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.specifications;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author nicolasdotnet
 */
public class BookingCriteria {

    @ApiModelProperty(notes = "booking id")
    private Long bookingId;

    @ApiModelProperty(notes = "booking status : ENCOURS, TERMINE, PROLONGE")
    private String bookingStatus;

    @ApiModelProperty(notes = "email booking owner")
    private String bookingUserEmail;

    @ApiModelProperty(notes = "title reserved book")
    private String bookTitle;

    public BookingCriteria() {
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBookingUserEmail() {
        return bookingUserEmail;
    }

    public void setBookingUserEmail(String bookingUserEmail) {
        this.bookingUserEmail = bookingUserEmail;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    

}
