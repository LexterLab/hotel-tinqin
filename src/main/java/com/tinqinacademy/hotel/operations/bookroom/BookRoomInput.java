package com.tinqinacademy.hotel.operations.bookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookRoomInput {
    @JsonIgnore
    private String roomId;
    @Schema(example = "2024-01-01")
    private LocalDate startDate;
    @Schema(example = "2024-01-01")
    private LocalDate endDate;
    @Schema(example = "Alexander")
    private String firstName;
    @Schema(example = "VP")
    private String lastName;
    @Schema(example = "+359742342342")
    private String phoneNo;
}
