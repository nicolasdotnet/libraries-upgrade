/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services;

import com.bigcity.apiweb.dao.IReservationRepository;
import com.bigcity.apiweb.dto.ReservationDTO;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.BookingStatus;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.entity.ReservationStatus;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.exceptions.ReservationNotPossibleException;
import com.bigcity.apiweb.services.interfaces.IBookService;
import com.bigcity.apiweb.services.interfaces.IBookingService;
import com.bigcity.apiweb.services.interfaces.IReservationService;
import com.bigcity.apiweb.services.interfaces.IUserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private static final Logger log = LogManager.getLogger(ReservationServiceImpl.class);

    @Autowired
    private IReservationRepository iReservationRepository;

    @Autowired
    private IBookingService iBookingservice;

    @Autowired
    private IBookService iBookService;

    @Autowired
    private IUserService iUserService;

    @Value("${responseDurationInHours}")
    private String responseDuration;

    @Value("${bookingDurationInDay}")
    private String bookingDuration;

    @Override
    public List<Reservation> ManagementOfReservations(LocalDate date) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        List<Reservation> reservationsEdit = this.deleteReservationsOld(date);

        if (!reservationsEdit.isEmpty()) {

            this.activateReservationNext(reservationsEdit);

        }

        return iReservationRepository.findAllByValidateReservationDateAndReservationStatus(LocalDate.now(), ReservationStatus.ATTENTE.getValue());
    }

    @Override
    public Reservation register(ReservationDTO reservationDto) throws EntityAlreadyExistsException, ReservationNotPossibleException, EntityNotFoundException {

        Optional<Book> book = iBookService.getBookByIsbn(reservationDto.getBookIsbn());
        Optional<User> reservationUser = iUserService.getUserByEmail(reservationDto.getReservationUserEmail());

        int copiesAvailable = book.get().getCopiesAvailable();

        if (copiesAvailable != 0) {

            log.error("Il y a encore des exemplaires disponibles !");

            throw new ReservationNotPossibleException("Il y a encore des exemplaires disponibles !");

        }

        Optional<Booking> bookingFind = iBookingservice.getBookingByBookAndUserAndBookingStatus2(book.get(), reservationUser.get(), BookingStatus.TERMINE.getValue());

        if (bookingFind.isPresent()) {

            log.error("Vous avez un prêt en cours avec ce livre !");

            throw new EntityAlreadyExistsException("Vous avez un prêt en cours avec ce livre !");

        }

        Optional<Reservation> reservationFind = iReservationRepository.findByBookAndReservationUser(book.get(), reservationUser.get());

        if (reservationFind.isPresent()) {

            log.error("La réservation existe déjà !");

            throw new EntityAlreadyExistsException("La réservation existe déjà !");

        }

        // algo va cherché en base les résa enregistré puis compare avec le nombre de copie * 2 ou *3....
        if (iReservationRepository.findAllByBook(book.get()).size() >= (book.get().getNumberOfCopies() * 2)) {

            log.error("Il n'y a plus de réservation disponible pour réserver ce livre !");

            throw new ReservationNotPossibleException("Il n'y a plus de réservation disponible pour réserver ce livre !");

        }

        Reservation reservation = new Reservation();

        reservation.setBook(book.get());
        reservation.setReservationStatus(ReservationStatus.ENCOURS.getValue());
        reservation.setReservationUser(reservationUser.get());
        reservation.setReservationDate(new Date());

        return iReservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(Long reservationId) throws EntityNotFoundException, EntityAlreadyExistsException, BookingNotPossibleException {

        Optional<Reservation> reservationFind = iReservationRepository.findById(reservationId);

        if (!reservationFind.isPresent()) {

            log.error("La réservation n'existe pas !");

            throw new EntityNotFoundException("La réservation n'existe pas !");

        }

        Optional<Booking> bookingOptional = iBookingservice.getBookingByBookAndUserAndBookingStatus(reservationFind.get().getBook(),
                reservationFind.get().getReservationUser(), BookingStatus.RESERVE.getValue());

        if (bookingOptional.isPresent()) {

            iBookingservice.cancelBookingForReservation(bookingOptional.get().getBookingId());

            Collection<Reservation> collReservations = reservationFind.get().getBook().getReservations();

            int size = collReservations.size();

            if (size > 1) {

                List<Reservation> reservations = new ArrayList(collReservations);
                Collections.sort(reservations, Reservation.ComparatorReservationId);

                Reservation r = reservations.get(1);

                Reservation r2 = this.activateReservation(r.getReservationId());

            }

        }

        iReservationRepository.delete(reservationFind.get());
    }

    @Override
    public Booking validateReservation(Long reservationId) throws EntityNotFoundException, BookingNotPossibleException {

        Optional<Reservation> reservationFind = iReservationRepository.findById(reservationId);

        if (!reservationFind.isPresent()) {

            log.error("La réservation n'existe pas !");

            throw new EntityNotFoundException("La réservation n'existe pas !");

        }

        Optional<Booking> bookingOptional = iBookingservice.getBookingByBookAndUserAndBookingStatus(reservationFind.get().getBook(),
                reservationFind.get().getReservationUser(), BookingStatus.RESERVE.getValue());

        if (!bookingOptional.isPresent()) {

            log.error("Vous ne pouvez pas valider ! Aucun exemplaire disponible !");

            throw new EntityNotFoundException("Vous ne pouvez pas valider ! Aucun exemplaire disponible");

        }

        bookingOptional.get().setBookingEndDate(java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()).plusDays(Long.valueOf(bookingDuration) + 1)));

        bookingOptional.get().setBookingStatus(BookingStatus.ENCOURS.getValue());

        iReservationRepository.delete(reservationFind.get());

        return iBookingservice.updateBookingForReservation(bookingOptional.get());

    }

    @Override
    public Reservation activateReservation(Long reservationId) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        Optional<Reservation> reservationFind = iReservationRepository.findById(reservationId);

        if (!reservationFind.isPresent()) {

            log.error("La réservation n'existe pas !");

            throw new EntityNotFoundException("La réservation n'existe pas!");

        }

        reservationFind.get().setReservationStatus(ReservationStatus.ATTENTE.getValue());
        reservationFind.get().setValidateReservationDate(LocalDate.now());

        Booking booking = new Booking();
        booking.setBook(reservationFind.get().getBook());
        booking.setBookingUser(reservationFind.get().getReservationUser());
        booking.setBookingStatus(BookingStatus.RESERVE.getValue());

        iBookingservice.registerBookingForReservation(booking);

        return iReservationRepository.saveAndFlush(reservationFind.get());

    }

    @Override
    public List<Reservation> getAllReservationByUser(String email) throws EntityNotFoundException {

        Optional<User> userFind = iUserService.getUserByEmail(email);

        if (!userFind.isPresent()) {

            throw new EntityNotFoundException("Il n'y a pas de prêt pour cet usager.");
        }

        List<Reservation> reservations = iReservationRepository.findAllByReservationUser(userFind.get());

        return reservations;
    }

    @Override
    public Reservation getReservation(Long reservationId) {
        return iReservationRepository.findById(reservationId).get();
    }

    @Override
    public List<Reservation> getAllReservationByIsbn(String isbn) throws EntityNotFoundException {

        Optional<Book> bookFind = iBookService.getBookByIsbn(isbn);

        if (!bookFind.isPresent()) {

            throw new EntityNotFoundException("Il n'y a pas de livre pour cette référence isbn.");
        }

        return iReservationRepository.findAllByBook(bookFind.get());

    }

    private List<Reservation> deleteReservationsOld(LocalDate date) throws EntityNotFoundException {

        List<Reservation> reservations = iReservationRepository.findAllByValidateReservationDateBeforeAndReservationStatus(date, ReservationStatus.ATTENTE.getValue());
        
        List<Reservation> reservationsEdit = new ArrayList<>();

        if (! reservations.isEmpty()) {

            Iterator<Reservation> iterator = reservations.iterator();

            while (iterator.hasNext()) {

                Reservation reservation = iterator.next();

                Booking bookingFind = this.getBookingByReservation(reservation);

                iBookingservice.cancelBookingForReservation(bookingFind.getBookingId());

                iReservationRepository.delete(reservation);

                reservationsEdit.add(reservation);
            }

        }
        return reservationsEdit;
    }

    private void activateReservationNext(List<Reservation> reservationsEdit) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        int size;

        for (Reservation reservation : reservationsEdit) {

            Collection<Reservation> collReservations = reservation.getBook().getReservations();

            size = collReservations.size();

            if (size > 1) {

                List<Reservation> lr = new ArrayList(collReservations);
                Collections.sort(lr, Reservation.ComparatorReservationId);

                Reservation r = lr.get(1);

                this.activateReservation(r.getReservationId());

            }

        }
    }

    private Booking getBookingByReservation(Reservation reservation) throws EntityNotFoundException {

        Optional<Booking> bookingOptional = iBookingservice.getBookingByBookAndUserAndBookingStatus(reservation.getBook(),
                reservation.getReservationUser(), BookingStatus.RESERVE.getValue());

        if (!bookingOptional.isPresent()) {

            log.error("Le prêt n'existe pas !");

            throw new EntityNotFoundException("Le prêt n'existe pas !");

        }

        return bookingOptional.get();

    }
}
