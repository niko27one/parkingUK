package com.parkinguk.parkinguk;
import com.parkinguk.parkinguk.config.FloorConfigInit;
import com.parkinguk.parkinguk.service.initSlots.ParkingInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingukApplication implements CommandLineRunner {
    @Autowired
    ParkingInit parkingInit;
    @Autowired
    FloorConfigInit floorConfigInit;


    public static void main(String[] args) {
        SpringApplication.run(ParkingukApplication.class, args);

    }
    @Override
    public void run(String... args) throws Exception{
        parkingInit.initFloors();
    }
}
