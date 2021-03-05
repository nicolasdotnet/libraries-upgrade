/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author pi
 *
 * Reservation is the registration entity of a book.
 */
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long reservationId;

    @Column(nullable = false)
    private Date reservationDate;

    @Column(nullable = false)
    private String reservationStatus;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User reservationUser;

    @ManyToOne
    @JoinColumn(nullable = false)
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
        return "Reservation{" + "reservationId=" + reservationId + ","
                + " reservationDate=" + reservationDate + ","
                + " reservationStatus=" + reservationStatus + ","
                + " reservationUser=" + reservationUser + ","
                + " book=" + book + '}';
    }

}
