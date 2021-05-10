/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services;

import com.bigcity.batch.bean.Booking;
import com.bigcity.batch.bean.Reservation;
import com.bigcity.batch.services.interfaces.IProxyService;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class ProxyServiceImpl implements IProxyService {

    private final org.apache.logging.log4j.Logger log = LogManager.getLogger(ProxyServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${apiLogin}")
    private String login;

    @Value("${apiPassword}")
    private String password;

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public List<Booking> getAllBookingByOutdated(LocalDate dateBookingOut) throws RestClientException{
        
        log.debug("getAllBookingByOutdated()");

        URI uri = null;

        try {
            uri = new URI(baseUrl + serverPort + "/api/user/bookingout?dateBookingOut=" + dateBookingOut);
        } catch (URISyntaxException ex) {
            log.error("erreur de endpoint !");
        }

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

    @Override
    public List<Reservation> getAllReservationsByValidateDate(LocalDate dateValidate) throws RestClientException {
        
                
        log.debug("getAllReservationsByValidateDate()");

        URI uri = null;

        try {
            uri = new URI(baseUrl + serverPort + "/api/user/reservationsValidate?dateValidate=" + dateValidate);
        } catch (URISyntaxException ex) {
            log.error("erreur de endpoint !");
        }

        headers.setBasicAuth(login, password);

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Reservation>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Reservation>>() {
        });

        if (result.getBody().isEmpty()) {

            log.info("il n'y a pas de réservations ");

        } else {

            log.info("il y a " + result.getBody().size() + " réservations");
        }

        return result.getBody();
    }
}
