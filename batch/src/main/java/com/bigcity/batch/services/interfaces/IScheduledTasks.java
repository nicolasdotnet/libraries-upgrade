/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services.interfaces;

import com.bigcity.batch.bean.Booking;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IScheduledTasks {

    void scheduleBookingReminder();

    /**
     * method to get all bookings by outdated
     *
     * @return
     * @throws java.net.URISyntaxException
     */
    List<Booking> getAllBookingByOutdated() throws URISyntaxException, RestClientException;

}
