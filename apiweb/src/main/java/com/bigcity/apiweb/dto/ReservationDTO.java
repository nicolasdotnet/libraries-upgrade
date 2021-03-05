/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author nicolasdotnet
 */
public class ReservationDTO {

    @NotEmpty(message = "la réference usagé n'est pas renseigné")
    @ApiModelProperty(notes = "booking user email")
    private String bookingUserEmail;

    @NotEmpty(message = "la réference livre n'est pas renseigné")
    @ApiModelProperty(notes = "book Isbn")
    private String bookIsbn;

    public ReservationDTO() {
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
        return "ReservationDTO{" + "bookingUserEmail=" 
                + bookingUserEmail + ", bookIsbn=" 
                + bookIsbn + '}';
    }
}
