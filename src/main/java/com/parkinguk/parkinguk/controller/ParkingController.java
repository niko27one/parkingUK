package com.parkinguk.parkinguk.controller;
import com.parkinguk.parkinguk.service.initSlots.ParkingInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/parkingInit/v1")
public class ParkingController {

    @Autowired
    private ParkingInit parkingInit;

    @GetMapping("/")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");

        return modelAndView;
    }

    @PostMapping("/")
    public ResponseEntity <Void> slotsInit() throws Exception {
        parkingInit.initFloors();
        parkingInit.initSlots();
        return new ResponseEntity<>(HttpStatus.OK);
    } //void will not return the object but only the http status

}
