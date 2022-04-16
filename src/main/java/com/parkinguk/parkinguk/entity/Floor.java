package com.parkinguk.parkinguk.entity;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Floor")
@Getter
@Setter
@Builder
public class Floor {

    @Id
    @Column(name = "floor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int capacity;

    @OneToMany(mappedBy = "floor",cascade = CascadeType.ALL)
    private List<Slot> slots;

}
