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

    @Value("${personalizeReminderDay}")
    private String days;

    @Scheduled(cron = "${cron.expression}")
    public void ManagerSchedule() {

        try {
            this.scheduleBookingReminder();
        } catch (RestClientException ex) {
            log.error("erreur dans la réponse de l'api ! " + ex.getMessage());

        } catch (MessagingException ex) {
            log.error("erreur dans l'envoi de l'email ! " + ex.getMessage());

        } finally {

            try {
                this.scheduleReservationInform();
            } catch (RestClientException ex) {
                log.error("erreur dans la réponse de l'api ! " + ex.getMessage());
            } catch (MessagingException ex) {
                log.error("erreur dans l'envoi de l'email ! " + ex.getMessage());
            }

        }

    }

    @Override
    public void scheduleBookingReminder() throws RestClientException, MessagingException {

        log.debug("scheduleBookingReminder()");

        List<Booking> bookings = null;

        // rappel régle de gestion : §§§§§§
        LocalDate dateBookingOut = LocalDate.now().plusDays((Long.valueOf(days)));

        bookings = iProxyService.getAllBookingByOutdated(dateBookingOut);

        for (Booking booking : bookings) {

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("userName", booking.getBookingUser().getFirstname());
            templateModel.put("title", booking.getBook().getTitle());
            templateModel.put("bookingEndDate", booking.getBookingEndDate());
            templateModel.put("senderName", senderName);

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);

            String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

            iEmailService.sendHtmlMessage(booking.getBookingUser().getEmail(), reminderSubject, htmlBody);

        }

    }

    public void scheduleReservationInform() throws RestClientException, MessagingException {

        log.debug("scheduleReservationInform()");

        List<Reservation> reservations = null;

        // rappel régle de gestion : §§§§§§
        LocalDate dateValidate = LocalDate.now().plusDays((Long.valueOf(days)));

        reservations = iProxyService.getAllReservationsByValidateDate(dateValidate);

        for (Reservation reservation : reservations) {

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("userName", reservation.getReservationUser().getFirstname());
            templateModel.put("title", reservation.getBook().getTitle());
            templateModel.put("senderName", senderName);

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);

            String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

            iEmailService.sendHtmlMessage(reservation.getReservationUser().getEmail(), reminderSubject, htmlBody);

        }
    }
}
