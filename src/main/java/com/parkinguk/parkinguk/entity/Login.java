package com.parkinguk.parkinguk.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "login")
@Getter
@Setter
public class Login {

    //annotation that state ID is the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increments the primary key
    private Long id;

    private String password;
    private String email;

    @OneToOne
    @JoinColumn(name = "customer_id" , referencedColumnName = "customer_id" , unique = true)
    private Customer customer;
}
