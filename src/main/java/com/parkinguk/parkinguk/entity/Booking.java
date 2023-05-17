package com.parkinguk.parkinguk.entity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
@Getter
@Setter
@Builder
public class Booking {

    @Id
    @Column(name = "bookingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long amount;
    private LocalDateTime bookingDate;

    private LocalDateTime bookingToDate;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "booking")
    private Slot slot;

    @OneToOne
    @JoinColumn(name = "customer_id" , referencedColumnName = "customer_id")
    private Customer customer;
}
