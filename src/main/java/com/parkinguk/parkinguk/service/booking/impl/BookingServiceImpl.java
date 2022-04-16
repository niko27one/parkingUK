package com.parkinguk.parkinguk.service.booking.impl;

import com.parkinguk.parkinguk.dto.BookingRequest;
import com.parkinguk.parkinguk.dto.ReceiptResponse;
import com.parkinguk.parkinguk.entity.Booking;
import com.parkinguk.parkinguk.entity.Customer;
import com.parkinguk.parkinguk.entity.Login;
import com.parkinguk.parkinguk.entity.Slot;
import com.parkinguk.parkinguk.exceptions.BookingRejectionException;
import com.parkinguk.parkinguk.repository.BookingRepository;
import com.parkinguk.parkinguk.repository.CustomerRepository;
import com.parkinguk.parkinguk.repository.LogInRepository;
import com.parkinguk.parkinguk.repository.SlotsRepository;
import com.parkinguk.parkinguk.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private SlotsRepository slotsRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LogInRepository logInRepository;


    @Override
    public void bookingRequest(BookingRequest bookingRequest) throws Exception {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDateTime.now());
        LocalDateTime earlyLimit = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN);
        LocalDateTime lateLimit = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MAX);
        booking.setAmount(5);
        List<Slot> allSlots =slotsRepository.findAll();
        List<Long> customerIds = allSlots.stream().map(Slot::getBooking).filter(Objects::nonNull).map(p->p.getCustomer().getId()).collect(Collectors.toList());
        if(customerIds.contains(bookingRequest.getCustomerId())){
            throw new Exception("Customer has already booked a slot");
        }
        if (bookingRequest.getDisable()) {
            Optional<List<Slot>> slots = slotsRepository.findByBookingDateBeforeOrBookingDateAfterOrBookingDateIsNullAndDisableTrueAndOccupiedFalse(earlyLimit,lateLimit);
            submitSlotrequest(slots, booking, bookingRequest);
            return;
        }
        if (bookingRequest.getMotorbike()) {
            Optional<List<Slot>> slots = slotsRepository.findByBookingDateBeforeOrBookingDateAfterOrBookingDateIsNullAndMotorbikeTrueAndOccupiedFalse(earlyLimit,lateLimit);
            submitSlotrequest(slots, booking, bookingRequest);
            return;
        }
        if (bookingRequest.getParent()) {
            Optional<List<Slot>> slots = slotsRepository.findByBookingDateBeforeOrBookingDateAfterOrBookingDateIsNullAndParentTrueAndOccupiedFalse(earlyLimit,lateLimit);
            submitSlotrequest(slots, booking, bookingRequest);
            return;
        }
        Optional<List<Slot>> slots = slotsRepository.findByBookingDateBeforeOrBookingDateAfterOrBookingDateIsNullAndMotorbikeFalseAndDisableFalseAndParentFalseAndOccupiedFalse(earlyLimit,lateLimit);
        submitSlotrequest(slots, booking, bookingRequest);

    }

    public ReceiptResponse getReceipt(Long customerId) throws Exception {

        Optional<Customer> customer = customerRepository.findById(customerId);
        customer.orElseThrow(()-> new Exception("Customer not present"));
        Optional<Booking> booking = bookingRepository.findByCustomer(customer.get());
        booking.orElseThrow(()-> new Exception("Booking not present"));
        return ReceiptResponse.builder().amount(booking.get().getAmount()).bookingDate(booking.get().getBookingDate()).bookingToDate(booking.get().getBookingToDate()).firstName(booking.get().getCustomer().getFirstName()).vehicleRegistrationNumber(booking.get().getCustomer().getVehicleRegistrationNumber()).build();
    }

    public Booking getBooking(String email) throws Exception {

        Optional<List<Login>> login = logInRepository.findByEmail(email);
        login.orElseThrow(()-> new Exception("Customer not present"));
        Optional<Booking> booking = bookingRepository.findByCustomer(login.get().get(0).getCustomer());
        return booking.orElseGet(Booking::new);
    }

    public void submitSlotrequest(Optional<List<Slot>> slots, Booking booking, BookingRequest bookingRequest) throws Exception {
        if (slots.isPresent() && !slots.get().isEmpty()) {
            for (Slot slot : slots.get()) {
                if (!slot.getOccupied()) {
                    booking.setBookingDate(LocalDateTime.now());
                    booking.setBookingToDate(LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MAX));
                    Optional<Customer> customer = customerRepository.findById(bookingRequest.getCustomerId());
                    if(!customer.isPresent()){
                        throw  new Exception("Customer isn't registered");
                    }
                    slot.setBookingDate(LocalDateTime.now());
                    slot.setBookingToDate(LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MAX));
                    slot.setOccupied(true);
                    booking.setCustomer(customer.get());
                    bookingRepository.save(booking);
                    slot.setBooking(booking);
                    slotsRepository.save(slot);
                    break;
                }
            }
         return;
        }
        throw new BookingRejectionException("all slots are occupied");
    }
}

    /*public boolean checkIfSlotFree(BookingRequest arg){
        Optional<List<Slot>> bookings = slotsRepository.findByBookingDateAndOccupied(arg.getDateTime(),arg.isOccupied());
        return false;
    }

    public boolean checkIfMotorbikeFree(BookingRequest arg){
        Optional<List<Slot>> mBookings = slotsRepository.findByMotorbikeSlot(arg.isMotorbike(),arg.isOccupied());
        return false;
    }*/

