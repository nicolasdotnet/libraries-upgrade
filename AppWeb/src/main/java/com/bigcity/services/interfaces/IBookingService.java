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
     * @param username
     * @param topoId
     * @return
     * @throws Exception
     */
    Booking register (String username, Long topoId)throws Exception;
    
    /**
     * method to validate a booking
     * 
     * @param bookingId
     * @return
     * @throws Exception
     */
    Booking validate(Long bookingId) throws Exception;
    
    /**
     * method to make available a booking
     * 
     * @param bookingId
     * @return
     * @throws Exception
     */
    Booking makeAvailable (Long bookingId) throws Exception;
    
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
     * @param username
     * @return
     * @throws Exception
     */
    public List<Booking> getAllBookingByUser(String username) throws Exception;
    
    /**
     * method to remove a booking
     * 
     * @param bookingId
     */
    void delete(Long bookingId);
    
}
