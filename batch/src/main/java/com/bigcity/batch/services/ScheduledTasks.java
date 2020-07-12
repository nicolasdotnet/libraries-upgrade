/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services;

import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.services.interfaces.IBookingService;
import com.bigcity.batch.services.interfaces.IEmailService;
import com.bigcity.batch.services.interfaces.IScheduledTasks;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class ScheduledTasks implements IScheduledTasks {

    @Autowired
    private IBookingService iBookingService;

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${senderName}")
    private String senderName;

    @Value("${reminderSubject}")
    private String reminderSubject;

    @Scheduled(cron = "${cron.expression}")
    @Override
    public void scheduleBookingReminder() {

        System.out.println("B com.bigcity.services.ScheduledTasks.scheduleRevive()");

        Date dateToday = new Date();

        List<Booking> bookings = iBookingService.getAllBookingByOutdated(dateToday);

        for (Booking booking : bookings) {

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("userName", booking.getBookingUser().getFirstname());
            templateModel.put("title", booking.getBook().getTitle());
            templateModel.put("bookingEndDate", booking.getBookingEndDate());
            templateModel.put("senderName", senderName);

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);

            String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

            try {
                iEmailService.sendHtmlMessage(booking.getBookingUser().getEmail(), reminderSubject, htmlBody);
            } catch (MessagingException ex) {

                Logger.getLogger(ScheduledTasks.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
