/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.services.interfaces;

import com.bigcity.appweb.beans.Booking;
import java.util.List;
import org.springframework.security.core.Authentication;

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
     * @return
     * @throws Exception
     */
    Booking register (String isbn, Authentication authentication)throws Exception;
    
    /**
     * method to extend a booking
     * 
     * @param bookingId
     * @param authentication
     * @return
     * @throws Exception
     */
    Booking extend(Long bookingId, Authentication authentication) throws Exception;
    
    /**
     *
     * @param bookingId
     * @param authentication
     * @return
     * @throws Exception
     */
    Booking backBook(Long bookingId, Authentication authentication) throws Exception;
    
    /**
     * method to get a booking
     * 
     * @param bookingId
     * @param authentication
     * @return
     * @throws Exception
     */
    Booking getBooking(Long bookingId, Authentication authentication) throws Exception;
    
    /**
     * method to get all bookings by ask user
     * 
     * @param authentication
     * @return
     * @throws Exception
     */
    List<Booking> getAllBookingByUser(Authentication authentication) throws Exception;
    
}
