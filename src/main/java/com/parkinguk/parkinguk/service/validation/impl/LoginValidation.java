package com.parkinguk.parkinguk.service.validation.impl;
import com.parkinguk.parkinguk.dto.LoginRequest;
import com.parkinguk.parkinguk.entity.Login;
import com.parkinguk.parkinguk.repository.LogInRepository;
import com.parkinguk.parkinguk.service.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginValidation implements ValidationService<LoginRequest> {

    @Autowired
    private LogInRepository logInRepository;

    @Override
    public boolean checkIfExist(LoginRequest arg) {
        Optional<List<Login>> logins = logInRepository.findByEmailAndPassword(arg.getEmail(),arg.getPassword());
        return logins.isPresent() && !logins.get().isEmpty();
    }
}
