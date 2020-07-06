/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services;

import com.bigcity.batch.beans.Revive;
//import com.bigcity.apiweb.entity.Booking;
//import com.bigcity.apiweb.services.interfaces.IBookingService;
import com.bigcity.batch.services.interfaces.IReviveService;
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
public class ReviveServiceImpl implements IReviveService {

//    @Autowired
//    private IBookingService iBookingService;

    @Override
    public List<Revive> getRevives(Date dateToday) {
//
//        List<Booking> bookings = iBookingService.getAllBookingByOutdated(dateToday);
//        ArrayList<Revive> revives = new ArrayList<Revive>();
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
