/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.controllers;

import com.bigcity.beans.Booking;
import com.bigcity.services.interfaces.IBookingService;
import java.security.Principal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
    @PostMapping("/user/book/{id}/booking")
    public String saveBookingBook(@PathVariable("id") int id, final RedirectAttributes redirectAttributes, Principal principal) {

        log.debug("saveBookingBook() id: {}", id);

        Booking bookingNew = null;

        try {
            bookingNew = iBookingService.register(principal.getName(), Long.valueOf(id));
            
        } catch (Exception e) {
            
            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/user/book/" + id;
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation réalisée ");

        return "redirect:/user/booking/" + Math.toIntExact(bookingNew.getBookingId());

    }

    // show booking
    @GetMapping("/user/booking/{id}")
    public String showBooking(@PathVariable("id") Long id, Model model, Principal principal) {

        log.debug("showBooking() id: {}", id);

        Booking bookingFind = null;

        try {

            bookingFind = iBookingService.getBooking(id);

        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());

            return "common/infos";
        }

        model.addAttribute("bookingFind", bookingFind);

        return "/booking/show";

    }

    // booking list page by user
    @GetMapping("/user/bookings")
    public String showAllBookingByUser(Model model, Principal principal) {

        log.debug("showAllBookingsbyUser()");

        List<Booking> bookingList = null;

        try {

            bookingList = iBookingService.getAllBookingByUser(principal.getName());

        } catch (Exception e) {

        }

        model.addAttribute("bookings", bookingList);
        model.addAttribute("user", principal.getName());

        return "/booking/list";

    }

    // cancel booking
    @PostMapping("/user/booking/{id}/cancel")
    public String cancelBooking(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

        log.debug("deleteBooking() id: {}", id);

        try {

            iBookingService.delete(Long.valueOf(id));

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/bookings/topos";
        }

        redirectAttributes.addFlashAttribute("msg", "Réservation annulée");

        return "redirect:/user/bookings/topos";

    }

}
