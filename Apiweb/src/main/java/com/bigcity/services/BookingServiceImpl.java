package com.bigcity.services;

import com.bigcity.entity.Book;
import com.bigcity.entity.Booking;
import com.bigcity.entity.BookingStatus;
import com.bigcity.entity.User;
import com.bigcity.exceptions.EntityAlreadyExistsException;
import com.bigcity.exceptions.EntityNotFoundException;
import com.bigcity.exceptions.BookingNotPossibleException;
import com.bigcity.services.interfaces.IBookService;
import com.bigcity.services.interfaces.IUserService;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bigcity.specifications.BookingSpecification;
import com.bigcity.services.interfaces.IBookingService;
import com.bigcity.specifications.BookingCriteria;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.bigcity.dao.IBookingRepository;

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

    @Value("${bookingDuration}")
    private String bookingDuration;

    @Value("${counterExtension}")
    private String counterExtension;

    @Override
    public Booking register(Long librarianId, Long bookingUserId, Long bookId) throws Exception {

        Book book = iBookService.getBook(bookId);

        User bookingUser = iUserService.getUser(bookingUserId);

        User librarian = iUserService.getUser(librarianId);

        Optional<Booking> bookingFind = bookingRepository.findByBookAndBookingUser(book, bookingUser);

        if (bookingFind.isPresent()) {

            log.error("La réservation existe déjà !");

            throw new EntityAlreadyExistsException("la réservation existe déjà !");

        }

        int copiesAvailable = book.getCopiesAvailable();

        if (copiesAvailable == 0) {

            log.error("Il n'y a plus d'exemplaire disponible !");

            throw new BookingNotPossibleException("Il n'y a plus d'exemplaire disponible !");

        }

        // TODO POURQUOI CA MARCHE ?
        book.setCopiesAvailable(--copiesAvailable);

        // save en base => transaction
        // TODO : calcul en fonction du param week + TimeZone ?
//        Date bookingEndDate = java.sql.Date.valueOf(LocalDate.now().plusWeeks(4));
        Date bookingEndDate = java.sql.Date.valueOf(LocalDate.now());

        Booking booking = new Booking();

        booking.setBook(book);
        booking.setBookingDurationDay(bookingDuration);
        booking.setBookingStartDate(new Date());
        booking.setBookingEndDate(bookingEndDate);
        booking.setBookingStatus(BookingStatus.ENCOURS);
        booking.setBookingUser(bookingUser);
        booking.setCounterExtension(counterExtension);
//        booking.setLibrarian(librarian);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking bookIsBack(Long bookingId) throws Exception {

        Optional<Booking> bookingFind = bookingRepository.findById(bookingId);

        if (!bookingFind.isPresent()) {

            log.error("La réservation n'existe pas dans la base.");

            throw new EntityNotFoundException("La réservation n'existe pas !");

        }

        bookingFind.get().setBackBookDate(new Date());
        bookingFind.get().setBookingStatus(BookingStatus.TERMINE);

        int copiesAvailable = bookingFind.get().getBook().getCopiesAvailable();
        bookingFind.get().getBook().setCopiesAvailable(copiesAvailable++);

        return bookingFind.get();

    }

    @Override
    public List<Booking> getOutdatedBookingList() throws Exception {

        List<Booking> bookings = bookingRepository.findAll(BookingSpecification.isExpired(java.sql.Date.valueOf(LocalDate.now())));

        if (bookings.isEmpty()) {

            throw new EntityNotFoundException("Il n'y a pas de réservations dépassées dans la base.");
        }

        return bookings;

    }

    @Override
    public Booking getBooking(Long bookingId) {

        return bookingRepository.findById(bookingId).get();
    }

    @Override
    public void delete(Long bookingId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Booking> getAllBookingByUser(String email) throws Exception {

        Optional<User> userFind = iUserService.getUserByEmail(email);

        List<Booking> bookings = bookingRepository.findAllByBookingUser(userFind.get());

        if (bookings.isEmpty()) {

            throw new EntityNotFoundException("Il n'y a pas de réservations pour ce usagé dans la base.");
        }

        return bookings;

    }

    @Override
    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }

    @Override
    public Booking extendBooking(Long bookingId) throws Exception {

        Optional<Booking> bookingFind = bookingRepository.findById(bookingId);

        if (!bookingFind.isPresent()) {

            log.error("La réservation n'existe pas dans la base.");

            throw new EntityNotFoundException("la réservation n'existe pas !");

        }

        if (!bookingFind.get().getCounterExtension().equals("0")) {

            log.error("Une prolongation du prêt a déjà été réalisée !");

            throw new BookingNotPossibleException("Une prolongation du prêt a déjà été réalisée !");

        }

        System.out.println("TEST 1");

        System.out.println("old date : )" + bookingFind.get().getBookingEndDate());

        LocalDate bookingEndDateOld = Instant.ofEpochMilli(bookingFind.get().getBookingEndDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // ne marche pas avec un date sans le time !
//                bookingFind.get().getBookingEndDate().toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate();
        System.out.println("old date : 2 " + bookingEndDateOld.toString());

        LocalDate bookingEndDateNew = bookingEndDateOld.plusDays(28);

        System.out.println("TEST 2");
        System.out.println("new date : " + bookingEndDateNew);

        bookingFind.get().setBookingEndDate(java.sql.Date.valueOf(bookingEndDateNew));

        bookingFind.get().setCounterExtension("1");

        return bookingRepository.saveAndFlush(bookingFind.get());
    }

    @Override
    public Page<Booking> getAllBookingsByCriteria(BookingCriteria bookingCriteria, int page, int size) {

        bookingCriteria.setBookTitle("".equals(bookingCriteria.getBookTitle()) ? null : bookingCriteria.getBookTitle());
        bookingCriteria.setBookingId(bookingCriteria.getBookingId() == 0 ? null : bookingCriteria.getBookingId());
        bookingCriteria.setBookingStatus("".equals(bookingCriteria.getBookingStatus()) ? null : bookingCriteria.getBookingStatus());
        bookingCriteria.setBookingUserEmail("".equals(bookingCriteria.getBookingUserEmail()) ? null : bookingCriteria.getBookingUserEmail());

        BookingSpecification bookSpecification = new BookingSpecification(bookingCriteria);

        return bookingRepository.findAll(bookSpecification, PageRequest.of(page, size));
    }

}
