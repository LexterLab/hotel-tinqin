package com.tinqinacademy.hotel.api.operations.getguestreport;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookingReport {
    private List<BookingInfo> bookings;
}
