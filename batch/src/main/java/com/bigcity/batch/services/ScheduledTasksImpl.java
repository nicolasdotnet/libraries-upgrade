/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services;

import com.bigcity.batch.bean.Booking;
import com.bigcity.batch.bean.Reservation;
import com.bigcity.batch.services.interfaces.IEmailService;
import com.bigcity.batch.services.interfaces.IProxyService;
import com.bigcity.batch.services.interfaces.IScheduledTasks;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class ScheduledTasksImpl implements IScheduledTasks {

    private final org.apache.logging.log4j.Logger log = LogManager.getLogger(ScheduledTasksImpl.class);

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private IProxyService iProxyService;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${senderName}")
    private String senderName;

    @Value("${reminderSubject}")
    private String reminderSubject;

    @Value("${reservationSubject}")
    private String reservationSubject;

    @Value("${personalizeReminderDay}")
    private String reminderDays;

    @Value("${personalizeReservationDay}")
    private String ReservationDays;
    
    @Scheduled(cron = "${cron.booking}")
    @Override
    public void scheduleBookingReminder() {

        log.debug("scheduleBookingReminder()");

        List<Booking> bookings = null;

        // rappel régle de gestion : §§§§§§
        LocalDate dateBookingOut = LocalDate.now().plusDays((Long.valueOf(reminderDays)));

        try {

            bookings = iProxyService.getAllBookingByOutdated(dateBookingOut);

        } catch (RestClientException ex) {

            log.error("erreur dans la réponse de l'api booking ! " + ex.getMessage());
        }

        for (Booking booking : bookings) {

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("userName", booking.getBookingUser().getFirstname());
            templateModel.put("title", booking.getBook().getTitle());
            templateModel.put("bookingEndDate", booking.getBookingEndDate());
            templateModel.put("senderName", senderName);

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);

            String htmlBody = thymeleafTemplateEngine.process("booking-template-thymeleaf.html", thymeleafContext);

            try {

                iEmailService.sendHtmlMessage(booking.getBookingUser().getEmail(), reminderSubject, htmlBody);

            } catch (MessagingException ex) {

                log.error("erreur dans l'envoi de l'email ! " + ex.getMessage());
            }

        }

    }

    @Scheduled(cron = "${cron.reservation}")
    @Override
    public void scheduleReservationInform() {

        log.debug("scheduleReservationInform()");

        List<Reservation> reservations = null;

        // rappel régle de gestion : §§§§§§
        LocalDate dateValidate = LocalDate.now().plusDays((Long.valueOf(ReservationDays)));

        try {
            
            reservations = iProxyService.getAllReservationsByValidateDate(dateValidate);

        } catch (RestClientException ex) {

            log.error("erreur dans la réponse de l'api reservation ! " + ex.getMessage());
        }

        Calendar validateReservationDate = Calendar.getInstance();

        for (Reservation reservation : reservations) {

            Map<String, Object> templateModel = new HashMap<>();

            validateReservationDate.setTime(reservation.getValidateReservationDate());
            validateReservationDate.add(Calendar.DATE, 2);

            templateModel.put("userName", reservation.getReservationUser().getFirstname());
            templateModel.put("title", reservation.getBook().getTitle());
            templateModel.put("reservationDate", reservation.getReservationDate());
            templateModel.put("validateReservationDate", validateReservationDate.getTime());
            templateModel.put("senderName", senderName);

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);

            String htmlBody = thymeleafTemplateEngine.process("reservation-template-thymeleaf.html", thymeleafContext);

            try {

                iEmailService.sendHtmlMessage(reservation.getReservationUser().getEmail(), reservationSubject, htmlBody);

            } catch (MessagingException ex) {

                log.error("erreur dans l'envoi de l'email ! " + ex.getMessage());
            }

        }
    }
}
