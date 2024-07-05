package com.tinqinacademy.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoomInput {
    @Schema(example = "1")
    private String id;
    @Min(value = 4, message = "Maximum of 4 beds  possible for rooms")
    private Integer bedCount;
    private String bedSize;
    private Integer floor;
    private String roomNumber;
    private BigDecimal price;
    private String bathroomType;


}
