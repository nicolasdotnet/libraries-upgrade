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
    public Booking register(BookingDTO bookingDto) throws Exception {

        Optional<Book> book = iBookService.getBookByIsbn(bookingDto.getBookIsbn());

        Optional<User> bookingUser = iUserService.getUserByEmail(bookingDto.getBookingUserEmail());

        Optional<Booking> bookingFind = bookingRepository.findByBookAndBookingUser(book.get(), bookingUser.get());

        if (bookingFind.isPresent()) {

            log.error("La réservation existe déjà !");

            throw new EntityAlreadyExistsException("la réservation existe déjà !");

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
    public Booking bookIsBack(Long bookingId) throws Exception {

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
    public List<Booking> getAllBookingByUser(String email) throws Exception {

        if (email.isEmpty()) {

            throw new EntityNotFoundException("Il n'y a pas de réservations pour cet usager.");
        }

        Optional<User> userFind = iUserService.getUserByEmail(email);

        List<Booking> bookings = bookingRepository.findAllByBookingUser(userFind.get());

        return bookings;

    }

    @Override
    public Booking extendBooking(Long bookingId) throws Exception {

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
//
//        System.out.println("TEST 1");
//
//        System.out.println("old date : )" + bookingFind.get().getBookingEndDate());
//
        LocalDate bookingEndDateOld = Instant.ofEpochMilli(bookingFind.get().getBookingEndDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
//
//        // ne marche pas avec un date sans le time !
//        bookingFind.get().getBookingEndDate().toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate();
//        System.out.println("old date : 2 " + bookingEndDateOld.toString());
//
        LocalDate bookingEndDateNew = bookingEndDateOld.plusDays((Long.valueOf(bookingDuration) + 1));
//
//        System.out.println("TEST 2");
//        System.out.println("new date : " + bookingEndDateNew);

        bookingFind.get().setBookingEndDate(java.sql.Date.valueOf(bookingEndDateNew));

        bookingFind.get().setBookingStatus(BookingStatus.PROLONGE.getValue());

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

    @Override
    public List<Booking> getAllBookingByOutdated(Date dateToday) {
        
        // sup à la date du jour fin la 25 debut relance 26 !!
        
        // patvarable / request par default date du jour de la date de la date user
        
        return bookingRepository.findAllByBookingEndDateLessThanEqualAndBookingStatusNotLike(dateToday,BookingStatus.TERMINE.getValue());

    }

}
