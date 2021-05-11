/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.ReservationDTO;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.exceptions.ReservationNotPossibleException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nicolasdotnet
 */
public interface IReservationService {

    /**
     * method to register a reservation
     *
     * @param reservationDto
     * @return reservation objet
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     * @throws com.bigcity.apiweb.exceptions.ReservationNotPossibleException
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Reservation register(ReservationDTO reservationDto) throws EntityAlreadyExistsException,
            ReservationNotPossibleException, EntityNotFoundException;

    /**
     * method to management the reservations : update status
     *
     * @param date
     * @return reservation list
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     * @throws com.bigcity.apiweb.exceptions.BookingNotPossibleException
     */
    List<Reservation> ManagementOfReservations(LocalDate date) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException;

    /**
     * method to cancel reservation by a user
     *
     * @param reservationId
     * @throws EntityNotFoundException
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     * @throws com.bigcity.apiweb.exceptions.BookingNotPossibleException
     */
    void cancelReservation(Long reservationId) throws EntityNotFoundException, EntityAlreadyExistsException, BookingNotPossibleException;

    /**
     * method to validate reservation by a user
     *
     * @param reservationId
     * @return the booking create by the validate reservation
     * @throws EntityNotFoundException
     * @throws BookingNotPossibleException
     */
    Booking validateReservation(Long reservationId) throws EntityNotFoundException, BookingNotPossibleException;

    /**
     * method to activate reservation after a book is back
     *
     * @param reservationId
     * @return one reservation
     * @throws EntityNotFoundException
     * @throws EntityAlreadyExistsException
     * @throws BookingNotPossibleException
     */
    Reservation activateReservation(Long reservationId) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException;

    /**
     * method to get all reservations by ask user
     *
     * @param email
     * @return reservation list
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    List<Reservation> getAllReservationByUser(String email) throws EntityNotFoundException;

    /**
     * method to get a reservation by id
     *
     * @param reservationId
     * @return one reservation
     */
    Reservation getReservation(Long reservationId);

    /**
     * method to get all reservations isbn book
     *
     * @param isbn
     * @return reservation list
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    List<Reservation> getAllReservationByIsbn(String isbn) throws EntityNotFoundException;

}
