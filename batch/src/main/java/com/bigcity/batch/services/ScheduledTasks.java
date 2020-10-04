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
import org.apache.logging.log4j.LogManager;
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

    private final org.apache.logging.log4j.Logger log = LogManager.getLogger(ScheduledTasks.class);

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
    // apiserveurport

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${apiLogin}")
    private String login;

    @Value("${apiPassword}")
    private String password;

    private HttpHeaders headers = new HttpHeaders();

    @Scheduled(cron = "${cron.expression}")
    @Override
    public void scheduleBookingReminder() {

        log.debug("scheduleBookingReminder()");

        List<Booking> bookings = null;

        try {
            bookings = getAllBookingByOutdated();

        } catch (RestClientException ex) {
            log.error("erreur dans la réponse de l'api !" + ex.getMessage());
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
    public List<Booking> getAllBookingByOutdated() {

        log.debug("getAllBookingByOutdated()");

        URI uri = null;

        try {
            uri = new URI(baseUrl + serverPort + "/api/user/alpha");
        } catch (URISyntaxException ex) {
            log.error("erreur de endpoint !");
        }

// add basic authentication header
        headers.setBasicAuth(login, password);

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Booking>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Booking>>() {
        });

        if (result.getBody().isEmpty()) {

            log.info("il n'y a pas de relances ");

        } else {

            log.info("il y a " + result.getBody().size() + " relances");
        }

        return result.getBody();
    }

}
