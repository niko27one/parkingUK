package com.parkinguk.parkinguk.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingResponse {
    String message;
    LocalDateTime bookingDate;
    Long slotId;
}
