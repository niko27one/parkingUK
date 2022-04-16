package com.parkinguk.parkinguk.entity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Slot")
@Getter
@Setter
@Builder
public class Slot {
    @Id
    @Column(name = "slot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime bookingDate;
    private LocalDateTime bookingToDate;
    private Boolean disable;
    private Boolean motorbike;
    private Boolean parent;
    private Boolean occupied;

    @ManyToOne(fetch = FetchType.LAZY)// when we run the hybernate, the spring makes a proxy object.
    @JoinColumn(name = "floorId")
    private Floor floor;

    @OneToOne(fetch = FetchType.LAZY)// when we run the hybernate, the spring makes a proxy object.
    @JoinColumn(name = "bookingId" , referencedColumnName = "bookingId")
    private Booking booking;
}
