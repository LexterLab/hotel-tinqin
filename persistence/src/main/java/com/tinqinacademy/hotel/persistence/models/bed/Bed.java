package com.tinqinacademy.hotel.persistence.models.bed;

import com.tinqinacademy.hotel.api.models.constants.BedSize;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "beds")
public class Bed {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BedSize bedSize;
    @Column(nullable = false)
    private Integer bedCapacity;
}
