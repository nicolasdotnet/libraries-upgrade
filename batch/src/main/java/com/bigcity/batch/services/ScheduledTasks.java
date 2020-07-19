/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services;

import com.bigcity.batch.bean.Booking;
import com.bigcity.batch.services.interfaces.IEmailService;
import com.bigcity.batch.services.interfaces.IScheduledTasks;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class ScheduledTasks implements IScheduledTasks {

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${senderName}")
    private String senderName;

    @Value("${reminderSubject}")
    private String reminderSubject;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${login}")
    private String login;

    @Value("${password}")
    private String password;

    private HttpHeaders headers = new HttpHeaders();

    @Scheduled(cron = "${cron.expression}")
    @Override
    public void scheduleBookingReminder() {

        System.out.println("B com.bigcity.services.ScheduledTasks.scheduleRevive()");

        List<Booking> bookings = null;

        try {
            bookings = getAllBookingByOutdated();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ScheduledTasks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RestClientException ex) {
            Logger.getLogger(ScheduledTasks.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    @Override
    public List<Booking> getAllBookingByOutdated() throws URISyntaxException, RestClientException {

        System.out.println("C com.bigcity.services.ScheduledTasks.scheduleRevive()");

        URI uri = new URI(baseUrl + serverPort + "/api/user/alpha");

// add basic authentication header
        headers.setBasicAuth(login, password);

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Booking>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Booking>>() {
        });

        if (result.getBody().isEmpty()) {

            System.out.println("Pas de relances : " + result.getStatusCode());

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("userName", "Laure");
            templateModel.put("title", "RELANCE");
            templateModel.put("bookingEndDate", "Aujourd'hui");
            templateModel.put("senderName", senderName);

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);

            String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

            try {
                iEmailService.sendHtmlMessage("laure@yahoo.com", reminderSubject, htmlBody);
            } catch (MessagingException ex) {

                Logger.getLogger(ScheduledTasks.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            System.out.println("il y a " + result.getBody().size() + " relances");
        }

        return result.getBody();
    }
}
