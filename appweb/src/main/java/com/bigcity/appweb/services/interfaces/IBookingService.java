/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.services.interfaces;

import com.bigcity.appweb.beans.Booking;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookingService {

    /**
     * method to register a booking
     *
     * @param isbn
     * @param authentication
     * @return booking objet
     * @throws java.net.URISyntaxException
     */
    Booking register(String isbn, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to extend a booking
     *
     * @param bookingId
     * @param authentication
     * @return booking objet
     * @throws java.net.URISyntaxException
     */
    Booking extend(Long bookingId, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     *
     * @param bookingId
     * @param authentication
     * @return booking objet
     * @throws java.net.URISyntaxException
     */
    Booking backBook(Long bookingId, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to get a booking
     *
     * @param bookingId
     * @param authentication
     * @return booking objet
     * @throws java.net.URISyntaxException
     */
    Booking getBooking(Long bookingId, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to get all bookings by ask user
     *
     * @param authentication
     * @return booking list
     * @throws java.net.URISyntaxException
     */
    List<Booking> getAllBookingByUser(Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to get all current bookings by book
     *
     * @param isbn
     * @param authentication
     * @return booking list
     * @throws java.net.URISyntaxException
     */
    List<Booking> getAllCurrentBookingByBook(String isbn, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to get all current bookings
     *
     * @param authentication
     * @return booking list
     * @throws java.net.URISyntaxException
     */
    List<Booking> getAllBooking(Authentication authentication) throws URISyntaxException, RestClientException;

}
