/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.batch.services;


import com.bigcity.apiweb.batch.beans.Revive;
import com.bigcity.apiweb.batch.services.interfaces.IBatchBookingService;
import com.bigcity.apiweb.batch.services.interfaces.IScheduledTasks;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 */
@Component
public class ScheduledTasks implements IScheduledTasks {

    @Autowired
    private IBatchBookingService iBatchBookingService;

    @Scheduled(cron = "${cron.expression}")
    @Override
    public void scheduleRevive() {

        Date dateToday = new Date();

        List<Revive> revives = iBatchBookingService.getRevives(dateToday);

        for (Revive revive : revives) {

            try {
                // envoyer email;
                System.out.println("WWWWWWWWWWWWWWWWWWW email : " + revive.getEmail());
            

                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScheduledTasks.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
