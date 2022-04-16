package com.parkinguk.parkinguk.service.validation.impl;
import com.parkinguk.parkinguk.entity.Login;
import com.parkinguk.parkinguk.repository.LogInRepository;
import com.parkinguk.parkinguk.service.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisterValidation implements ValidationService<String> {

    @Autowired
    LogInRepository logInRepository;

    @Override
    public boolean checkIfExist(String email) {
        Optional<List<Login>>  logins = logInRepository.findByEmail(email);
        if(logins.isPresent() && !logins.get().isEmpty()){
            return true;
        }
        return false;
    }


}
