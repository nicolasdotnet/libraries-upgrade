/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.beans;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 *
 * Reservation is the registration entity of a book.
 */
@Component
public class Reservation implements Serializable {

    private Long reservationId;

    private Date reservationDate;

    private Date validateReservationDate;

    private Date returnDateBook;

    private String queuePosition;

    private String reservationStatus;

    private User reservationUser;

    private Book book;

    public Reservation() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getValidateReservationDate() {
        return validateReservationDate;
    }

    public void setValidateReservationDate(Date validateReservationDate) {
        this.validateReservationDate = validateReservationDate;
    }

    public Date getReturnDateBook() {
        return returnDateBook;
    }

    public void setReturnDateBook(Date returnDateBook) {
        this.returnDateBook = returnDateBook;
    }

    public String getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(String queuePosition) {
        this.queuePosition = queuePosition;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public User getReservationUser() {
        return reservationUser;
    }

    public void setReservationUser(User reservationUser) {
        this.reservationUser = reservationUser;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Reservation{" + "reservationId="
                + reservationId + ", reservationDate="
                + reservationDate + ", validateReservationDate="
                + validateReservationDate + ", reservationStatus="
                + reservationStatus + ", reservationUser="
                + reservationUser + ", book="
                + book + '}';
    }

    public static Comparator<Reservation> ComparatorReservationId = new Comparator<Reservation>() {

        @Override
        public int compare(Reservation r1, Reservation r2) {
            return (int) (r1.getReservationId() - r2.getReservationId());
        }

    };

}
