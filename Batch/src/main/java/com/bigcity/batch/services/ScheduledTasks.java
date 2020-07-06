/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.services;

import com.bigcity.batch.beans.Revive;
import com.bigcity.batch.services.interfaces.IEmailService;
import com.bigcity.batch.services.interfaces.IReviveService;
import com.bigcity.batch.services.interfaces.IScheduledTasks;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class ScheduledTasks implements IScheduledTasks {

    @Autowired
    private IReviveService iReviveService;

    @Autowired
    private IEmailService iEmailService;

    @Scheduled(cron = "${cron.expression}")
    @Override
    public void scheduleRevive() {
        
        System.out.println("B com.bigcity.services.ScheduledTasks.scheduleRevive()");

        Date dateToday = new Date();

        String firstname = "Laure";
        String lastname = "desdevises";
        String email = "laure@yahoo.com";
        String bookTitle = "book1";
        Date bookingStartDate = new Date();
        Date bookingEndDate = java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()).plusDays(Long.valueOf("14") + 1));

        Revive r = new Revive();
        r.setBookTitle(bookTitle);
        r.setBookingEndDate(bookingEndDate);
        r.setBookingStartDate(bookingStartDate);
        r.setEmail(email);
        r.setFirstname(firstname);
        r.setLastname(lastname);

        List<Revive> revives = new ArrayList<>();

        revives.add(r);

//        List<Revive> revives = iReviveService.getRevives(dateToday);

        for (Revive revive : revives) {

            try {
                // envoyer email;
                System.out.println("WWWWWWWWWWWWWWWWWWW email : " + revive.getEmail());

                try {
                    iEmailService.sendReviveMessage(revive);
                } catch (IOException ex) {
                    Logger.getLogger(ScheduledTasks.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(ScheduledTasks.class.getName()).log(Level.SEVERE, null, ex);
                }

                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScheduledTasks.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
