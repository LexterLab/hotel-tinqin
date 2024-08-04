package com.tinqinacademy.hotel.api.operations.getguestreport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GuestInfo {
    @Schema(example = "Michael")
    private String firstName;
    @Schema(example = "Jordan")
    private String lastName;
    @Schema(example = "3232 3232 3232 3232")
    private String idCardNo;
    @Schema(example = "2024-01-01")
    private LocalDate idCardValidity;
    @Schema(example = "Authority")
    private String idCardIssueAuthority;
    @Schema(example = "2024-01-01")
    private LocalDate idCardIssueDate;
}
