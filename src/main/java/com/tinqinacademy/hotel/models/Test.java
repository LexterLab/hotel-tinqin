package com.tinqinacademy.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Test {
    @Schema(example = "101")
    private Long roomNumber;
    @Schema(example = "2")
    private Integer capacity;
    @Schema(example = "True")
    private Boolean oceanView;
}
