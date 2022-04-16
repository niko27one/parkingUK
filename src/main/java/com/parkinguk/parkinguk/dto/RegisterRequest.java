package com.parkinguk.parkinguk.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private LocalDateTime dob;
    private String vehicleRegistrationNumber;

}
