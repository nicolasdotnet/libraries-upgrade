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
import java.net.URI;
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
    @PostMapping("/api/bookings")
    public ResponseEntity saveBooking(@Valid @RequestBody BookingDTO bookingDto) throws Exception {

        log.debug("saveBooking()");

        // TODO ajouter securité

        Booking bookingSave = iBookingService.register(bookingDto.getLibrarianId(), bookingDto.getBookingUserId(), bookingDto.getBookId());

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
    @GetMapping("/api/booking/{id}")
    public ResponseEntity showBooking(@PathVariable("id") int id) {

        log.debug("showBooking() id: {}", id);

        return ResponseEntity.ok(iBookingService.getBooking(Long.valueOf(id)));

    }

    @ApiOperation("Récupère l'ensemble des prêts de la base ou récupèrer une liste de prêt a partir d'un mot clé sur le identifiant de l'usagé")
    @GetMapping("/api/bookings")
    public ResponseEntity showAllBookings(@RequestParam(defaultValue = " ") String email) throws Exception {

        log.debug("showBookings()", email);

        List<Booking> bookings = null;

        if (email.equals(" ")) {

            bookings = iBookingService.getAllBookings();

            return ResponseEntity.ok(bookings);

        }

        bookings = iBookingService.getAllBookingByUser(email);

        return ResponseEntity.ok(bookings);

    }

    @ApiOperation("Récupère l'ensemble des prêts dont la date est dépasée")
    @GetMapping("/api/bookings/outdate")
    public ResponseEntity showAllBookingsOutDate() throws Exception {

        log.debug("showBookingsOutDate");

        return ResponseEntity.ok(iBookingService.getOutdatedBookingList());

    }

    //TODO : comment construire les url pour action prolonger booking et action retour du libre ?
    @ApiOperation("Mettre à jour un prêt à partir de son ID présent dans la base")
    @PutMapping("/api/booking/{id}")
    public ResponseEntity updateBooking(@PathVariable("id") int id) {

        log.debug("updateBook() id: {}", id);

        Booking bookingFind = null;

        try {
//            bookingFind = iBookingService.;
        } catch (Exception ex) {

            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(bookingFind);

    }

}
