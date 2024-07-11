package com.tinqinacademy.hotel.api.operations.visitor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class VisitorOutput {
    @Schema(example = "2024-01-01")
    private LocalDate startDate;
    @Schema(example = "2024-01-01")
    private LocalDate endDate;
    @Schema(example = "Michael")
    private String firstName;
    @Schema(example = "Jordan")
    private String lastName;
    @Schema(example = "+35984238424")
    private String phoneNo;
    @Schema(example = "3232 3232 3232 3232")
    private String idCardNo;
    @Schema(example = "2024-01-01")
    private LocalDate idCardValidity;
    @Schema(example = "Authority")
    private String idCardIssueAuthority;
    @Schema(example = "2024-01-01")
    private LocalDate idCardIssueDate;
    @Schema(example = "201A")
    private String roomNo;
}
