package com.parkinguk.parkinguk.service.customer;
import com.parkinguk.parkinguk.dto.Information;
import com.parkinguk.parkinguk.dto.LoginRequest;
import com.parkinguk.parkinguk.dto.RegisterRequest;
import com.parkinguk.parkinguk.exceptions.NotUserFoundException;


public interface CustomerService {
    void register(RegisterRequest registerRequest) throws Exception;
    void logIn(LoginRequest loginRequest) throws NotUserFoundException;
    Information  information(String email);
}
