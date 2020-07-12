/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.BookingDTO;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.specifications.BookingCriteria;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookingService {

    /**
     * method to register a booking
     *
     * @param bookingDto
     * @return
     * @throws Exception
     */
    Booking register(BookingDTO bookingDto) throws Exception;

    /**
     * method to stop a booking when book is back
     *
     * @param bookingId
     * @return
     * @throws Exception
     */
    Booking bookIsBack(Long bookingId) throws Exception;
    
    /**
     * method to extend a booking
     * 
     * @param bookingId
     * @return
     * @throws Exception
     */
    Booking extendBooking(Long bookingId) throws Exception;

    /**
     * method to get a booking
     *
     * @param bookingId
     * @return
     */
    Booking getBooking(Long bookingId);

    /**
     * method to get all bookings by ask user
     *
     * @param email
     * @return
     * @throws Exception
     */
    List<Booking> getAllBookingByUser(String email) throws Exception;

    /**
     * method to get all bookings by criteria
     * 
     * @param bookingCriteria
     * @param page
     * @param size
     * @return
     */
    Page<Booking> getAllBookingsByCriteria(BookingCriteria bookingCriteria, int page, int size);
    
    /**
     * method to get all bookings by outdated
     * 
     * @param dateToday
     * @return
     */
    List<Booking> getAllBookingByOutdated(Date dateToday);

}
