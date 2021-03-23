/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.dto;

/**
 *
 * @author nicolasdotnet
 */
public class ReservationDTO {

    private String reservationUserEmail;

    private String bookIsbn;

    public ReservationDTO() {
    }

    public String getReservationUserEmail() {
        return reservationUserEmail;
    }

    public void setReservationUserEmail(String reservationUserEmail) {
        this.reservationUserEmail = reservationUserEmail;
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
                + reservationUserEmail + ", bookIsbn=" 
                + bookIsbn + '}';
    }
}
