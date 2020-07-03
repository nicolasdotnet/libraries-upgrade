/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.controllers;

import com.bigcity.dto.BookingDTO;
import com.bigcity.entity.Booking;
import com.bigcity.services.interfaces.IBookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "API pour les opérations sur les prêts de livre par un usagé.")
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
    public ResponseEntity saveBooking(@Valid @RequestBody BookingDTO bookingDto) throws Exception {

        log.debug("saveBooking()");
        
        Booking bookingSave = iBookingService.register(bookingDto);

//code 201, ajouter l'URI 
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
    public ResponseEntity extendBooking(@PathVariable("id") int id) throws Exception {

        log.debug("extendBooking() id: {}", id);

        return ResponseEntity.ok(iBookingService.extendBooking(Long.valueOf(id)));

    }

//    @ApiOperation("Récupère l'ensemble des livres de la base en fonction du titre ou de l'auteur ou du numero ISBN")
//    @ApiResponses(value = {
//        @ApiResponse(code = SC_OK, message = "ok", response = BookingDTO.class),
//        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookingDTO.class),
//        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
//    })
//    @GetMapping("/api/librarian/bookings")
//    public Page<Booking> showAllBookingsByCriteria(@RequestBody BookingCriteria bookingCriteria, int page, int size) throws Exception {
//
//        log.debug("showAllBookingsByCriteria", bookingCriteria);
//
//        return iBookingService.getAllBookingsByCriteria(bookingCriteria, page, size);
//    }
    @ApiOperation("Récupère l'ensemble des prêts de la base ou récupèrer une liste de prêt a partir d'un mot clé sur le identifiant/email de l'usagé")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie dans la demande", response = BookingDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/bookings")
    public ResponseEntity showAllBookings(@RequestParam(defaultValue = " ") String email) throws Exception {

        log.debug("showAllBookings()", email);

        List<Booking> bookings = null;

        if (email.equals(" ")) {

            bookings = iBookingService.getAllBookings();

            return ResponseEntity.ok(bookings);

        }

        bookings = iBookingService.getAllBookingByUser(email);

        return ResponseEntity.ok(bookings);

    }

    @ApiOperation("Enregistrer un retour de livre à la bibliothéque suite à un prêt.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "retour du livre enregistré", response = BookingDTO.class),
        @ApiResponse(code = 404, message = "le prêt n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/bookings/{id}/back")
    public ResponseEntity backFromTheBook(@PathVariable("id") int id) throws Exception {

        log.debug("backFromTheBook() id: {}", id);

        return ResponseEntity.ok(iBookingService.bookIsBack(Long.valueOf(id)));

    }
    
    @GetMapping("/api/user/alpha")
    public ResponseEntity getBookingOut(){
        
        Date dateToday = new Date();
        
        
        return ResponseEntity.ok(iBookingService.getAllBookingByOutdated(dateToday));
    }

}
