package com.parkinguk.parkinguk.service.booking;
import com.parkinguk.parkinguk.dto.BookingRequest;
import com.parkinguk.parkinguk.entity.Booking;


public interface BookingService {
    void bookingRequest(BookingRequest bookingRequest) throws Exception;

    Booking getBooking(String email) throws Exception;
}
