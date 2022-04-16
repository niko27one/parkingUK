package com.parkinguk.parkinguk.controller;
import antlr.StringUtils;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.web.HazelcastHttpSession;
import com.parkinguk.parkinguk.dto.*;
import com.parkinguk.parkinguk.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

//Spring web annotations

@RestController
@RequestMapping( "/parking/v1")
public class LoginController {
    private static final String principalIndexName = Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_INDEX_NAME;

    @Autowired
    private CustomerService customerService;
    @Autowired
    Hazelcast4IndexedSessionRepository sessionRepository;
    @Autowired
    HazelcastInstance hazelcastInstance;

    public void createSession(HttpServletRequest request , String email){
        String resultSession ="";
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
            session.setAttribute(principalIndexName, session.getId());
            session.setAttribute("customerEmail",email);
            resultSession =  "Session created: " + session.getId();
        } else {
            resultSession =  "Session already exists: " + session.getId();
        }
        System.out.println(resultSession);

    }

    @PostMapping("/information")
    public ResponseEntity<Information> information(@RequestBody InformationReq informationReq){
        Information information = customerService.information(informationReq.getEmail());
        return new ResponseEntity<>(information,HttpStatus.OK);
    }

    @GetMapping("/validateSession")
    public ResponseEntity<ValidateSession> validate(HttpServletRequest req){
        ValidateSession validateSession = new ValidateSession();
        HttpSession session = req.getSession(false);
        if(session != null && sessionRepository.findByPrincipalName(session.getId()).get(session.getId()) != null && session.getAttribute("customerEmail") !=null){
            validateSession.setMessageValidate("loggedIn");
            validateSession.setEmail((String) session.getAttribute("customerEmail"));
        }
        return new ResponseEntity<>(validateSession,HttpStatus.OK);
    }

    @PostMapping("/invalidateSession")
    public ResponseEntity<Void> invalidateSession(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register/customer")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest , HttpServletRequest request) throws Exception {
        RegisterResponse registerResponse = new RegisterResponse();
        if (registerRequest.getFirstName()== null || registerRequest.getFirstName().isEmpty() || registerRequest.getDob() == null || registerRequest.getVehicleRegistrationNumber() == null || registerRequest.getVehicleRegistrationNumber().isEmpty() || registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty() || registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty() || registerRequest.getLastName() == null || registerRequest.getLastName().isEmpty() ){
            registerResponse.setMessage("Some fields are missing");
            return new ResponseEntity<>(registerResponse.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if(!registerRequest.getFirstName().matches("[A-Za-z]*")){
            registerResponse.setMessage("only plain characters accepted, please re-enter the name");
            return new ResponseEntity<>(registerResponse.getMessage(), HttpStatus.BAD_REQUEST);
        }
        customerService.register(registerRequest);
        registerResponse.setMessage("Customer created");
        createSession( request ,  registerRequest.getEmail());
        return new ResponseEntity<>(registerResponse.getMessage(), HttpStatus.CREATED);
    }

    @PostMapping(value = "login/customer" )
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        customerService.logIn(loginRequest);
        loginResponse.setMessage("Login successful");
        createSession( request ,  loginRequest.getEmail());
        return new ResponseEntity<>(loginResponse.getMessage(),HttpStatus.OK);
    }
}
