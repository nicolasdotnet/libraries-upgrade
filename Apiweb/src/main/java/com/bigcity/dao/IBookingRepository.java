package com.bigcity.dao;

import com.bigcity.entity.Book;
import com.bigcity.entity.Booking;
import com.bigcity.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    Optional<Booking> findByBookAndBookingUser(Book book, User bookingUser);

    List<Booking> findAllByBookingUser(User userFind);

}
