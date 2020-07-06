/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.batch.services.interfaces;


import com.bigcity.apiweb.batch.beans.Revive;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicolasdotnet
 */
public interface IBatchBookingService {


    List<Revive> getRevives(Date dateToday);


}
