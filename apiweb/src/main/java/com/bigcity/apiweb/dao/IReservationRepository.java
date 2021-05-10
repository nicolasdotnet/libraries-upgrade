/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.dao;

import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.Reservation;
import com.bigcity.apiweb.entity.User;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author nicolasdotnet
 */
public interface IReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    List<Reservation> findAllByReservationUser(User userFind);

    List<Reservation> findAllByValidateReservationDateAndReservationStatus(LocalDate validateReservationDate, String reservationStatus);
    
    List<Reservation> findAllByBook(Book bookFind);

    Optional<Reservation> findByBookAndReservationUser(Book book, User user);

    List<Reservation> findAllByValidateReservationDateBeforeAndReservationStatus(LocalDate dateFormat, String value);

}
