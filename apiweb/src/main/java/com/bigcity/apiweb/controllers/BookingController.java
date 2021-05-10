/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.controllers;

import com.bigcity.apiweb.dto.BookingDTO;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.services.interfaces.IBookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.time.LocalDate;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author nicolasdotnet
 */
@Api(tags = "API pour les opérations sur les prêts de livre.")
@RestController
public class BookingController {

    private static final Logger log = LogManager.getLogger(BookingController.class);

    @Autowired
    private IBookingService iBookingService;

    @ApiOperation("Enregister un nouveau prêt")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "prêt enregistré", response = BookingDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookingDTO.class),
        @ApiResponse(code = 409, message = "le prêt existe déjà dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire"),
        @ApiResponse(code = 500, message = "erreur dans la requéte")
    })
    @PostMapping("/api/user/bookings")
    public ResponseEntity saveBooking(@Valid @RequestBody BookingDTO bookingDto) throws
            BookingNotPossibleException, EntityAlreadyExistsException, EntityNotFoundException {

        log.debug("saveBooking()");

        Booking bookingSave = iBookingService.register(bookingDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookingSave.getBookingId())
                .toUri();

        return ResponseEntity.created(location)
                .body(bookingSave);

    }

    @ApiOperation("Récupère un prêt grâce à son ID à condition que celui-ci soit enregistré !")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = 404, message = "le prêt n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/bookings/{id}")
    public ResponseEntity showBooking(@PathVariable("id") int id) {

        log.debug("showBooking() id: {}", id);

        return ResponseEntity.ok(iBookingService.getBooking(Long.valueOf(id)));

    }

    @ApiOperation("Prolonger un prêt à partir de son ID présent dans la base")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "prolongement du prêt enregistré", response = BookingDTO.class),
        @ApiResponse(code = 404, message = "le prêt n'existe pas dans la base"),
        @ApiResponse(code = 409, message = "le prêt ne peut plus être prolongé"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/bookings/{id}")
    public ResponseEntity extendBooking(@PathVariable("id") int id) throws BookingNotPossibleException, EntityNotFoundException {

        log.debug("extendBooking() id: {}", id);

        return ResponseEntity.ok(iBookingService.extendBooking(Long.valueOf(id)));

    }

    @ApiOperation("Récupère des prêts en fonction du numéro de réservation, de son statut, "
            + "de l'email de l'usagé à l'origine du prêt et du titre du livre")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookingDTO.class),
        @ApiResponse(code = 403, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/librarian/bookings")
    public Page<Booking> showAllBookingsByCriteria(
            @RequestParam(name = "bookingId", defaultValue = "") String bookingId,
            @RequestParam(name = "bookingStatus", defaultValue = "") String bookingStatus,
            @RequestParam(name = "bookingUserEmail", defaultValue = "") String bookingUserEmail,
            @RequestParam(name = "bookTitle", defaultValue = "") String bookTitle,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws Exception {

        log.debug("showAllBookingsByCriteria");

        return iBookingService.getAllBookingsByCriteria(bookingId, bookingStatus, bookingUserEmail, bookTitle, page, size);
    }

    @ApiOperation("Récupère une liste de prêts a partir de l'email de l'usagé")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie dans la demande", response = BookingDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/bookings")
    public ResponseEntity showAllBookingsByUser(@RequestParam String email) throws EntityNotFoundException {

        log.debug("showAllBookingsByUser()", email);

        return ResponseEntity.ok(iBookingService.getAllBookingByUser(email));

    }

    @ApiOperation("Récupère une liste de prêts de l'isbn d'un livre")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie dans la demande", response = BookingDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/bookings/book/{isbn}")
    public ResponseEntity showAllBookingsByIsbn(@PathVariable("isbn") String isbn) throws EntityNotFoundException {

        log.debug("showAllBookingsByIsbn()", isbn);

        return ResponseEntity.ok(iBookingService.getAllBookingByIsbn(isbn));

    }

    @ApiOperation("Enregistrer un retour de livre à la bibliothéque suite à un prêt.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "retour du livre enregistré", response = BookingDTO.class),
        @ApiResponse(code = 404, message = "le prêt n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/bookings/{id}/back")
    public ResponseEntity backFromTheBook(@PathVariable("id") int id)
            throws EntityNotFoundException, EntityAlreadyExistsException, BookingNotPossibleException {

        log.debug("backFromTheBook() id: {}", id);

        return ResponseEntity.ok(iBookingService.ManagementOfBookReturns(Long.valueOf(id)));

    }

    @ApiOperation("Récupère une liste de prêts non retourné à une date.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie dans la demande", response = BookingDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/bookingout")
    public ResponseEntity getBookingOut(@RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateBookingOut) {

        log.debug("getBookingOut() dateBookingOut: {}", dateBookingOut);

        return ResponseEntity.ok(iBookingService.getAllBookingByOutdated(dateBookingOut));
    }

    @GetMapping("/api/librarian/allbookings")
    public ResponseEntity showAllBookings() throws EntityNotFoundException {

        log.debug("showAllBookings");

        return ResponseEntity.ok(iBookingService.getAllBookings());

    }

}
