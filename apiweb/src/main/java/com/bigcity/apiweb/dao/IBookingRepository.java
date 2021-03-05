package com.bigcity.apiweb.dao;

import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author nicolasdotnet
 */
public interface IBookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    Optional<Booking> findByBookAndBookingUserAndBookingStatusNotLike(Book book, User bookingUser, String bookingStatus);

    List<Booking> findAllByBookingUser(User userFind);

    List<Booking> findAllByBookingEndDate(Date dateToday);

    List<Booking> findAllByBookingEndDateLessThanEqual(Date dateToday);

    List<Booking> findAllByBookingEndDateLessThanEqualAndBookingStatusNotLike(Date dateToday, String bookingStatus);
    
    Optional<Booking> findByBookAndBookingUserAndBookingStatus(Book book, User reservationUser, String bookingStatus);

}
