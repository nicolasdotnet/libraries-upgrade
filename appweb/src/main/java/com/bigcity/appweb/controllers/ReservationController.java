/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.controllers;

import com.bigcity.appweb.beans.Reservation;
import com.bigcity.appweb.services.Tools;
import com.bigcity.appweb.services.interfaces.IReservationService;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nicolasdotnet
 */
@Controller
@Transactional
public class ReservationController {

    private final Logger log = LogManager.getLogger(ReservationController.class);

    @Autowired
    private IReservationService iReservationService;

    @PostMapping("/user/book/{isbn}/reserve")
    public String saveReservationBook(@PathVariable("isbn") String isbn, Authentication authentication,
            final RedirectAttributes redirectAttributes) throws URISyntaxException {

        log.debug("saveReservationBook() isbn: {}", isbn);

        Reservation reservationNew = null;

        try {
            reservationNew = iReservationService.register(isbn, authentication);

        } catch (HttpClientErrorException e) {

            redirectAttributes.addFlashAttribute("error", Tools.messageExtraction(e).getMessage());

            return "redirect:/user/books/" + isbn;
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation réalisée !");

        return "redirect:/user/reservations/" + Math.toIntExact(reservationNew.getReservationId());

    }

    @GetMapping("/user/reservations/{id}")
    public String showReservation(@PathVariable("id") Long id, Model model, Authentication authentication) throws URISyntaxException {

        log.debug("showReservation() id: {}", id);

        Reservation reservationFind = null;

        try {

            reservationFind = iReservationService.getReservation(id, authentication);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "common/infos";
        }

        model.addAttribute("reservationFind", reservationFind);

        return "/reservation/show";

    }

    @GetMapping("/user/reservations")
    public String showAllReservationsByUser(Model model, Authentication authentication) throws URISyntaxException {

        log.debug("showAllReservationsbyUser()");

        List<Reservation> reservationList = null;

        try {

            reservationList = iReservationService.getAllReservationByUser(authentication);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "/reservation/list";

        }

        model.addAttribute("reservations", reservationList);

        return "/reservation/list";

    }

    @GetMapping("/user/reservations/{id}/cancel")
    public String cancelReservation(@PathVariable("id") int id, Authentication authentication, final RedirectAttributes redirectAttributes) throws URISyntaxException {

        log.debug("cancelReservation() id: {}", id);

        try {

            iReservationService.cancel(Long.valueOf(id), authentication);

        } catch (HttpClientErrorException e) {

            redirectAttributes.addFlashAttribute("error", Tools.messageExtraction(e).getMessage());

            return "redirect:/user/reservations";
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation annulée !");

        return "redirect:/user/reservations";

    }
    
    @GetMapping("/user/reservations/{id}/validate")
    public String validateReservation(@PathVariable("id") int id, Authentication authentication, final RedirectAttributes redirectAttributes) throws URISyntaxException {

        log.debug("validateReservation() id: {}", id);

        try {

            iReservationService.validate(Long.valueOf(id), authentication);

        } catch (HttpClientErrorException e) {

            redirectAttributes.addFlashAttribute("error", Tools.messageExtraction(e).getMessage());

            return "redirect:/user/reservations";
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation validé -> prêt enregistré !");

        return "redirect:/user/bookings";

    }

}
