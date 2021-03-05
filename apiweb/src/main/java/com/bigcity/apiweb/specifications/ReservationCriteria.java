/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.specifications;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author pi
 */
public class ReservationCriteria {

    @ApiModelProperty(notes = "reservation id")
    private Long reservationId;

    @ApiModelProperty(notes = "reservation status")
    private String reservationStatus;

    @ApiModelProperty(notes = "email reservation owner")
    private String reservationUserEmail;

    @ApiModelProperty(notes = "title reserved book")
    private String bookTitle;

    public ReservationCriteria() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getReservationUserEmail() {
        return reservationUserEmail;
    }

    public void setReservationUserEmail(String reservationUserEmail) {
        this.reservationUserEmail = reservationUserEmail;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    

}
