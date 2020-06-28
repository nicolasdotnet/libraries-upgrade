/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.services.interfaces;

import com.bigcity.beans.Booking;
import java.util.List;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookingService {
    
    /**
     * method to register a booking
     * 
     * @param isbn
     * @return
     * @throws Exception
     */
    Booking register (String isbn)throws Exception;
    
    /**
     * method to extend a booking
     * 
     * @param bookingId
     * @return
     * @throws Exception
     */
    Booking extend(Long bookingId) throws Exception;
    
    /**
     * method to get a booking
     * 
     * @param bookingId
     * @return
     * @throws Exception
     */
    Booking getBooking(Long bookingId) throws Exception;
    
    /**
     * method to get all bookings by ask user
     * 
     * @param email
     * @return
     * @throws Exception
     */
    List<Booking> getAllBookingByUser(String email) throws Exception;
    
    /**
     * method to remove a booking
     * 
     * @param bookingId
     */
    void delete(Long bookingId);
    
}
