package com.parkinguk.parkinguk.repository;
import com.parkinguk.parkinguk.entity.Booking;
import com.parkinguk.parkinguk.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> { //if I write findBycolumname is a language of JPA that understand where to look inside the diamond. Spring understand

    Optional<List<Booking>> findByBookingDateGreaterThanEqualAndBookingDateLessThanEqualAndCustomer (LocalDateTime bookingDate,LocalDateTime bookingToDate, Customer customer); //SELECT from Booking where date = the date passed.
    Optional<Booking> findByCustomer(Customer customer);
}
