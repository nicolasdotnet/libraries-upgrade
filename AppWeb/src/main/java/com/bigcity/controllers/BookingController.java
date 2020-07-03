/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.controllers;

import com.bigcity.beans.Booking;
import com.bigcity.services.interfaces.IBookingService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    // save booking with a book
    @PostMapping("/user/book/{isbn}/booking")
    public String saveBookingBook(@PathVariable("isbn") String isbn, Authentication authentication, final RedirectAttributes redirectAttributes) {

        log.debug("saveBookingBook() isbn: {}", isbn);

        Booking bookingNew = null;

        try {
            bookingNew = iBookingService.register(isbn, authentication);

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/user/books/" + isbn;
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation réalisée ");

        return "redirect:/user/bookings/" + Math.toIntExact(bookingNew.getBookingId());

    }

    // show booking
    @GetMapping("/user/bookings/{id}")
    public String showBooking(@PathVariable("id") Long id, Model model, Authentication authentication) {

        log.debug("showBooking() id: {}", id);

        Booking bookingFind = null;

        try {

            bookingFind = iBookingService.getBooking(id, authentication);

        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());

            return "common/infos";
        }

        model.addAttribute("bookingFind", bookingFind);

        return "/booking/show";

    }

    // booking list page by user
    @GetMapping("/user/bookings")
    public String showAllBookingsByUser(Model model, Authentication authentication) {

        log.debug("showAllBookingsbyUser()");

        List<Booking> bookingList = null;

        try {

            bookingList = iBookingService.getAllBookingByUser(authentication);

        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());

            return "/booking/list";

        }

        model.addAttribute("bookings", bookingList);
//        model.addAttribute("user", principal.getName());

        return "/booking/list";

    }

    // cancel booking
    @DeleteMapping("/user/bookings/{id}/cancel")
    public String cancelBooking(@PathVariable("id") int id, Authentication authentication, final RedirectAttributes redirectAttributes) {

        log.debug("cancelBooking() id: {}", id);

        try {

            iBookingService.delete(Long.valueOf(id), authentication);

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/bookings/{id}";
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation annulée");

        return "redirect:/user/bookings";

    }

    // extend booking
    @GetMapping("/user/bookings/{id}/extend")
    public String extendBooking(@PathVariable("id") int id, Authentication authentication, final RedirectAttributes redirectAttributes) {

        log.debug("extendBooking() id: {}", id);

        try {

            iBookingService.extend(Long.valueOf(id), authentication);

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/bookings/{id}";
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation annulée");

        return "redirect:/user/bookings/{id}";

    }

    // back book
    @GetMapping("/user/bookings/{id}/back")
    public String backBookBooking(@PathVariable("id") int id, Authentication authentication, final RedirectAttributes redirectAttributes) {

        log.debug("backBookBooking() id: {}", id);

        try {

            iBookingService.backBook(Long.valueOf(id), authentication);

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/bookings/{id}";
        }

        redirectAttributes.addFlashAttribute("msg", "Retour du livre enregistré");

        return "redirect:/user/bookings/{id}";

    }

}
