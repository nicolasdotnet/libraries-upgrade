/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.specifications;

import com.bigcity.entity.Booking;
import java.util.Date;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author nicolasdotnet
 */
public class BookingSpecification {

    public static Specification<Booking> isExpired(Date dateNow) {
        return (root, query, cb) -> {
            
            return cb.equal(root.get("bookingEndDate"), dateNow);
        };
    }
    
    
    // objet en paramatre valeur critéria retours des pages

}
