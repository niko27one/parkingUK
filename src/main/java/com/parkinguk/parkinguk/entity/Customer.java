package com.parkinguk.parkinguk.entity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String lastName;
    private LocalDateTime dob;
    private String vehicleRegistrationNumber;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "customer" )//Persist say to the hibernate object what to do with the dependency
    private Login login;

}
