/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.services;

import com.bigcity.appweb.beans.Booking;
import com.bigcity.appweb.beans.Reservation;
import com.bigcity.appweb.dto.ReservationDTO;
import com.bigcity.appweb.services.interfaces.IBookingService;
import com.bigcity.appweb.services.interfaces.IReservationService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private static final Logger log = LogManager.getLogger(ReservationServiceImpl.class);

    @Autowired
    private IBookingService iBookingService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    @Value("${baseUrl}")
    private String baseUrl;

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public Reservation register(String isbn, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/reservations");

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setBookIsbn(isbn);
        reservationDTO.setReservationUserEmail(authentication.getName());

        HttpEntity requestEntity = new HttpEntity(reservationDTO, headers);

        ResponseEntity<Reservation> result = null;

        result = restTemplate.postForEntity(uri, requestEntity, Reservation.class);

        return result.getBody();
    }

    @Override
    public void cancel(Long reservationId, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/reservations/" + reservationId + "/cancel");

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        restTemplate.put(uri, requestEntity);

    }

    @Override
    public void validate(Long reservationId, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/reservations/" + reservationId + "/validate");

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        restTemplate.put(uri, requestEntity);

    }

    @Override
    public Reservation getReservation(Long reservationId, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/reservations/" + reservationId);

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<Reservation> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Reservation.class);

        return result.getBody();
    }

    @Override
    public List<Reservation> getAllReservationByUser(Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/reservations?email=" + authentication.getName());

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Reservation>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Reservation>>() {
        });

        if (!result.getBody().isEmpty()) {

            for (Reservation reservationFind : result.getBody()) {

                List<Booking> bookingsFind = iBookingService.getAllCurrentBookingByBook(reservationFind.getBook().getIsbn(), authentication);
                Collections.sort(bookingsFind, Booking.ComparatorBookingEndDate);
                
                reservationFind.setReturnDateBook(bookingsFind.get(0).getBookingEndDate());

                List<Reservation> reservations = this.getAllReservationByBook(reservationFind.getBook().getIsbn(), authentication);
                Collections.sort(reservations, Reservation.ComparatorReservationId);
                reservations.forEach(reservation -> {

                    if (reservation.getReservationUser().getEmail().equals(authentication.getName())) {

                        int position = reservations.indexOf(reservation) + 1;

                        reservationFind.setQueuePosition(String.valueOf(position));

                    }
                });
                
                result.getBody().set(result.getBody().indexOf(reservationFind), reservationFind);

            }

        }

        return result.getBody();

    }

    @Override
    public List<Reservation> getAllReservationByBook(String isbn, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/reservations/book/" + isbn);

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Reservation>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Reservation>>() {
        });

        return result.getBody();
    }

}
