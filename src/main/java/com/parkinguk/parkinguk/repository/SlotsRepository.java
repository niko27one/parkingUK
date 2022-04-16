package com.parkinguk.parkinguk.repository;
import com.parkinguk.parkinguk.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface SlotsRepository extends JpaRepository<Slot, Long> {
    @Query(value = "select * from slot where ((booking_date < ?1 and booking_date > ?2 ) or booking_date is null) and motorbike = false and disable = false and parent = false and occupied =false" , nativeQuery = true)
    Optional<List<Slot>> findByBookingDateBeforeOrBookingDateAfterOrBookingDateIsNullAndMotorbikeFalseAndDisableFalseAndParentFalseAndOccupiedFalse(LocalDateTime bookingDate,LocalDateTime bookingToDate);
    @Query(value = "select * from slot where ((booking_date < ?1 and booking_date > ?2 ) or booking_date is null) and motorbike = true and occupied =false" , nativeQuery = true)
    Optional<List<Slot>> findByBookingDateBeforeOrBookingDateAfterOrBookingDateIsNullAndMotorbikeTrueAndOccupiedFalse(LocalDateTime bookingDateFrom,LocalDateTime bookingToDate);
    @Query(value = "select * from slot where ((booking_date < ?1 and booking_date > ?2 ) or booking_date is null) and disable = true and occupied =false" , nativeQuery = true)
    Optional<List<Slot>> findByBookingDateBeforeOrBookingDateAfterOrBookingDateIsNullAndDisableTrueAndOccupiedFalse(LocalDateTime bookingDate,LocalDateTime bookingToDate);
    @Query(value = "select * from slot where ((booking_date < ?1 and booking_date > ?2 ) or booking_date is null) and parent = true and occupied =false" , nativeQuery = true)
    Optional<List<Slot>> findByBookingDateBeforeOrBookingDateAfterOrBookingDateIsNullAndParentTrueAndOccupiedFalse(LocalDateTime bookingDate,LocalDateTime bookingToDate);
}