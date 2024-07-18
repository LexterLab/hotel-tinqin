package com.tinqinacademy.hotel.persistence.models.booking;

import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.models.user.User;
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
    private Room room;
    private User user;

}
