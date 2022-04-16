package com.parkinguk.parkinguk.controller;

import com.parkinguk.parkinguk.dto.ReceiptResponse;
import com.parkinguk.parkinguk.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipt/v1")
public class ReceiptController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/get/{customerId}")
    public ResponseEntity<ReceiptResponse> booking (@PathVariable Long customerId) throws Exception{
        ReceiptResponse receiptResponse =bookingService.getReceipt(customerId);
        return new ResponseEntity<>(receiptResponse, HttpStatus.OK);
    }
}
