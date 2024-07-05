package com.tinqinacademy.hotel.models.output;

import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class GetRoomOutput {
    private String id;
    private BigDecimal price;
    private Integer floor;
    private BedSize bedSize;
    private BathroomType bathroomType;
    private List<LocalDate> datesOccupied;
}
