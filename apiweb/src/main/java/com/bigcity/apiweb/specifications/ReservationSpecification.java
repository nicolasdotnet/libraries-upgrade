/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.specifications;

import com.bigcity.apiweb.entity.Reservation;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author pi
 */
public class ReservationSpecification implements Specification<Reservation> {

    private ReservationCriteria filter;

    public ReservationSpecification(ReservationCriteria filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getReservationUserEmail() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.join("User").get("email"), filter.getReservationUserEmail()));
        }

        if (filter.getReservationStatus() != null) {

            predicate.getExpressions().add(criteriaBuilder.equal(root.get("reservationStatus"), filter.getReservationStatus()));
        }

        if (filter.getBookTitle() != null) {
            predicate.getExpressions().add(criteriaBuilder.like(root.join("book").get("title"), "%" + filter.getBookTitle() + "%"));
        }

        return criteriaBuilder.and(predicate);
    }

}
