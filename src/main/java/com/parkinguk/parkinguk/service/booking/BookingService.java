package com.parkinguk.parkinguk.service.booking;
import com.parkinguk.parkinguk.dto.BookingRequest;
import com.parkinguk.parkinguk.dto.ReceiptResponse;
import com.parkinguk.parkinguk.entity.Booking;
import com.parkinguk.parkinguk.exceptions.BookingRejectionException;


public interface BookingService {
    void bookingRequest(BookingRequest bookingRequest) throws Exception;
    ReceiptResponse getReceipt(Long customerId) throws Exception;
    Booking getBooking(String email) throws Exception;
}
