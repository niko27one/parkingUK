package com.parkinguk.parkinguk.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequest {
    private Long customerId;
    private LocalDateTime dateTime;
    private Boolean motorbike;
    private Boolean parent;
    private Boolean disable;
    private String email;
}
