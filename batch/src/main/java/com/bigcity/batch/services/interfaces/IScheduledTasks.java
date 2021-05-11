/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services.interfaces;

import javax.mail.MessagingException;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IScheduledTasks {

    /**
     *
     * @throws RestClientException
     * @throws MessagingException
     */
    void scheduleBookingReminder() throws RestClientException, MessagingException;
    
    /**
     *
     * @throws RestClientException
     * @throws MessagingException
     */
    void scheduleReservationInform() throws RestClientException, MessagingException;

}
