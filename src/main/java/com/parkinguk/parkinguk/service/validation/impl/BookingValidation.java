package com.parkinguk.parkinguk.service.validation.impl;
import com.parkinguk.parkinguk.dto.BookingRequest;
import com.parkinguk.parkinguk.entity.Booking;
import com.parkinguk.parkinguk.entity.Customer;
import com.parkinguk.parkinguk.repository.BookingRepository;
import com.parkinguk.parkinguk.repository.CustomerRepository;
import com.parkinguk.parkinguk.service.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
public class BookingValidation implements ValidationService<BookingRequest> {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean checkIfExist(BookingRequest arg) throws Exception {
        Optional<Customer> customer = customerRepository.findById(arg.getCustomerId());
        if(!customer.isPresent()){
            throw new Exception("Customer not registered");
        }
        LocalDateTime earlyLimit = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN);
        LocalDateTime lateLimit = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MAX);
        Optional<List<Booking>> bookings = bookingRepository.findByBookingDateGreaterThanEqualAndBookingDateLessThanEqualAndCustomer(earlyLimit,lateLimit,customer.get());
        return bookings.isPresent() && !bookings.get().isEmpty();
    }
}
