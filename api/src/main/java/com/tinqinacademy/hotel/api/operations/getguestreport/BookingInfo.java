package com.tinqinacademy.hotel.api.operations.getguestreport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookingInfo {
    @Schema(example = "+35984238424")
    private String phoneNo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<GuestInfo> guests;
}
