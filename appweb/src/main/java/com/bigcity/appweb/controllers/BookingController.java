/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.controllers;

import com.bigcity.appweb.beans.Booking;
import com.bigcity.appweb.services.Tools;
import com.bigcity.appweb.services.interfaces.IBookingService;
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
public class BookingController {

    private final Logger log = LogManager.getLogger(BookingController.class);
    
    @Autowired
    private IBookingService iBookingService;

    @PostMapping("/user/book/{isbn}/booking")
    public String saveBookingBook(@PathVariable("isbn") String isbn, Authentication authentication, final RedirectAttributes redirectAttributes) throws URISyntaxException {

        log.debug("saveBookingBook() isbn: {}", isbn);

        Booking bookingNew = null;

        try {
            bookingNew = iBookingService.register(isbn, authentication);

        } catch (HttpClientErrorException e ) {


            redirectAttributes.addFlashAttribute("error", Tools.messageExtraction(e).getMessage());

            return "redirect:/user/books/" + isbn;
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation réalisée !");

        return "redirect:/user/bookings/" + Math.toIntExact(bookingNew.getBookingId());

    }

    @GetMapping("/user/bookings/{id}")
    public String showBooking(@PathVariable("id") Long id, Model model, Authentication authentication) throws URISyntaxException {

        log.debug("showBooking() id: {}", id);

        Booking bookingFind = null;

        try {

            bookingFind = iBookingService.getBooking(id, authentication);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "common/infos";
        }

        model.addAttribute("bookingFind", bookingFind);

        return "/booking/show";

    }

    @GetMapping("/user/bookings")
    public String showAllBookingsByUser(Model model, Authentication authentication) throws URISyntaxException {

        log.debug("showAllBookingsbyUser()");

        List<Booking> bookingList = null;

        try {

            bookingList = iBookingService.getAllBookingByUser(authentication);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "/booking/list";

        }

        model.addAttribute("bookings", bookingList);

        return "/booking/list";

    }
    
    @GetMapping("/user/bookings/{id}/extend")
    public String extendBooking(@PathVariable("id") int id, Authentication authentication, final RedirectAttributes redirectAttributes) throws URISyntaxException {

        log.debug("extendBooking() id: {}", id);

        try {

            iBookingService.extend(Long.valueOf(id), authentication);

        } catch (HttpClientErrorException  e) {

            redirectAttributes.addFlashAttribute("error", Tools.messageExtraction(e).getMessage());
            return "redirect:/user/bookings/{id}";
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation prolongée !");

        return "redirect:/user/bookings/{id}";

    }

}
