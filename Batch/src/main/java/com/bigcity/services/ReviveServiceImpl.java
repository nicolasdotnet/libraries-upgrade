/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.services;

import com.bigcity.beans.Revive;
import com.bigcity.services.interfaces.IReviveService;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class ReviveServiceImpl implements IReviveService {

    @Override
    public List<Revive> getRevives(Date dateToday) {

        // List<Booking> bookings = iBookingService.getAllBookingByOutdated(dateToday);
        // ArrayList<Revive> revives = new ArrayList<Revive>();
//
//        for (Booking booking : bookings) {
//
//            Revive revive = new Revive();
//
//            revive.setFirstname(booking.getBookingUser().getFirstname());
//            revive.setLastname(booking.getBookingUser().getLastname());
//            revive.setEmail(booking.getBookingUser().getEmail());
//            revive.setBookTitle(booking.getBook().getTitle());
//            revive.setBookingStartDate(booking.getBookingStartDate());
//            revive.setBookingEndDate(booking.getBookingEndDate());
//
//            revives.add(revive);
//
//        }
//
//        return revives;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
