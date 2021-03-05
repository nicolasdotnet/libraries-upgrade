/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.dao;

import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author pi
 */
public interface IReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    Optional<Reservation> findByBookAndReservationUserAndReservationStatusNotLike(Book book, User bookingUser, String reservationStatus);

    List<Reservation> findAllByReservationUser(User userFind);

    List<Reservation> findAllByValidateReservationDateLessThanEqualAndReservationStatus(Date validateReservationDate, String reservationStatus);

    List<Reservation> findAllByValidateReservationDateAndReservationStatus(Date validateReservationDate, String reservationStatus);

}
