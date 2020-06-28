/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.services;

import com.bigcity.beans.Booking;
import com.bigcity.dto.BookingDTO;
import com.bigcity.services.interfaces.IBookingService;
import java.net.URI;
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
public class BookingServiceImpl implements IBookingService {

    private static final Logger log = LogManager.getLogger(BookingServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    private String baseUrl = "http://localhost:";

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public Booking register(String isbn) throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings");

// add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        // bookingDTO
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookIsbn(isbn);
        bookingDTO.setBookingUserEmail("nicolas.desdevises@yahoo.com");
        bookingDTO.setLibrarianEmail("nicolas.desdevises@yahoo.com");

        HttpEntity requestEntity = new HttpEntity(bookingDTO, headers);

        ResponseEntity<Booking> result = null;

        try {

            result = restTemplate.postForEntity(uri, requestEntity, Booking.class);

        } catch (RestClientException e) {

            throw new Exception(e.getMessage());

        }

        return result.getBody();
    }

    @Override
    public Booking extend(Long bookingId) throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings/" + bookingId);

        // add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        HttpEntity requestEntity = new HttpEntity(headers);

        try {

            restTemplate.put(uri, requestEntity);

        } catch (RestClientException e) {

            throw new Exception(e.getMessage());

        }

        return getBooking(bookingId);
        
    }

    @Override
    public Booking getBooking(Long bookingId) throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings/" + bookingId);

        // add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<Booking> result = null;

        try {

            result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Booking.class);

        } catch (RestClientException e) {

            throw new Exception(e.getMessage());

        }

        return result.getBody();
    }

    @Override
    public List<Booking> getAllBookingByUser(String email) throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings?email="+email);

// add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Booking>> result = null;

        try {

            result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Booking>>() {
            });

        } catch (RestClientException e) {

            throw new Exception(e.getMessage());

        }

        return result.getBody();

    }

    @Override
    public void delete(Long bookingId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
