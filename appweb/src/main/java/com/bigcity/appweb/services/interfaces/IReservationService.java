/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.services.interfaces;

import com.bigcity.appweb.beans.Reservation;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IReservationService {

    /**
     * method to register a reservation
     *
     * @param isbn
     * @param authentication
     * @return reservation objet
     * @throws java.net.URISyntaxException
     */
    Reservation register(String isbn, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to cancel a reservation
     *
     * @param reservationId
     * @param authentication
     * @throws java.net.URISyntaxException
     */
    void cancel(Long reservationId, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to get a reservation
     *
     * @param reservationId
     * @param authentication
     * @return reservation objet
     * @throws java.net.URISyntaxException
     */
    Reservation getReservation(Long reservationId, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to get all reservations by ask user
     *
     * @param authentication
     * @return reservation list
     * @throws java.net.URISyntaxException
     */
    List<Reservation> getAllReservationByUser(Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to get all current reservations by book
     *
     * @param isbn
     * @param authentication
     * @return reservation list
     * @throws java.net.URISyntaxException
     */
    List<Reservation> getAllReservationByBook(String isbn, Authentication authentication) throws URISyntaxException, RestClientException;

    /**
     * method to validate reservation by a user
     *
     * @param reservationId
     * @param authentication
     * @throws java.net.URISyntaxException
     */
    void validate(Long reservationId, Authentication authentication) throws URISyntaxException, RestClientException;

}
