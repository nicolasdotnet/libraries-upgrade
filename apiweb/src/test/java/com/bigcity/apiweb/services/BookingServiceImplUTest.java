/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services;

import com.bigcity.apiweb.dao.IBookingRepository;
import com.bigcity.apiweb.dto.BookingDTO;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.BookingStatus;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.services.interfaces.IBookService;
import com.bigcity.apiweb.services.interfaces.IUserService;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author nicolasdotnet
 */
@ExtendWith(MockitoExtension.class)
public class BookingServiceImplUTest {

    @InjectMocks
    private BookingServiceImpl instance;

    @Mock
    private IBookingRepository iBookingRepository;

    @Mock
    private IBookService iBookService;

    @Mock
    private IUserService iUserService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of register method, of class BookingServiceImpl.
     */
    @Test
    public void testRegisterWhenTheBookingIsAlreadyExist() throws Exception {
        System.out.println("testRegisterWhenTheBookingIsAlreadyExist");

        BookingDTO bookingDto = new BookingDTO();
        bookingDto.setBookIsbn("0000");
        bookingDto.setBookingUserEmail("fake@mail.com");

        doReturn(Optional.of(new Book())).when(iBookService).getBookByIsbn(bookingDto.getBookIsbn());

        doReturn(Optional.of(new User())).when(iUserService).getUserByEmail(bookingDto.getBookingUserEmail());

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
        booking.setCounterExtension("1");

        Optional<Booking> op = Optional.of(booking);

        when(iBookingRepository.findByBookAndBookingUserAndBookingStatusNotLike(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(op);

        try {

            instance.register(bookingDto);

        } catch (Exception e) {

            assertTrue(e instanceof EntityAlreadyExistsException);
            assertEquals(e.getMessage(), "Le prêt existe déjà !");

        }

    }

    /**
     * Test of registerBookingForReservation method, of class
     * BookingServiceImpl.
     */
    @Test
    public void testRegisterWhenPLUSEXEMPLAIRE() throws Exception {
        System.out.println("register");

        Book book = new Book();
        book.setIsbn("00000");
        book.setCopiesAvailable(0);

        User user = new User();
        user.setEmail("fake@mail.com");

        BookingDTO bookingDto = new BookingDTO();
        bookingDto.setBookIsbn("0000");
        bookingDto.setBookingUserEmail("fake@mail.com");;

        Optional<Booking> op = Optional.empty();

        doReturn(Optional.of(book)).when(iBookService).getBookByIsbn(bookingDto.getBookIsbn());
        doReturn(Optional.of(user)).when(iUserService).getUserByEmail(bookingDto.getBookingUserEmail());
        when(iBookingRepository.findByBookAndBookingUserAndBookingStatusNotLike(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(op);

        try {

            instance.register(bookingDto);

        } catch (Exception e) {

            assertTrue(e instanceof BookingNotPossibleException);
            assertEquals(e.getMessage(), "Il n'y a plus d'exemplaire disponible !");

        }

    }

    /**
     * Test of register method, of class BookingServiceImpl.
     */
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");

        ReflectionTestUtils.setField(instance, "bookingDuration", "48");

        Book book = new Book();
        book.setIsbn("00000");
        book.setCopiesAvailable(1);

        User user = new User();
        user.setEmail("fake@mail.com");

        BookingDTO bookingDto = new BookingDTO();
        bookingDto.setBookIsbn("0000");
        bookingDto.setBookingUserEmail("fake@mail.com");

        Optional<Booking> op = Optional.empty();

        Booking booking = new Booking();
        booking.setBookingStatus("ENCOURS");

        doReturn(Optional.of(book)).when(iBookService).getBookByIsbn(bookingDto.getBookIsbn());
        doReturn(Optional.of(user)).when(iUserService).getUserByEmail(bookingDto.getBookingUserEmail());
        when(iBookingRepository.findByBookAndBookingUserAndBookingStatusNotLike(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(op);
        when(iBookingRepository.save(Mockito.any())).thenReturn(booking);

        Booking r = instance.register(bookingDto);

        assertEquals("ENCOURS", r.getBookingStatus());
        Mockito.verify(iBookingRepository).save(Mockito.any());
    }

    /**
     * Test of bookIsBack method, of class BookingServiceImpl.
     */
    @Test
    public void testBookIsBackWhenTheBookingDoesNotExit() throws EntityNotFoundException {
        System.out.println("testBookIsBackWhenTheBookingDoesNotExit");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            Long bookingId = 1L;

            Optional<Booking> op = Optional.empty();

            doReturn(op).when(iBookingRepository).findById(bookingId);

            instance.bookIsBack(bookingId);

        });

        String expectedMessage = "Le prêt n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of bookIsBack method, of class BookingServiceImpl.
     */
    @Test
    public void testBookIsBack() throws EntityNotFoundException {
        System.out.println("testBookIsBack");

        Book book = new Book();
        book.setIsbn("00000");
        book.setCopiesAvailable(0);

        User user = new User();
        user.setEmail("fake@mail.com");

        Booking booking = new Booking();
        booking.setBook(book);
        booking.setBookingUser(user);
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
        booking.setCounterExtension("1");
        Optional<Booking> op = Optional.of(booking);

        Long bookingId = 1L;

        doReturn(op).when(iBookingRepository).findById(bookingId);
        doReturn(booking).when(iBookingRepository).saveAndFlush(op.get());

        Booking r = instance.bookIsBack(bookingId);

        assertEquals("TERMINE", r.getBookingStatus());
        Mockito.verify(iBookingRepository).saveAndFlush(op.get());

    }

    /**
     * Test of extendBooking method, of class BookingServiceImpl.
     */
    @Test
    public void testExtendBookingWhenTheBookingDoesNotExit() throws EntityNotFoundException, BookingNotPossibleException {
        System.out.println("testExtendBookingWhenTheBookingDoesNotExit");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            Long bookingId = 1L;

            Optional<Booking> op = Optional.empty();

            doReturn(op).when(iBookingRepository).findById(bookingId);

            instance.extendBooking(bookingId);
        });

        String expectedMessage = "Le prêt n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of extendBooking method, of class BookingServiceImpl.
     */
    @Test
    public void testExtendBookingWhenTheBookingIsOver() throws EntityNotFoundException, BookingNotPossibleException {
        System.out.println("testExtendBookingWhenTheBookingIsOver");

        Exception exception = assertThrows(BookingNotPossibleException.class, () -> {

            Long bookingId = 1L;

            Booking booking = new Booking();
            booking.setBookingStatus(BookingStatus.TERMINE.getValue());

            Optional<Booking> op = Optional.of(booking);

            doReturn(op).when(iBookingRepository).findById(bookingId);

            instance.extendBooking(bookingId);
        });

        String expectedMessage = "Le prêt est terminé !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of extendBooking method, of class BookingServiceImpl.
     */
    @Test
    public void testExtendBookingWhenTheBookingIsNotValidate() throws EntityNotFoundException, BookingNotPossibleException {
        System.out.println("testExtendBookingWhenTheBookingIsNotValidate");

        Exception exception = assertThrows(BookingNotPossibleException.class, () -> {

            Long bookingId = 1L;

            Booking booking = new Booking();
            booking.setBookingStatus(BookingStatus.RESERVE.getValue());

            Optional<Booking> op = Optional.of(booking);

            doReturn(op).when(iBookingRepository).findById(bookingId);

            instance.extendBooking(bookingId);
        });

        String expectedMessage = "Le prêt n'est pas validé !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of extendBooking method, of class BookingServiceImpl.
     */
    @Test
    public void testExtendBookingWhenTheBookingIsAlreadyExtend() throws EntityNotFoundException, BookingNotPossibleException {
        System.out.println("testExtendBookingWhenTheBookingIsAlreadyExtend");

        Exception exception = assertThrows(BookingNotPossibleException.class, () -> {

            Long bookingId = 1L;

            Booking booking = new Booking();
            booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
            booking.setCounterExtension("1");

            Optional<Booking> op = Optional.of(booking);

            doReturn(op).when(iBookingRepository).findById(bookingId);

            ReflectionTestUtils.setField(instance, "counterExtension", "1");

            instance.extendBooking(bookingId);
        });

        String expectedMessage = "Une prolongation du prêt a déjà été réalisée !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of extendBooking method, of class BookingServiceImpl.
     */
    @Test
    public void testExtendBookingWhenTheDateHasPassed() throws EntityNotFoundException, BookingNotPossibleException {
        System.out.println("testExtendBookingWhenTheDateHasPassed");

        Exception exception = assertThrows(BookingNotPossibleException.class, () -> {

            Long bookingId = 1L;
            Date bookingEndDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.MARCH, 3));

            Booking booking = new Booking();
            booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
            booking.setCounterExtension("0");
            booking.setBookingEndDate(bookingEndDate);
            Optional<Booking> op = Optional.of(booking);

            doReturn(op).when(iBookingRepository).findById(bookingId);

            ReflectionTestUtils.setField(instance, "counterExtension", "1");

            instance.extendBooking(bookingId);
        });

        String expectedMessage = "La date de fin du prêt est dépassé ! Vous ne pouvez pas le prolonger";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of extendBooking method, of class BookingServiceImpl.
     */
    @Test
    public void testExtendBooking() throws EntityNotFoundException, BookingNotPossibleException {
        System.out.println("testExtendBooking");

        Long bookingId = 1L;
        ReflectionTestUtils.setField(instance, "counterExtension", "1");
        ReflectionTestUtils.setField(instance, "bookingDuration", "48");
        Date bookingEndDate = java.sql.Date.valueOf(LocalDate.now().plusDays(2));

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
        booking.setCounterExtension("0");
        booking.setBookingEndDate(bookingEndDate);
        Optional<Booking> op = Optional.of(booking);

        doReturn(op).when(iBookingRepository).findById(bookingId);
        doReturn(booking).when(iBookingRepository).saveAndFlush(op.get());

        Booking r = instance.extendBooking(bookingId);

        assertEquals("PROLONGE", r.getBookingStatus());
        Mockito.verify(iBookingRepository).saveAndFlush(op.get());

    }

    /**
     * Test of registerBookingForReservation method, of class
     * BookingServiceImpl.
     */
    @Test
    public void testRegisterBookingForReservationWhenTheBookingDoesNotExit() throws Exception {
        System.out.println("registerBookingForReservation");

        Book book = new Book();
        book.setIsbn("00000");

        User user = new User();
        user.setEmail("fake@mail.com");

        Booking booking = new Booking();
        booking.setBook(book);
        booking.setBookingUser(user);
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
        booking.setCounterExtension("1");

        Optional<Booking> op = Optional.of(booking);

        doReturn(Optional.of(book)).when(iBookService).getBookByIsbn(booking.getBook().getIsbn());
        doReturn(Optional.of(user)).when(iUserService).getUserByEmail(booking.getBookingUser().getEmail());
        when(iBookingRepository.findByBookAndBookingUserAndBookingStatusNotLike(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(op);

        try {

            instance.registerBookingForReservation(booking);

        } catch (Exception e) {

            assertTrue(e instanceof EntityAlreadyExistsException);
            assertEquals(e.getMessage(), "Le prêt existe déjà !");

        }

    }

    /**
     * Test of registerBookingForReservation method, of class
     * BookingServiceImpl.
     */
    @Test
    public void testRegisterBookingForReservationWhenPLUSEXEMPLAIRE() throws Exception {
        System.out.println("registerBookingForReservation");

        Book book = new Book();
        book.setIsbn("00000");
        book.setCopiesAvailable(0);

        User user = new User();
        user.setEmail("fake@mail.com");

        Booking booking = new Booking();
        booking.setBook(book);
        booking.setBookingUser(user);
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
        booking.setCounterExtension("1");

        Optional<Booking> op = Optional.empty();

        doReturn(Optional.of(book)).when(iBookService).getBookByIsbn(booking.getBook().getIsbn());
        doReturn(Optional.of(user)).when(iUserService).getUserByEmail(booking.getBookingUser().getEmail());
        when(iBookingRepository.findByBookAndBookingUserAndBookingStatusNotLike(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(op);

        try {

            instance.registerBookingForReservation(booking);

        } catch (Exception e) {

            assertTrue(e instanceof BookingNotPossibleException);
            assertEquals(e.getMessage(), "Il n'y a plus d'exemplaire disponible !");

        }

    }

    /**
     * Test of registerBookingForReservation method, of class
     * BookingServiceImpl.
     */
    @Test
    public void testRegisterBookingForReservation() throws Exception {
        System.out.println("registerBookingForReservation");

        ReflectionTestUtils.setField(instance, "bookingDuration", "48");

        Book book = new Book();
        book.setIsbn("00000");
        book.setCopiesAvailable(1);

        User user = new User();
        user.setEmail("fake@mail.com");

        Booking booking = new Booking();
        booking.setBook(book);
        booking.setBookingUser(user);
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
        booking.setCounterExtension("1");

        Optional<Booking> op = Optional.empty();

        Booking b = new Booking();
        b.setBookingStatus(BookingStatus.RESERVE.getValue());

        doReturn(Optional.of(book)).when(iBookService).getBookByIsbn(booking.getBook().getIsbn());
        doReturn(Optional.of(user)).when(iUserService).getUserByEmail(booking.getBookingUser().getEmail());
        when(iBookingRepository.findByBookAndBookingUserAndBookingStatusNotLike(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(op);
        when(iBookingRepository.save(Mockito.any())).thenReturn(b);

        Booking r = instance.registerBookingForReservation(booking);

        assertEquals("RESERVE", r.getBookingStatus());
        Mockito.verify(iBookingRepository).save(Mockito.any());

    }

    /**
     * Test of getBookingByBookAndUserAndBookingStatus method, of class
     * BookingServiceImpl.
     */
    @Test
    public void testGetBookingByBookAndUserAndBookingStatus() throws Exception {
        System.out.println("getBookingByBookAndUserAndBookingStatus");

        Optional<Booking> op = Optional.of(new Booking());

        doReturn(op).when(iBookingRepository).findByBookAndBookingUserAndBookingStatus(Mockito.any(), Mockito.any(), Mockito.any());

        Optional<Booking> r = instance.getBookingByBookAndUserAndBookingStatus(new Book(), new User(), BookingStatus.ENCOURS.getValue());

        assertTrue(r.isPresent());

        Mockito.verify(iBookingRepository).findByBookAndBookingUserAndBookingStatus(Mockito.any(), Mockito.any(), Mockito.any());

    }

    /**
     * Test of getBookingByBookAndUserAndBookingStatus2 method, of class
     * BookingServiceImpl.
     */
    @Test
    public void testGetBookingByBookAndUserAndBookingStatus2() throws Exception {
        System.out.println("getBookingByBookAndUserAndBookingStatus2");

        Optional<Booking> op = Optional.of(new Booking());

        doReturn(op).when(iBookingRepository).findByBookAndBookingUserAndBookingStatusNotLike(Mockito.any(), Mockito.any(), Mockito.any());

        Optional<Booking> r = instance.getBookingByBookAndUserAndBookingStatus2(new Book(), new User(), BookingStatus.ENCOURS.getValue());

        assertTrue(r.isPresent());

        Mockito.verify(iBookingRepository).findByBookAndBookingUserAndBookingStatusNotLike(Mockito.any(), Mockito.any(), Mockito.any());

    }

    /**
     * Test of cancelBookingForReservation method, of class BookingServiceImpl.
     */
    @Test
    public void testCancelBookingForReservationWhenTheBookingDoesNotExit() throws Exception {
        System.out.println("cancelBookingForReservationWhenTheBookingDoesNotExit");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            Long bookingId = 1L;

            Optional<Booking> op = Optional.empty();

            doReturn(op).when(iBookingRepository).findById(bookingId);

            instance.cancelBookingForReservation(bookingId);
        });

        String expectedMessage = "Le prêt n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of cancelBookingForReservation method, of class BookingServiceImpl.
     */
    @Test
    public void testcancelBookingForReservation() throws Exception {
        System.out.println("cancelBookingForReservation");

        Long bookingId = 1L;

        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());
        booking.setBook(new Book());

        Optional<Booking> op = Optional.of(booking);

        doReturn(op).when(iBookingRepository).findById(bookingId);

        doReturn(new Book()).when(iBookService).updateBook(booking.getBook());

        doNothing().when(iBookingRepository).deleteById(op.get().getBookingId());

        instance.cancelBookingForReservation(bookingId);

        Mockito.verify(iBookService).updateBook(booking.getBook());
        Mockito.verify(iBookingRepository).deleteById(op.get().getBookingId());

    }

    /**
     * Test of updateBookingForReservation method, of class BookingServiceImpl.
     */
    @Test
    public void testUpdateBookingWhenTheBookingDoesNotExit() throws Exception {
        System.out.println("updateBooking");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            Booking booking = new Booking();
            booking.setBookingId(00L);

            Optional<Booking> op = Optional.empty();

            doReturn(op).when(iBookingRepository).findById(booking.getBookingId());

            instance.updateBookingForReservation(booking);
        });

        String expectedMessage = "Le prêt n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of updateBookingForReservation method, of class BookingServiceImpl.
     */
    @Test
    public void testUpdateBookingWhenTheBookingIsOver() throws Exception {
        System.out.println("updateBooking");

        Exception exception = assertThrows(BookingNotPossibleException.class, () -> {

            Booking booking = new Booking();
            booking.setBookingId(00L);
            booking.setBookingStatus(BookingStatus.TERMINE.getValue());

            Optional<Booking> op = Optional.of(booking);

            doReturn(op).when(iBookingRepository).findById(booking.getBookingId());

            instance.updateBookingForReservation(booking);
        });

        String expectedMessage = "Le prêt est terminé ! Vous ne pouvez plus le prolonger";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of updateBookingForReservation method, of class BookingServiceImpl.
     */
    @Test
    public void testUpdateBooking() throws Exception {
        System.out.println("updateBooking");

        Booking booking = new Booking();
        booking.setBookingId(00L);
        booking.setBookingStatus(BookingStatus.ENCOURS.getValue());

        Optional<Booking> op = Optional.of(booking);

        doReturn(op).when(iBookingRepository).findById(booking.getBookingId());
        doReturn(booking).when(iBookingRepository).saveAndFlush(op.get());

        Booking r = instance.updateBookingForReservation(booking);

        assertEquals(booking.getBookingId(), r.getBookingId());

        Mockito.verify(iBookingRepository).saveAndFlush(op.get());

    }

    /**
     * Test of getAllBookingByIsbn method, of class BookingServiceImpl.
     */
    @Test
    public void testGetAllBookingByIsbnWhenNotBookForTheIsbn() throws Exception {
        System.out.println("getAllBookingByIsbn");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            String isbn = "0000";

            Optional<Book> book = Optional.empty();

            doReturn(book).when(iBookService).getBookByIsbn(isbn);

            instance.getAllBookingByIsbn(isbn);
        });

        String expectedMessage = "Il n'y a pas de livre pour cette référence isbn.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of getAllBookingByIsbn method, of class BookingServiceImpl.
     */
    @Test
    public void testGetAllBookingByIsbn() throws Exception {
        System.out.println("getAllBookingByIsbn");

        String isbn = "0000";

        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(new Booking());

        Book book = new Book();
        book.setIsbn(isbn);

        Optional<Book> op = Optional.of(book);

        doReturn(op).when(iBookService).getBookByIsbn(isbn);
        doReturn(bookings).when(iBookingRepository).findAllByBook(op.get());

        List<Booking> r = instance.getAllBookingByIsbn(isbn);

        assertEquals(bookings.size(), r.size());

        Mockito.verify(iBookingRepository).findAllByBook(op.get());

    }

    /**
     * Test of getAllBookings method, of class BookingServiceImpl.
     */
    @Test
    public void testGetAllBookings() throws Exception {
        System.out.println("getAllBooking");

        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(new Booking());

        doReturn(bookings).when(iBookingRepository).findAll();

        List<Booking> r = instance.getAllBookings();

        assertEquals(bookings.size(), r.size());

        Mockito.verify(iBookingRepository).findAll();

    }

    /**
     * Test of getAllBookingByOutdated method, of class BookingServiceImpl.
     */
    @Test
    public void testGetAllBookingByOutdated() {
        System.out.println("getAllBookingByOutdated");

        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(new Booking());

        doReturn(bookings).when(iBookingRepository).
                findAllByBookingEndDateLessThanEqualAndBookingStatusNotLike(Mockito.any(), Mockito.any());

        List<Booking> r = instance.getAllBookingByOutdated(LocalDate.now());

        assertEquals(bookings.size(), r.size());

        Mockito.verify(iBookingRepository).
                findAllByBookingEndDateLessThanEqualAndBookingStatusNotLike(Mockito.any(), Mockito.any());

    }

    /**
     * Test of getBooking method, of class BookingServiceImpl.
     */
    @Test
    public void testGetBooking() {
        System.out.println("getBooking");

        Long id = 0L;

        Optional<Booking> op = Optional.of(new Booking());

        doReturn(op).when(iBookingRepository).findById(id);

        Optional<Booking> r = instance.getBooking(id);

        assertTrue(r.isPresent());

        Mockito.verify(iBookingRepository).findById(id);

    }

    /**
     * Test of getAllBookingByUser method, of class BookingServiceImpl.
     */
    @Test
    public void testGetAllBookingByUser() throws EntityNotFoundException {
        System.out.println("getAllBookingByUser");
        String email = "fake@mail.com";

        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(new Booking());

        Optional<User> userFind = Optional.of(new User());

        doReturn(userFind).when(iUserService).getUserByEmail(email);
        doReturn(bookings).when(iBookingRepository).findAllByBookingUser(userFind.get());

        List<Booking> r = instance.getAllBookingByUser(email);

        assertEquals(bookings.size(), r.size());
        Mockito.verify(iBookingRepository).findAllByBookingUser(userFind.get());
    }

}
