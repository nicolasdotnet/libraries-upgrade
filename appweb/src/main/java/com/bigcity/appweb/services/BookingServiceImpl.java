/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.services;

import com.bigcity.appweb.beans.Booking;
import com.bigcity.appweb.dto.BookingDTO;
import com.bigcity.appweb.services.interfaces.IBookingService;
import java.net.URI;
import java.net.URISyntaxException;
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
public class BookingServiceImpl implements IBookingService {

    private static final Logger log = LogManager.getLogger(BookingServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    @Value("${baseUrl}")
    private String baseUrl;

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public Booking register(String isbn, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings");

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookIsbn(isbn);
        bookingDTO.setBookingUserEmail(authentication.getName());
        bookingDTO.setLibrarianEmail(authentication.getName());

        HttpEntity requestEntity = new HttpEntity(bookingDTO, headers);

        ResponseEntity<Booking> result = null;

        result = restTemplate.postForEntity(uri, requestEntity, Booking.class);

        return result.getBody();
    }

    @Override
    public Booking extend(Long bookingId, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings/" + bookingId);

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        restTemplate.put(uri, requestEntity);

        return getBooking(bookingId, authentication);

    }

    @Override
    public Booking backBook(Long bookingId, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings/" + bookingId + "/back");

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        restTemplate.put(uri, requestEntity);

        return getBooking(bookingId, authentication);

    }

    @Override
    public Booking getBooking(Long bookingId, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings/" + bookingId);

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<Booking> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Booking.class);

        return result.getBody();
    }

    @Override
    public List<Booking> getAllBookingByUser(Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings?email=" + authentication.getName());

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Booking>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Booking>>() {
        });

        return result.getBody();

    }

    @Override
    public List<Booking> getAllCurrentBookingByBook(String isbn, Authentication authentication) throws URISyntaxException, RestClientException {
                
        URI uri = new URI(baseUrl + serverPort + "/api/user/bookings/book/" + isbn);

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Booking>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Booking>>() {
        });

        return result.getBody();
    }

    @Override
    public List<Booking> getAllBooking(Authentication authentication) throws URISyntaxException, RestClientException {
        
                URI uri = new URI(baseUrl + serverPort + "/api/librarian/allbookings");

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Booking>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Booking>>() {
        });

        return result.getBody();
        
    }

}
