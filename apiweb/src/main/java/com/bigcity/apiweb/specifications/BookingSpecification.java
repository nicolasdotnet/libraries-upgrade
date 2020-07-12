/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.specifications;

import com.bigcity.apiweb.entity.Booking;
import java.util.Date;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author nicolasdotnet
 * 
 * This class is used to search Bookings specifying the criteria
 * 
 */
public class BookingSpecification implements Specification<Booking> {

    private BookingCriteria filter;

    public BookingSpecification(BookingCriteria filter) {
        super();
        this.filter = filter;
    }

    public static Specification<Booking> isExpired(Date dateNow) {
        return (root, query, cb) -> {

            return cb.equal(root.get("bookingEndDate"), dateNow);
        };
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getBookingUserEmail() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.join("User").get("email"), filter.getBookingUserEmail()));
        }

        if (filter.getBookingStatus() != null) {
            
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("bookingStatus"), filter.getBookingStatus()));
        }

        if (filter.getBookTitle() != null) {
            predicate.getExpressions().add(criteriaBuilder.like(root.join("book").get("title"), "%" + filter.getBookTitle() + "%"));
        }

        return criteriaBuilder.and(predicate);
    }

}
