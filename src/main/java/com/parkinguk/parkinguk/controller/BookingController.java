package com.parkinguk.parkinguk.controller;
import com.parkinguk.parkinguk.dto.BookingRequest;
import com.parkinguk.parkinguk.dto.BookingResponse;
import com.parkinguk.parkinguk.entity.Booking;
import com.parkinguk.parkinguk.service.booking.BookingService;
import com.parkinguk.parkinguk.service.validation.impl.BookingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking/v1")
public class BookingController {

    @Autowired
    private BookingValidation bookingValidation;
    @Autowired
    private BookingService bookingService;

    @PostMapping("booking")
    public ResponseEntity<String> booking (@RequestBody BookingRequest bookingRequest) throws Exception{
        BookingResponse bookingResponse = new BookingResponse();
        if(!bookingValidation.checkIfExist(bookingRequest)){
            bookingService.bookingRequest(bookingRequest);
            bookingResponse.setMessage("booking created");
            return new ResponseEntity<>(bookingResponse.getMessage(), HttpStatus.OK);
        }
        bookingResponse.setMessage("Booking already exist");
        return new ResponseEntity<>(bookingResponse.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("checkBooking/{email}")
    public ResponseEntity<BookingResponse> checkBooking(@PathVariable String email) throws Exception{
        BookingResponse bookingResponse = new BookingResponse();
        Booking booking = bookingService.getBooking(email);
        if(booking.getBookingDate() !=null && booking.getSlot() != null) {
            bookingResponse.setBookingDate(booking.getBookingDate());
            bookingResponse.setSlotId(booking.getSlot().getId());
            bookingResponse.setMessage("alreadyBooked");
        }
        else {
            bookingResponse.setMessage("emptyBooking");
        }
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }
}
