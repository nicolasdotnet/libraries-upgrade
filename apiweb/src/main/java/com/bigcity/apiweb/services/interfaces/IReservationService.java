/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.ReservationDTO;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.exceptions.ReservationNotPossibleException;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.exceptions.ReservationNotPossibleException;
import java.time.LocalDate;
import java.util.Date;
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
    List<Reservation> ManagementOfReservations(Date date) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException;

    /**
     *
     * @param dateReservationOut
     * @return
     */
    List<Reservation> getAllReservationsByOutdated(Date dateReservationOut);

    // date de validate du jour et status en cours 
    /**
     *
     * @param validateDate
     * @return
     */
    List<Reservation> getAllReservationsByValidateDate(Date validateDate);

    // statut rservation en plus comme accepter par user . Apres reservation status résa = terminer
    // check si encore dispo si oui cree pret
    /**
     *
     * @param idresa
     * @throws EntityNotFoundException
     * @throws EntityAlreadyExistsException
     * @throws BookingNotPossibleException
     */
    void fronReservationToBooking(Long idresa) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException;

    // status cancel
    /**
     *
     * @param idresa
     * @throws EntityNotFoundException
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     * @throws com.bigcity.apiweb.exceptions.BookingNotPossibleException
     */
    void cancelReservation(Long idresa) throws EntityNotFoundException, EntityAlreadyExistsException, BookingNotPossibleException;

    /**
     *
     * @param idresa
     * @return
     * @throws EntityNotFoundException
     * @throws BookingNotPossibleException
     */
    Booking validateReservation(Long idresa) throws EntityNotFoundException, BookingNotPossibleException;

    /**
     *
     * @param idresa
     * @return
     * @throws EntityNotFoundException
     * @throws EntityAlreadyExistsException
     * @throws BookingNotPossibleException
     */
    Reservation activateReservation(Long idresa) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException;

    /**
     * methode to get a booking by reservation
     *
     * @param idresa
     * @return
     * @throws EntityNotFoundException
     */
    Booking getBookingByReservation(Long idresa) throws EntityNotFoundException;

    /**
     * method to get all reservations by ask user
     *
     * @param email
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    List<Reservation> getAllReservationByUser(String email) throws EntityNotFoundException;

    /**
     * method to get a reservation
     *
     * @param reservationId
     * @return
     */
    Reservation getReservation(Long reservationId);
    
        /**
     * method to get all reservations isbn book
     *
     * @param isbn
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    List<Reservation> getAllReservationByIsbn(String isbn) throws EntityNotFoundException;
    
    

}
