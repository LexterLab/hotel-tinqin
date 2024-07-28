package com.tinqinacademy.hotel.persistence.models.room;

import com.tinqinacademy.hotel.persistence.enumerations.BathroomType;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "bookings")
@Builder
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String roomNo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BathroomType bathroomType;
    @Column(nullable = false, updatable = false)
    private Integer floor;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "room", orphanRemoval = true)
    List<Booking> bookings = new ArrayList<>();;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "room_beds",
            joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bed_id", referencedColumnName = "id")
    )
    private List<Bed> beds;
}
