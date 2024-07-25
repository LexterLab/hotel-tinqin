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
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private String bedSize;
    @Column(nullable = false)
    private Integer bedCapacity;

    public void setBedSize(BedSize bedSize) {
        this.bedSize = bedSize.toString();
    }
}
