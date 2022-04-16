package com.parkinguk.parkinguk.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class ReceiptResponse {

    public Long amount;
    private LocalDateTime bookingDate;
    private LocalDateTime bookingToDate;
    private String firstName;
    private String getLastName;
    private LocalDateTime dob;
    private String vehicleRegistrationNumber;
}
