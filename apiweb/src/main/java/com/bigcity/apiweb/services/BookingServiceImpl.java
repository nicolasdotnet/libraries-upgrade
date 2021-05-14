package com.bigcity.apiweb.services;

import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.BookingStatus;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.services.interfaces.IBookService;
import com.bigcity.apiweb.services.interfaces.IUserService;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bigcity.apiweb.specifications.BookingSpecification;
import com.bigcity.apiweb.services.interfaces.IBookingService;
import com.bigcity.apiweb.specifications.BookingCriteria;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.bigcity.apiweb.dao.IBookingRepository;
import com.bigcity.apiweb.dto.BookingDTO;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.services.interfaces.IReservationService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class BookingServiceImpl implements IBookingService {

    private static final Logger log = LogManager.getLogger(BookingServiceImpl.class);

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IBookService iBookService;

    @Autowired
    private IReservationService iReservationService;

    @Autowired
    private IUserService iUserService;

    @Value("${bookingDurationInDay}")
    private String bookingDuration;

    @Value("${bookingCounterExtension}")
    private String counterExtension;

    @Override
    public Booking ManagementOfBookReturns(Long bookingId) throws EntityNotFoundException, EntityAlreadyExistsException, BookingNotPossibleException {

        Booking booking = this.bookIsBack(bookingId);
        
        Collection<Reservation> collReservations = booking.getBook().getReservations();

        if (!collReservations.isEmpty()) {

            List<Reservation> reservations = new ArrayList(collReservations);

            Collections.sort(reservations, Reservation.ComparatorReservationId);

            Reservation reservation = reservations.get(0);

            iReservationService.activateReservation(reservation.getReservationId());
        }

        return booking;

    }

    @Override
    public Booking register(BookingDTO bookingDto) throws EntityAlreadyExistsException, BookingNotPossibleException, EntityNotFoundException {
        
        Optional<Book> book = iBookService.getBookByIsbn(bookingDto.getBookIsbn());
        Optional<User> bookingUser = iUserService.getUserByEmail(bookingDto.getBookingUserEmail());
        Optional<Booking> bookingFind = bookingRepository.findByBookAndBookingUserAndBookingStatusNotLike
        (book.get(), bookingUser.get(), BookingStatus.TERMINE.getValue());
        
        if (bookingFind.isPresent()) {

            log.error("Le prêt existe déjà !");

            throw new EntityAlreadyExistsException("Le prêt existe déjà !");

        }

        int copiesAvailable = book.get().getCopiesAvailable();

        if (copiesAvailable == 0) {

            log.error("Il n'y a plus d'exemplaire disponible !");

            throw new BookingNotPossibleException("Il n'y a plus d'exemplaire disponible !");

        }

        book.get().setCopiesAvailable(--copiesAvailable);

        Date bookingEndDate = java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()).plusDays(Long.valueOf(bookingDuration) + 1));

        Booking booking = new Booking();
        booking.setBook(book.get());
        booking.setBookingDurationDay(bookingDuration);
        booking.setBookingStartDate(new Date());
        booking.setBookingEndDate(bookingEndDate);
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
        booking.setBookingUser(bookingUser.get());
        booking.setCounterExtension("0");

        return bookingRepository.save(booking);
    }

    @Override
    public Booking bookIsBack(Long bookingId) throws EntityNotFoundException {

        Optional<Booking> bookingFind = bookingRepository.findById(bookingId);

        if (!bookingFind.isPresent()) {

            log.error("Le prêt n'existe pas dans la base.");

            throw new EntityNotFoundException("Le prêt n'existe pas !");

        }

        bookingFind.get().setBackBookDate(new Date());
        bookingFind.get().setBookingStatus(BookingStatus.TERMINE.getValue());

        int copiesAvailable = bookingFind.get().getBook().getCopiesAvailable();
        bookingFind.get().getBook().setCopiesAvailable(++copiesAvailable);

        return bookingRepository.saveAndFlush(bookingFind.get());

    }

    @Override
    public Optional<Booking> getBooking(Long bookingId) {

        return bookingRepository.findById(bookingId);
    }

    @Override
    public List<Booking> getAllBookingByUser(String email) throws EntityNotFoundException {

        Optional<User> userFind = iUserService.getUserByEmail(email);

        List<Booking> bookings = bookingRepository.findAllByBookingUser(userFind.get());

        return bookings;

    }
                
    @Override
    public Booking extendBooking(Long bookingId) throws EntityNotFoundException, BookingNotPossibleException {

        Optional<Booking> bookingFind = bookingRepository.findById(bookingId);

        if (!bookingFind.isPresent()) {

            log.error("Le prêt n'existe pas.");

            throw new EntityNotFoundException("Le prêt n'existe pas !");

        }

        if (bookingFind.get().getBookingStatus().equals(BookingStatus.TERMINE.getValue())) {

            log.error("Le prêt est terminé !");

            throw new BookingNotPossibleException("Le prêt est terminé ! Vous ne pouvez plus le prolonger");

        }

        if (bookingFind.get().getBookingStatus().equals(BookingStatus.RESERVE.getValue())) {

            log.error("Le prêt n'est pas validé !");

            throw new BookingNotPossibleException("Le prêt n'est pas validé ! Vous ne pouvez pas le prolonger");

        }
        
        if (bookingFind.get().getCounterExtension().equals(counterExtension)) {

            log.error("Une prolongation du prêt a déjà été réalisée !");

            throw new BookingNotPossibleException("Une prolongation du prêt a déjà été réalisée !");

        }

        if (bookingFind.get().getBookingEndDate().before(new Date())) {

            log.error("La date de fin du prêt est dépassé ! Vous ne pouvez pas le prolonger");

            throw new BookingNotPossibleException("La date de fin du prêt est dépassé ! Vous ne pouvez pas le prolonger");

        }

        LocalDate bookingEndDateOld = Instant.ofEpochMilli(bookingFind.get().getBookingEndDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate bookingEndDateNew = bookingEndDateOld.plusDays((Long.valueOf(bookingDuration) + 1));

        bookingFind.get().setBookingEndDate(java.sql.Date.valueOf(bookingEndDateNew));
        bookingFind.get().setBookingStatus(BookingStatus.PROLONGE.getValue());
        bookingFind.get().setCounterExtension("1");

        return bookingRepository.saveAndFlush(bookingFind.get());
    }

    @Override
    public Page<Booking> getAllBookingsByCriteria(String bookingId, String bookingStatus, String bookingUserEmail, String bookTitle, int page, int size) {

        BookingCriteria bookingCriteria = new BookingCriteria();

        bookingCriteria.setBookTitle("".equals(bookTitle) ? null : bookTitle);
        bookingCriteria.setBookingId("".equals(bookingId) ? null : Long.valueOf(bookingId));
        bookingCriteria.setBookingStatus("".equals(bookingStatus) ? null : bookingStatus);
        bookingCriteria.setBookingUserEmail("".equals(bookingUserEmail) ? null : bookingUserEmail);

        BookingSpecification bookSpecification = new BookingSpecification(bookingCriteria);

        return bookingRepository.findAll(bookSpecification, PageRequest.of(page, size));
    }

    @Override
    public List<Booking> getAllBookingByOutdated(LocalDate dateBookingOut) {

        return bookingRepository.findAllByBookingEndDateLessThanEqualAndBookingStatusNotLike(java.sql.Date.valueOf(dateBookingOut), BookingStatus.TERMINE.getValue());

    }

    @Override
    public Booking registerBookingForReservation(Booking booking) throws EntityAlreadyExistsException, BookingNotPossibleException, EntityNotFoundException {

        Optional<Book> book = iBookService.getBookByIsbn(booking.getBook().getIsbn());
        Optional<User> bookingUser = iUserService.getUserByEmail(booking.getBookingUser().getEmail());
        Optional<Booking> bookingFind = bookingRepository.findByBookAndBookingUserAndBookingStatusNotLike
        (book.get(), bookingUser.get(), BookingStatus.TERMINE.getValue());

        if (bookingFind.isPresent()) {

            log.error("Le prêt existe déjà !");

            throw new EntityAlreadyExistsException("Le prêt existe déjà !");

        }

        int copiesAvailable = book.get().getCopiesAvailable();

        if (copiesAvailable == 0) {

            log.error("Il n'y a plus d'exemplaire disponible !");

            throw new BookingNotPossibleException("Il n'y a plus d'exemplaire disponible !");

        }

        book.get().setCopiesAvailable(--copiesAvailable);

        Date bookingEndDate = java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()).plusDays(Long.valueOf(bookingDuration) + 1));

        booking.setBook(book.get());
        booking.setBookingDurationDay(bookingDuration);
        booking.setBookingStartDate(new Date());
        booking.setBookingEndDate(bookingEndDate);
        booking.setBookingStatus(BookingStatus.RESERVE.getValue());
        booking.setBookingUser(bookingUser.get());
        booking.setCounterExtension("0");

        return bookingRepository.save(booking);
    }

    @Override
    public void cancelBookingForReservation(Long bookingId) throws EntityNotFoundException {

        Optional<Booking> bookingFind = bookingRepository.findById(bookingId); // plus status ?

        if (!bookingFind.isPresent()) {

            log.error("Le prêt n'existe pas dans la base.");

            throw new EntityNotFoundException("Le prêt n'existe pas !");

        }

        iBookService.updateBook(bookingFind.get().getBook());

        bookingRepository.deleteById(bookingFind.get().getBookingId());

    }

    @Override
    public Optional<Booking> getBookingByBookAndUserAndBookingStatus(Book book, User reservationUser, String bookingStatus) {

        return bookingRepository.findByBookAndBookingUserAndBookingStatus(book, reservationUser, bookingStatus);

    }

    @Override
    public Optional<Booking> getBookingByBookAndUserAndBookingStatus2(Book book, User reservationUser, String bookingStatus) {

        return bookingRepository.findByBookAndBookingUserAndBookingStatusNotLike(book, reservationUser, bookingStatus);

    }

    @Override
    public Booking updateBookingForReservation(Booking booking) throws EntityNotFoundException, BookingNotPossibleException {

        Optional<Booking> bookingFind = bookingRepository.findById(booking.getBookingId());

        if (!bookingFind.isPresent()) {

            log.error("Le prêt n'existe pas.");

            throw new EntityNotFoundException("Le prêt n'existe pas !");

        }

        if (bookingFind.get().getBookingStatus().equals(BookingStatus.TERMINE.getValue())) {

            log.error("Le prêt est terminé !");

            throw new BookingNotPossibleException("Le prêt est terminé ! Vous ne pouvez plus le prolonger");

        }

        return bookingRepository.saveAndFlush(bookingFind.get());

    }

    @Override
    public List<Booking> getAllBookingByIsbn(String isbn) throws EntityNotFoundException {

        Optional<Book> bookFind = iBookService.getBookByIsbn(isbn);

        if (! bookFind.isPresent()) {

            throw new EntityNotFoundException("Il n'y a pas de livre pour cette référence isbn.");
        }

        return bookingRepository.findAllByBook(bookFind.get());
    }

    @Override
    public List<Booking> getAllBookings() throws EntityNotFoundException {
       return bookingRepository.findAll();
    }

}
