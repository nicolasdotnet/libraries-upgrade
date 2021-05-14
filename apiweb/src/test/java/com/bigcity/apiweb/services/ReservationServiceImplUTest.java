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
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.exceptions.ReservationNotPossibleException;
import com.bigcity.apiweb.services.interfaces.IBookService;
import com.bigcity.apiweb.services.interfaces.IBookingService;
import com.bigcity.apiweb.services.interfaces.IUserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author nicolasdotnet
 */
@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplUTest {
    
    @InjectMocks
    private ReservationServiceImpl instance;
    
    @Mock
    private IReservationRepository iReservationRepository;
    
    @Mock
    private IBookingService iBookingservice;
    
    @Mock
    private IBookService iBookService;
    
    @Mock
    private IUserService iUserService;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of cancelReservation method, of class ReservationServiceImpl.
     */
    @Test
    public void testCancelReservationWhenTheReservationDoesNotExit() throws EntityNotFoundException {
        System.out.println("cancelReservation");
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            
            Long reservationId = 1L;
            
            Optional<Reservation> op = Optional.empty();
            
            doReturn(op).when(iReservationRepository).findById(reservationId);
            
            instance.cancelReservation(reservationId);
        });
        
        String expectedMessage = "La réservation n'existe pas !";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of cancelReservation method, of class ReservationServiceImpl.
     */
    @Test
    public void testCancelReservation() throws EntityNotFoundException, EntityAlreadyExistsException, BookingNotPossibleException {
        System.out.println("cancelReservation");
        
        Long reservationId = 1L;
        
        Reservation reservation = new Reservation();
        reservation.setBook(new Book());
        Optional<Reservation> op = Optional.of(reservation);
        
        doReturn(op).when(iReservationRepository).findById(reservationId);
        
        instance.cancelReservation(reservationId);
    }

    /**
     * Test of validateReservation method, of class ReservationServiceImpl.
     */
    @Test
    public void testValidateReservationWhenTheReservationDoesNotExit() throws Exception {
        System.out.println("validateReservation");
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            
            Long reservationId = 1L;
            
            Optional<Reservation> op = Optional.empty();
            
            doReturn(op).when(iReservationRepository).findById(reservationId);
            
            instance.validateReservation(reservationId);
        });
        
        String expectedMessage = "La réservation n'existe pas !";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of validateReservation method, of class ReservationServiceImpl.
     */
    @Test
    public void testValidateReservationWhenNotBooking() throws Exception {
        System.out.println("validateReservation");
        
        Long reservationId = 1L;
        
        Optional<Reservation> o = Optional.of(new Reservation());
        doReturn(o).when(iReservationRepository).findById(reservationId);
        
        Optional<Booking> op = Optional.empty();
        doReturn(op).when(iBookingservice).getBookingByBookAndUserAndBookingStatus(Mockito.any(), Mockito.any(), Mockito.any());
        
        try {
            
            instance.validateReservation(reservationId);
            
        } catch (Exception e) {
            
            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "Vous ne pouvez pas valider ! Aucun exemplaire disponible");
            
        }
        
    }

//    /**
//     * Test of reservationsNext method, of class ReservationServiceImpl.
//     */
//    @Test
//    public void testReservationsNext() throws Exception {
//        System.out.println("reservationsNext");
//
//        Book book = new Book();
//
//        Reservation reservation1 = new Reservation();
//        reservation1.setBook(book);
//        reservation1.setReservationDate(new Date());
//        reservation1.setReservationId(001L);
//        reservation1.setReservationStatus("statut");
//        reservation1.setReservationUser(new User());
//
//        Reservation reservation2 = new Reservation();
//        reservation2.setBook(book);
//        reservation2.setReservationDate(new Date());
//        reservation2.setReservationId(002L);
//        reservation2.setReservationStatus("statut");
//        reservation2.setReservationUser(new User());
//
//        List<Reservation> reservationsEdit = new ArrayList<>();
//        reservationsEdit.add(reservation1);
//        reservationsEdit.add(reservation2);
//
//        List<Reservation> expResult = new ArrayList<>();
//        reservationsEdit.add(reservation1);
//        reservationsEdit.add(reservation2);
//
//        Collection<Reservation> reservations = new ArrayList<>();
//        reservations.add(reservation1);
//        reservations.add(reservation2);
//
//        book.setReservations(reservations);
//        
//        Reservation reservation = new Reservation();
//        reservation.setBook(new Book());
//        reservation.setReservationUser(new User());
//        reservation.setReservationId(00L);
//
//        Optional<Reservation> op = Optional.of(reservation);
//        doReturn(op).when(iReservationRepository).findById(Mockito.any());
//        doReturn(op.get()).when(iReservationRepository).saveAndFlush(Mockito.any());
//
//        List<Reservation> result = instance.reservationsNext(reservationsEdit);
//        assertEquals(expResult.get(0).getReservationId(), result.get(0).getReservationId());
//
//    }
    /**
     * Test of activateReservation method, of class ReservationServiceImpl.
     */
    @Test
    public void testActivateReservation() throws Exception {
        
        System.out.println("activateReservation");
        
        Long reservationId = 1L;
        
        Reservation reservation = new Reservation();
        reservation.setBook(new Book());
        reservation.setReservationUser(new User());
        reservation.setReservationId(00L);
        
        Optional<Reservation> op = Optional.of(reservation);
        
        doReturn(op).when(iReservationRepository).findById(reservationId);
        doReturn(op.get()).when(iReservationRepository).saveAndFlush(Mockito.any());
        doReturn(new Booking()).when(iBookingservice).registerBookingForReservation(Mockito.any());
        
        Reservation r = instance.activateReservation(reservationId);
        System.out.println("date reservation validate : " + r.getValidateReservationDate());
        
        Mockito.verify(iReservationRepository).findById(reservationId);
        Mockito.verify(iReservationRepository).saveAndFlush(Mockito.any());
        
    }

    /**
     * Test of activateReservation method, of class ReservationServiceImpl.
     */
    @Test
    public void testActivateReservationWhenTheReservationDoesNotExit() throws Exception {
        System.out.println("activateReservation");
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            
            Long reservationId = 1L;
            
            Optional<Reservation> op = Optional.empty();
            
            doReturn(op).when(iReservationRepository).findById(reservationId);
            
            instance.activateReservation(reservationId);
        });
        
        String expectedMessage = "La réservation n'existe pas !";
        String actualMessage = exception.getMessage();
        
    }

    /**
     * Test of getAllReservationByUser method, of class ReservationServiceImpl.
     */
    @Test
    public void testGetAllReservationByUserWhenNotReservation() throws Exception {
        System.out.println("getAllReservationByUser");
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            
            String email = "fake@mail.com";
            
            Optional<User> op = Optional.empty();
            
            doReturn(op).when(iUserService).getUserByEmail(email);
            
            instance.getAllReservationByUser(email);
        });
        
        String expectedMessage = "Il n'y a pas de prêt pour cet usager.";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of getAllReservationByUser method, of class ReservationServiceImpl.
     */
    @Test
    public void testGetAllReservationByUser() throws Exception {
        
        System.out.println("getAllReservationByUser");
        
        String email = "fake@mail.com";
        
        User user = new User();
        Optional<User> op = Optional.of(user);
        
        Reservation reservation = new Reservation();
        reservation.setReservationId(00L);
        List<Reservation> reservations = new ArrayList<>();
        
        doReturn(op).when(iUserService).getUserByEmail(email);
        doReturn(reservations).when(iReservationRepository).findAllByReservationUser(op.get());
        
        List<Reservation> r = instance.getAllReservationByUser(email);
        
        assertEquals(reservations.size(), r.size());
        
        Mockito.verify(iReservationRepository).findAllByReservationUser(op.get());
        
    }

    /**
     * Test of getReservation method, of class ReservationServiceImpl.
     */
    @Test
    public void testGetReservation() throws Exception {
        
        System.out.println("getReservation");
        
        Long reservationId = 1L;
        
        Reservation reservation = new Reservation();
        reservation.setReservationId(00L);
        Optional<Reservation> op = Optional.of(reservation);
        
        doReturn(op).when(iReservationRepository).findById(reservationId);
        
        Reservation r = instance.getReservation(reservationId);
        
        assertEquals(00L, r.getReservationId());
        
        Mockito.verify(iReservationRepository).findById(reservationId);
        
    }

    /**
     * Test of getAllReservationByIsbn method, of class ReservationServiceImpl.
     */
    @Test
    public void testGetAllReservationByIsbnWhenBookNotExist() throws Exception {
        System.out.println("getAllReservationByIsbn");
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            
            String isbn = "1234";
            
            Optional<Book> op = Optional.empty();
            
            doReturn(op).when(iBookService).getBookByIsbn(isbn);
            
            instance.getAllReservationByIsbn(isbn);
        });
        
        String expectedMessage = "Il n'y a pas de livre pour cette référence isbn.";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of getAllReservationByIsbn method, of class ReservationServiceImpl.
     */
    @Test
    public void testGetAllReservationByIsbn() throws Exception {
        System.out.println("getAllReservationByIsbn");
        
        String isbn = "1234";
        
        List<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(new Reservation());
        
        Book book = new Book();
        Optional<Book> op = Optional.of(book);
        
        doReturn(op).when(iBookService).getBookByIsbn(isbn);
        doReturn(reservations).when(iReservationRepository).findAllByBook(book);
        
        List<Reservation> r = instance.getAllReservationByIsbn(isbn);
        
        assertEquals(reservations.size(), r.size());
        
        Mockito.verify(iReservationRepository).findAllByBook(book);
        
    }

    /**
     * Test of register method, of class ReservationServiceImpl.
     */
    @Test
    public void testRegisterWhenThereAreStillCopieAvailable() throws EntityNotFoundException {
        System.out.println("register");
        
        Exception exception = assertThrows(ReservationNotPossibleException.class, () -> {
            
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setBookIsbn("000000");
            reservationDTO.setReservationUserEmail("fake@mail.com");
            
            Book book = new Book();
            book.setCopiesAvailable(1);
            Optional<Book> opBook = Optional.of(book);
            
            User user = new User();
            Optional<User> opUser = Optional.of(user);
            
            doReturn(opBook).when(iBookService).getBookByIsbn(reservationDTO.getBookIsbn());
            doReturn(opUser).when(iUserService).getUserByEmail(reservationDTO.getReservationUserEmail());
            
            instance.register(reservationDTO);
            
        });
        
        String expectedMessage = "Il y a encore des exemplaires disponibles !";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of register method, of class ReservationServiceImpl.
     */
    @Test
    public void testRegisterWhenBookingInProgress() throws EntityNotFoundException {
        System.out.println("register");
        
        Exception exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setBookIsbn("000000");
            reservationDTO.setReservationUserEmail("fake@mail.com");
            
            Book book = new Book();
            book.setCopiesAvailable(0);
            Optional<Book> opBook = Optional.of(book);
            
            User user = new User();
            Optional<User> opUser = Optional.of(user);
            
            Booking booking = new Booking();
            Optional<Booking> opBooking = Optional.of(booking);
            
            doReturn(opBook).when(iBookService).getBookByIsbn(reservationDTO.getBookIsbn());
            doReturn(opUser).when(iUserService).getUserByEmail(reservationDTO.getReservationUserEmail());
            doReturn(opBooking).when(iBookingservice)
                    .getBookingByBookAndUserAndBookingStatus2(Mockito.any(), Mockito.any(), Mockito.any());
            
            instance.register(reservationDTO);
            
        });
        
        String expectedMessage = "Vous avez un prêt en cours avec ce livre !";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of register method, of class ReservationServiceImpl.
     */
    @Test
    public void testRegisterWhenNoMoreReservationAvailable() throws EntityNotFoundException {
        System.out.println("register");
        
        Exception exception = assertThrows(ReservationNotPossibleException.class, () -> {
            
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setBookIsbn("000000");
            reservationDTO.setReservationUserEmail("fake@mail.com");
            
            Book book = new Book();
            book.setCopiesAvailable(0);
            book.setNumberOfCopies(1);
            Optional<Book> opBook = Optional.of(book);
            
            User user = new User();
            Optional<User> opUser = Optional.of(user);
            
            Booking booking = new Booking();
            Optional<Booking> opBooking = Optional.empty();
            
            List<Reservation> reservations = new ArrayList<>();
            Reservation reservation = new Reservation();
            Reservation reservation2 = new Reservation();
            reservations.add(reservation);
            reservations.add(reservation2);
            
            doReturn(opBook).when(iBookService).getBookByIsbn(reservationDTO.getBookIsbn());
            doReturn(opUser).when(iUserService).getUserByEmail(reservationDTO.getReservationUserEmail());
            doReturn(opBooking).when(iBookingservice)
                    .getBookingByBookAndUserAndBookingStatus2(Mockito.any(), Mockito.any(), Mockito.any());
            doReturn(reservations).when(iReservationRepository).findAllByBook(Mockito.any());
            
            instance.register(reservationDTO);
            
        });
        
        String expectedMessage = "Il n'y a plus de réservation disponible pour réserver ce livre !";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of register method, of class ReservationServiceImpl.
     */
    @Test
    public void testRegisterWhenTheReservationIsAlreadyExiste() throws EntityNotFoundException {
        System.out.println("register");
        
        Exception exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setBookIsbn("000000");
            reservationDTO.setReservationUserEmail("fake@mail.com");
            
            Book book = new Book();
            book.setCopiesAvailable(0);
            book.setNumberOfCopies(1);
            Optional<Book> opBook = Optional.of(book);
            
            User user = new User();
            Optional<User> opUser = Optional.of(user);
            
            Booking booking = new Booking();
            Optional<Booking> opBooking = Optional.empty();
            
            List<Reservation> reservations = new ArrayList<>();
            Reservation reservation = new Reservation();
            Reservation reservation2 = new Reservation();
            reservations.add(reservation);
            reservations.add(reservation2);
            
            doReturn(opBook).when(iBookService).getBookByIsbn(reservationDTO.getBookIsbn());
            doReturn(opUser).when(iUserService).getUserByEmail(reservationDTO.getReservationUserEmail());
            doReturn(opBooking).when(iBookingservice)
                    .getBookingByBookAndUserAndBookingStatus2(Mockito.any(), Mockito.any(), Mockito.any());
            doReturn(Optional.of(new Reservation())).when(iReservationRepository).findByBookAndReservationUser(Mockito.any(), Mockito.any());
            
            instance.register(reservationDTO);
            
        });
        
        String expectedMessage = "La réservation existe déjà !";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of register method, of class ReservationServiceImpl.
     */
    @Test
    public void testRegister() throws EntityNotFoundException, EntityAlreadyExistsException, ReservationNotPossibleException {
        System.out.println("register");
        
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setBookIsbn("000000");
        reservationDTO.setReservationUserEmail("fake@mail.com");
        
        Book book = new Book();
        book.setCopiesAvailable(0);
        book.setNumberOfCopies(1);
        Optional<Book> opBook = Optional.of(book);
        
        User user = new User();
        user.setEmail("fake@mail.com");
        Optional<User> opUser = Optional.of(user);
        
        Booking booking = new Booking();
        Optional<Booking> opBooking = Optional.empty();
        
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setReservationStatus("ENCOURS");
        reservations.add(reservation);
        
        doReturn(opBook).when(iBookService).getBookByIsbn(reservationDTO.getBookIsbn());
        doReturn(opUser).when(iUserService).getUserByEmail(reservationDTO.getReservationUserEmail());
        doReturn(opBooking).when(iBookingservice)
                .getBookingByBookAndUserAndBookingStatus2(Mockito.any(), Mockito.any(), Mockito.any());
        doReturn(reservations).when(iReservationRepository).findAllByBook(Mockito.any());
        doReturn(reservation).when(iReservationRepository).save(Mockito.any());
        
        Reservation r = instance.register(reservationDTO);
        
        assertEquals("ENCOURS", r.getReservationStatus());
        
        Mockito.verify(iReservationRepository).findAllByBook(book);
    }
    
    @Test
    public void testManagementOfReservations() throws EntityNotFoundException, EntityAlreadyExistsException, BookingNotPossibleException {
        
        LocalDate date = LocalDate.now();
        List<Reservation> reservations = new ArrayList<>();
        
        doReturn(reservations).when(iReservationRepository).
                findAllByValidateReservationDateBeforeAndReservationStatus(Mockito.any(), Mockito.any());
        
        List<Reservation> r = instance.ManagementOfReservations(date);
        
        assertTrue(r.isEmpty());
        
    }
    
}
