/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.controllers;

import com.bigcity.apiweb.dto.ReservationDTO;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.exceptions.ReservationNotPossibleException;
import com.bigcity.apiweb.services.interfaces.IReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
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
@Api(tags = "API pour les opérations sur les réservations de livre.")
@RestController
public class ReservationController {

    private static final Logger log = LogManager.getLogger(ReservationController.class);

    @Autowired
    private IReservationService iReservationService;

    @GetMapping("/api/user/reservationsValidate")
    public ResponseEntity getReservationsValidates(@RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateValidate) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        log.debug("getReservationsValidates dateToday: {}", dateValidate);

//        List<Reservation> reponse = iReservationService.ManagementOfReservations(java.sql.Date.valueOf(dateValidate));
//
//        System.out.println("REPONSE API RESERVATION : " + reponse.size());

        return ResponseEntity.ok(iReservationService.ManagementOfReservations(java.sql.Date.valueOf(dateValidate)));
    }

    @ApiOperation("Récupère une liste de réservations a partir de l'email de l'usagé")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = ReservationDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie dans la demande", response = ReservationDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/reservations")
    public ResponseEntity showAllReservationsByUser(@RequestParam String email) throws EntityNotFoundException {

        log.debug("showAllReservationsByUser()", email);

        return ResponseEntity.ok(iReservationService.getAllReservationByUser(email));

    }

    @ApiOperation("Récupère une liste de réservations a partir de l'isbn d'un livre")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = ReservationDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie dans la demande", response = ReservationDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/reservations/book/{isbn}")
    public ResponseEntity showAllReservationsByIsbn(@PathVariable("isbn") String isbn) throws EntityNotFoundException {

        log.debug("showAllReservationsByIsbn()", isbn);

        return ResponseEntity.ok(iReservationService.getAllReservationByIsbn(isbn));

    }

    @ApiOperation("Enregistrer une nouvelle réservation")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "réservation enregistrée", response = ReservationDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = ReservationDTO.class),
        @ApiResponse(code = 409, message = "la réservation existe déjà dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire"),
        @ApiResponse(code = 500, message = "erreur dans la requéte")
    })
    @PostMapping("/api/user/reservations")
    public ResponseEntity saveReservation(@Valid @RequestBody ReservationDTO reservationDto) throws ReservationNotPossibleException, EntityAlreadyExistsException, EntityNotFoundException {

        log.debug("saveReservation()");

        Reservation reservationSave = iReservationService.register(reservationDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservationSave.getReservationId())
                .toUri();

        return ResponseEntity.created(location)
                .body(reservationSave);

    }

    @ApiOperation("Récupère un prêt grâce à son ID à condition que celui-ci soit enregistré !")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = ReservationDTO.class),
        @ApiResponse(code = 404, message = "le prêt n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/reservations/{id}")
    public ResponseEntity showReservation(@PathVariable("id") int id) {

        log.debug("showReservation() id: {}", id);

        return ResponseEntity.ok(iReservationService.getReservation(Long.valueOf(id)));

    }

    @ApiOperation("Enregistrer une validation une réservation sur un livre.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "valider une réservation enregistrée", response = ReservationDTO.class),
        @ApiResponse(code = 404, message = "la réservation n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/reservations/{id}/validate")
    public ResponseEntity validateReservation(@PathVariable("id") int id) throws BookingNotPossibleException, EntityNotFoundException {

        log.debug("validateReservation() id: {}", id);

        return ResponseEntity.ok(iReservationService.validateReservation(Long.valueOf(id)));

    }

    @ApiOperation("Enregistrer l'annulation d'une réservation sur un livre.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "annulation de la réservation enregistrée", response = ReservationDTO.class),
        @ApiResponse(code = 404, message = "la réservation n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/reservations/{id}/cancel")
    public void cancelReservation(@PathVariable("id") int id) throws EntityNotFoundException,
            EntityAlreadyExistsException, BookingNotPossibleException {

        log.debug("cancelReservation() id: {}", id);

        iReservationService.cancelReservation(Long.valueOf(id));

    }

}
