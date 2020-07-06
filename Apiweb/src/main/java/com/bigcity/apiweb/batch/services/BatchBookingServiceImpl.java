/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.batch.services;

import com.bigcity.apiweb.batch.beans.Revive;
import com.bigcity.apiweb.batch.services.interfaces.IBatchBookingService;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.services.interfaces.IBookingService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class BatchBookingServiceImpl implements IBatchBookingService {

    @Autowired
    private IBookingService iBookingService;

    @Override
    public List<Revive> getRevives(Date dateToday) {

        Date bookingEndDate = java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()).plusDays(Long.valueOf("14") + 1));
        
        List<Booking> bookings = iBookingService.getAllBookingByOutdated(bookingEndDate);

        ArrayList<Revive> revives = new ArrayList<Revive>();

        for (Booking booking : bookings) {

            Revive revive = new Revive();

            revive.setFirstname(booking.getBookingUser().getFirstname());
            revive.setLastname(booking.getBookingUser().getLastname());
            revive.setEmail(booking.getBookingUser().getEmail());
            revive.setBookTitle(booking.getBook().getTitle());
            revive.setBookingStartDate(booking.getBookingStartDate());
            revive.setBookingEndDate(booking.getBookingEndDate());

            revives.add(revive);

        }

        return revives;
    }

}
