package com.tinqinacademy.hotel.persistence.models.booking;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {
    private UUID id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID roomId;
    private UUID userId;

}
