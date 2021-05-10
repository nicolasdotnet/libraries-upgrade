/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.BookingDTO;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookingService {

    /**
     * method to register a booking
     *
     * @param bookingDto
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     * @throws com.bigcity.apiweb.exceptions.BookingNotPossibleException
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Booking register(BookingDTO bookingDto) throws EntityAlreadyExistsException, BookingNotPossibleException, EntityNotFoundException;

    /**
     * method to Management of book returns
     *
     * @param bookingId
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     * @throws com.bigcity.apiweb.exceptions.BookingNotPossibleException
     */
    Booking ManagementOfBookReturns(Long bookingId) throws EntityNotFoundException, EntityAlreadyExistsException, BookingNotPossibleException;

    /**
     * method to stop a booking when book is back
     *
     * @param bookingId
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Booking bookIsBack(Long bookingId) throws EntityNotFoundException;

    /**
     * method to extend a booking
     *
     * @param bookingId
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     * @throws com.bigcity.apiweb.exceptions.BookingNotPossibleException
     */
    Booking extendBooking(Long bookingId) throws EntityNotFoundException, BookingNotPossibleException;

    /**
     * method to get a booking
     *
     * @param bookingId
     * @return
     */
    Optional<Booking> getBooking(Long bookingId);

    /**
     * method to get all bookings by ask user
     *
     * @param email
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    List<Booking> getAllBookingByUser(String email) throws EntityNotFoundException;

    /**
     * method to get all bookings by isbn book
     *
     * @param isbn
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    List<Booking> getAllBookingByIsbn(String isbn) throws EntityNotFoundException;

    /**
     * method to get all bookings
     *
     * @return
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    List<Booking> getAllBookings() throws EntityNotFoundException;

    /**
     * method to get all bookings by criteria
     *
     * @param bookingId
     * @param bookingStatus
     * @param bookingUserEmail
     * @param bookTitle
     * @param page
     * @param size
     * @return
     */
    Page<Booking> getAllBookingsByCriteria(String bookingId, String bookingStatus, String bookingUserEmail, String bookTitle, int page, int size);

    /**
     * method to get all bookings by outdated
     *
     * @param dateBookingOut
     * @return
     */
    List<Booking> getAllBookingByOutdated(LocalDate dateBookingOut);

    /**
     * method to create booking post validate reservation by user
     *
     * @param booking
     * @return booking create
     * @throws EntityAlreadyExistsException
     * @throws BookingNotPossibleException
     * @throws EntityNotFoundException
     */
    Booking registerBookingForReservation(Booking booking) throws EntityAlreadyExistsException, BookingNotPossibleException, EntityNotFoundException;

    /**
     * method to cancel booking post cancel reservation by user
     *
     * @param bookingId
     * @throws EntityNotFoundException
     */
    void cancelBookingForReservation(Long bookingId) throws EntityNotFoundException;

    /**
     * method to get all booking by book, user and status booking
     *
     * @param book
     * @param reservationUser
     * @param bookingStatus
     * @return
     */
    Optional<Booking> getBookingByBookAndUserAndBookingStatus(Book book, User reservationUser, String bookingStatus);

    /**
     * method to get all booking by book, user and status booking
     *
     * @param book
     * @param reservationUser
     * @param bookingStatus
     * @return
     */
    Optional<Booking> getBookingByBookAndUserAndBookingStatus2(Book book, User reservationUser, String bookingStatus);

    /**
     * method to update booking
     *
     * @param booking
     * @return booking update
     * @throws EntityNotFoundException
     * @throws BookingNotPossibleException
     */
    Booking updateBookingForReservation(Booking booking) throws EntityNotFoundException, BookingNotPossibleException;

}
