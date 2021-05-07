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
    private IUserService iUserService;

    @Value("${bookingDurationInDay}")
    private String bookingDuration;

    @Value("${bookingCounterExtension}")
    private String counterExtension;

    @Override
    public Booking register(BookingDTO bookingDto) throws EntityAlreadyExistsException, BookingNotPossibleException, EntityNotFoundException {

        Book book = iBookService.getBookByIsbn(bookingDto.getBookIsbn());

        Optional<User> bookingUser = iUserService.getUserByEmail(bookingDto.getBookingUserEmail());

        Optional<Booking> bookingFind = bookingRepository.findByBookAndBookingUserAndBookingStatusNotLike(book, bookingUser.get(), BookingStatus.TERMINE.getValue());

        if (bookingFind.isPresent()) {

            log.error("La réservation existe déjà !");

            throw new EntityAlreadyExistsException("la réservation existe déjà !");

        }

        int copiesAvailable = book.getCopiesAvailable();

        if (copiesAvailable == 0) {

            log.error("Il n'y a plus d'exemplaire disponible !");

            throw new BookingNotPossibleException("Il n'y a plus d'exemplaire disponible !");

        }

        book.setCopiesAvailable(--copiesAvailable);

        Date bookingEndDate = java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()).plusDays(Long.valueOf(bookingDuration) + 1));

        Booking booking = new Booking();

        booking.setBook(book);
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

            log.error("La réservation n'existe pas dans la base.");

            throw new EntityNotFoundException("La réservation n'existe pas !");

        }

        bookingFind.get().setBackBookDate(new Date());
        bookingFind.get().setBookingStatus(BookingStatus.TERMINE.getValue());

        int copiesAvailable = bookingFind.get().getBook().getCopiesAvailable();
        bookingFind.get().getBook().setCopiesAvailable(++copiesAvailable);

        return bookingFind.get();

    }

    @Override
    public Booking getBooking(Long bookingId) {

        return bookingRepository.findById(bookingId).get();
    }

    @Override
    public List<Booking> getAllBookingByUser(String email) throws EntityNotFoundException {

        if (email.isEmpty()) {

            throw new EntityNotFoundException("Il n'y a pas de réservations pour cet usager.");
        }

        Optional<User> userFind = iUserService.getUserByEmail(email);

        List<Booking> bookings = bookingRepository.findAllByBookingUser(userFind.get());

        return bookings;

    }

    @Override
    public Booking extendBooking(Long bookingId) throws EntityNotFoundException, BookingNotPossibleException {

        Optional<Booking> bookingFind = bookingRepository.findById(bookingId);

        if (!bookingFind.isPresent()) {

            log.error("La réservation n'existe pas.");

            throw new EntityNotFoundException("la réservation n'existe pas !");

        }

        if (bookingFind.get().getBookingStatus().equals(BookingStatus.TERMINE.getValue())) {

            log.error("Le prêt est terminé !");

            throw new BookingNotPossibleException("Le prêt est terminé ! Vous ne pouvez plus le prolonger");

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
        bookingCriteria.setBookingId("".equals(bookingId) ? null : Long.parseLong(bookingId));
        bookingCriteria.setBookingStatus("".equals(bookingStatus) ? null : bookingStatus);
        bookingCriteria.setBookingUserEmail("".equals(bookingUserEmail) ? null : bookingUserEmail);

        BookingSpecification bookSpecification = new BookingSpecification(bookingCriteria);

        return bookingRepository.findAll(bookSpecification, PageRequest.of(page, size));
    }

    @Override
    public List<Booking> getAllBookingByOutdated(LocalDate dateBookingOut) {

        return bookingRepository.findAllByBookingEndDateLessThanEqualAndBookingStatusNotLike(java.sql.Date.valueOf(dateBookingOut), BookingStatus.TERMINE.getValue());

    }

}
