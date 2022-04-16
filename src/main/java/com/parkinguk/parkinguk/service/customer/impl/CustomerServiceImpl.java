package com.parkinguk.parkinguk.service.customer.impl;

import com.parkinguk.parkinguk.dto.Information;
import com.parkinguk.parkinguk.dto.LoginRequest;
import com.parkinguk.parkinguk.dto.RegisterRequest;
import com.parkinguk.parkinguk.entity.Customer;
import com.parkinguk.parkinguk.entity.Login;
import com.parkinguk.parkinguk.exceptions.EmailFoundException;
import com.parkinguk.parkinguk.exceptions.NotUserFoundException;
import com.parkinguk.parkinguk.repository.CustomerRepository;
import com.parkinguk.parkinguk.repository.LogInRepository;
import com.parkinguk.parkinguk.service.customer.CustomerService;
import com.parkinguk.parkinguk.service.validation.impl.LoginValidation;
import com.parkinguk.parkinguk.service.validation.impl.RegisterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RegisterValidation registerValidation;
    @Autowired
    private LoginValidation loginValidation;
    @Autowired
    private LogInRepository logInRepository;

    @Override
    public void register(RegisterRequest registerRequest) throws Exception {
        if(registerValidation.checkIfExist(registerRequest.getEmail())){
            throw new EmailFoundException("Email already exists");//:)
        }
        Customer customer = Customer.builder().firstName(registerRequest.getFirstName()).lastName(registerRequest.getLastName()).dob(registerRequest.getDob()).vehicleRegistrationNumber(registerRequest.getVehicleRegistrationNumber()).build();
        Login login = new Login();
        login.setCustomer(customer);
        login.setPassword(Base64Coder.decodeString(registerRequest.getPassword()));// per passare la password criptata
        login.setEmail(registerRequest.getEmail());
        customer.setLogin(login);
        customerRepository.save(customer);
    }

    @Override
    public void logIn(LoginRequest loginRequest) throws NotUserFoundException {
        loginRequest.setPassword(Base64Coder.decodeString(loginRequest.getPassword()));
        if(!loginValidation.checkIfExist(loginRequest)){
            throw new NotUserFoundException("User not found");
        }//this is it for login
        //logins.ifPresent(loginList -> System.out.println(loginList.get(0).getCustomer())); //fastest way
    }

    @Override
    public Information information(String email) {
        Information information = new Information();
        Optional<List<Login>> login = logInRepository.findByEmail(email);
        List<Login> logins = login.orElseGet(ArrayList::new);
        if(!logins.isEmpty()){
            information.setRegistrationNumber(logins.get(0).getCustomer().getVehicleRegistrationNumber());
            information.setEmail(logins.get(0).getEmail());
            information.setFName(logins.get(0).getCustomer().getFirstName());
            information.setLName(logins.get(0).getCustomer().getLastName());
            information.setCustomerId(logins.get(0).getCustomer().getId());
        }
        return information;
    }
}
