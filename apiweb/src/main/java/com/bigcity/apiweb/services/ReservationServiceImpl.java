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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * @author pi
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
    public List<Reservation> ManagementOfReservations(Date date) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        List<Reservation> reservationsEdit = this.deleteResevationOld(date);

        if (!reservationsEdit.isEmpty()) {

            List<Reservation> r = this.reservationsNext(reservationsEdit);

        }

        return this.getAllReservationsByValidateDate(new Date());

    }

    @Override
    public Reservation register(ReservationDTO reservationDto) throws EntityAlreadyExistsException, ReservationNotPossibleException, EntityNotFoundException {

        Book book = iBookService.getBookByIsbn(reservationDto.getBookIsbn());
        Optional<User> reservationUser = iUserService.getUserByEmail(reservationDto.getBookingUserEmail());

        int copiesAvailable = book.getCopiesAvailable();

        if (copiesAvailable != 0) {

            log.error("Il y a encore des exemplaires disponibles !");

            throw new ReservationNotPossibleException("Il y a encore des exemplaires disponibles !");

        }

        Optional<Booking> bookingFind = iBookingservice.getBookingByBookAndUserAndBookingStatus(book, reservationUser.get(), BookingStatus.TERMINE.getValue());

        if (bookingFind.isPresent()) {

            log.error("Vous avez un prêt en cours avec ce livre !");

            throw new EntityAlreadyExistsException("Vous avez un prêt en cours avec ce livre !");

        }

        Optional<Reservation> reservationFind = iReservationRepository.findByBookAndReservationUserAndReservationStatusNotLike(book, reservationUser.get(), ReservationStatus.TERMINE.getValue());

        if (reservationFind.isPresent()) {

            log.error("La réservation existe déjà !");

            throw new EntityAlreadyExistsException("La réservation existe déjà !");

        }

        int reservationsAvailable = book.getReservationsAvailable();

        if (reservationsAvailable == 0) {

            log.error("Il n'y a plus de réservation disponible pour réserver ce livre !");

            throw new ReservationNotPossibleException("Il n'y a plus de réservation disponible pour réserver ce livre !");

        }

        book.setReservationsAvailable(--reservationsAvailable);
        
        // algo va cherché en base le nombre de résa enregistré * 2 ou *3....

        Reservation reservation = new Reservation();

        reservation.setBook(book);
        reservation.setReservationStatus(ReservationStatus.ENCOURS.getValue());
        reservation.setReservationUser(reservationUser.get());
        reservation.setReservationDate(new Date());

        try {
            // date = 00/00/00 pas de time car non valide
            
            Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-21");
            
            System.out.println("DATE TEST : "+testDate.toString());
            
            reservation.setValidateReservationDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-21"));
        } catch (ParseException ex) {
            log.error(ex.getMessage());
        }

        return iReservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservationsByOutdated(Date dateReservationOut) {

        Date dateFormat = this.generateDateGoodFormat(dateReservationOut);

        System.out.println("DDDDDDDDDDDDDDATE : " + dateFormat);

        return iReservationRepository.findAllByValidateReservationDateLessThanEqualAndReservationStatus
        (dateFormat, ReservationStatus.ATTENTE.getValue());
    }

    @Override
    public List<Reservation> getAllReservationsByValidateDate(Date dateretourlivre) {

        Date dateFormat = this.generateDateGoodFormat(dateretourlivre);

        System.out.println("DDDDDDDDDDDDDDATE : " + dateFormat);

        return iReservationRepository.findAllByValidateReservationDateAndReservationStatus
        (dateFormat, ReservationStatus.ATTENTE.getValue());
    }

    @Override
    public void fronReservationToBooking(Long idresa) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        Optional<Reservation> reservationFind = iReservationRepository.findById(idresa);

        if (!reservationFind.isPresent()) {

            log.error("La réservation n'existe pas !");

            throw new EntityNotFoundException("La réservation n'existe pas!");

        }

        Booking booking = new Booking();
        booking.setBook(reservationFind.get().getBook());
        booking.setBookingUser(reservationFind.get().getReservationUser());
        booking.setBookingStatus(BookingStatus.RESERVE.getValue());

        iBookingservice.registerBookingForReservation(booking);

//        return iReservationRepository.saveAndFlush(reservationFind.get());
    }

    @Override
    public void cancelReservation(Long idresa) throws EntityNotFoundException {

        Optional<Reservation> reservationFind = iReservationRepository.findById(idresa);

        if (!reservationFind.isPresent()) {

            log.error("La réservation n'existe pas !");

            throw new EntityNotFoundException("La réservation n'existe pas!");

        }

        Optional<Booking> bookingOptional = iBookingservice.getBookingByBookAndUserAndBookingStatus(reservationFind.get().getBook(),
                reservationFind.get().getReservationUser(), BookingStatus.RESERVE.getValue());

        if (!bookingOptional.isPresent()) {

            log.error("Le prêt n'existe pas !");

            throw new EntityNotFoundException("Le prêt n'existe pas !" + " " + reservationFind.get().getBook().getBookId() + " "
                    + reservationFind.get().getReservationUser().getUserId() + " " + BookingStatus.RESERVE.getValue());

        }

        iBookingservice.cancelBookingForReservation(bookingOptional.get().getBookingId());

        iReservationRepository.delete(reservationFind.get());
    }

    @Override
    public Booking validateReservation(Long idresa) throws EntityNotFoundException, BookingNotPossibleException {

        Optional<Reservation> reservationFind = iReservationRepository.findById(idresa);

        if (!reservationFind.isPresent()) {

            log.error("La réservation n'existe pas !");

            throw new EntityNotFoundException("La réservation n'existe pas!");

        }

        Optional<Booking> bookingOptional = iBookingservice.getBookingByBookAndUserAndBookingStatus(reservationFind.get().getBook(),
                reservationFind.get().getReservationUser(), BookingStatus.RESERVE.getValue());

        if (!bookingOptional.isPresent()) {

            log.error("Le prêt n'existe pas !");

            throw new EntityNotFoundException("Le prêt n'existe pas !" + " " + reservationFind.get().getBook().getBookId() + " "
                    + reservationFind.get().getReservationUser().getUserId() + " " + BookingStatus.RESERVE.getValue());

        }

        bookingOptional.get().setBookingEndDate(java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()).plusDays(Long.valueOf(bookingDuration) + 1)));

        bookingOptional.get().setBookingStatus(BookingStatus.ENCOURS.getValue());

        iReservationRepository.delete(reservationFind.get());

        return iBookingservice.updateBooking(bookingOptional.get());

    }

    public List<Reservation> deleteResevationOld(Date date) throws EntityNotFoundException {

        List<Reservation> reservations = this.getAllReservationsByOutdated(date);

        List<Reservation> reservationsEdit = new ArrayList<>();

        if (reservations != null) {

            Iterator<Reservation> iterator = reservations.iterator();

            while (iterator.hasNext()) {

                Reservation reservation = iterator.next();

                Booking bookingFind = this.getBookingByReservation(reservation.getReservationId());

                iBookingservice.cancelBookingForReservation(bookingFind.getBookingId());

                iReservationRepository.delete(reservation);

                reservationsEdit.add(reservation);
                
                // factoriser avec cancel methode
            }

        }
        return reservationsEdit;
    }

    public List<Reservation> reservationsNext(List<Reservation> reservationsEdit) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        int size;
        List<Reservation> lr = new ArrayList();

        for (Reservation reservation : reservationsEdit) {

            Collection<Reservation> collReservations = reservation.getBook().getReservations();

            size = collReservations.size();

            if (size > 1) {

                List<Reservation> reservations = new ArrayList(collReservations);
                Collections.sort(reservations, Reservation.ComparatorReservationId);

                Reservation r = reservations.get(1);

                Reservation r2 = this.activateReservation(r.getReservationId());

                lr.add(r2);

            }

        }

        return lr;
    }

    @Override
    public Reservation activateReservation(Long idresa) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        Optional<Reservation> reservationFind = iReservationRepository.findById(idresa);

        if (!reservationFind.isPresent()) {

            log.error("La réservation n'existe pas !");

            throw new EntityNotFoundException("La réservation n'existe pas!");

        }

        reservationFind.get().setReservationStatus(ReservationStatus.ATTENTE.getValue());

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA Date : " + this.generateDateGoodFormat(new Date()));

        reservationFind.get().setValidateReservationDate(this.generateDateGoodFormat(new Date()));

        this.fronReservationToBooking(reservationFind.get().getReservationId());

        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBB RESA Date : " + reservationFind.toString());

        return iReservationRepository.saveAndFlush(reservationFind.get());

    }

    @Override
    public Booking getBookingByReservation(Long idresa) throws EntityNotFoundException {

        Optional<Reservation> reservationFind = iReservationRepository.findById(idresa);

        if (!reservationFind.isPresent()) {

            log.error("La réservation n'existe pas !");

            throw new EntityNotFoundException("La réservation n'existe pas!");

        }

        Optional<Booking> bookingOptional = iBookingservice.getBookingByBookAndUserAndBookingStatus(reservationFind.get().getBook(),
                reservationFind.get().getReservationUser(), BookingStatus.RESERVE.getValue());

        if (!bookingOptional.isPresent()) {

            log.error("Le prêt n'existe pas !");

            throw new EntityNotFoundException("Le prêt n'existe pas !" + " " + reservationFind.get().getBook().getBookId() + " "
                    + reservationFind.get().getReservationUser().getUserId() + " " + BookingStatus.RESERVE.getValue());

        }

        return bookingOptional.get();

    }

    public Date generateDateGoodFormat(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date dateGoodFormat = new Date();

        try {
            dateGoodFormat = format.parse(format.format(date));
        } catch (ParseException ex) {
            log.error(ex.getMessage());
        }
        return dateGoodFormat;

    }
}
