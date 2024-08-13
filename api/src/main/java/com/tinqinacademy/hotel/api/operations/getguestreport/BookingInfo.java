package com.tinqinacademy.hotel.api.operations.getguestreport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookingInfo {
    private UUID userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<GuestInfo> guests;
}
