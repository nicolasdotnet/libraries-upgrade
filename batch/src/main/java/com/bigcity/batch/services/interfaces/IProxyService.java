/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services.interfaces;

import com.bigcity.batch.bean.Booking;
import com.bigcity.batch.bean.Reservation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IProxyService {

    /**
     * method to get all bookings by outdated
     *
     * @param dateBookingOut
     * @return
     */
    List<Booking> getAllBookingByOutdated(LocalDate dateBookingOut) throws RestClientException;

    /**
     * method to get all reservations by outdated
     * 
     * @param dateValidate
     * @return
     * @throws RestClientException
     */
    List<Reservation> getAllReservationsByValidateDate(LocalDate dateValidate) throws RestClientException;

}
