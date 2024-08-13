package com.tinqinacademy.hotel.api.operations.getguestreport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class GuestOutput {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Schema(example = "Michael")
    private String firstName;
    @Schema(example = "Jordan")
    private String lastName;
    @Schema(example = "+35984238424")
    private UUID userId;
    @Schema(example = "3232 3232 3232 3232")
    private String idCardNo;
    @Schema(example = "2024-01-01")
    private LocalDate idCardValidity;
    @Schema(example = "Authority")
    private String idCardIssueAuthority;
    @Schema(example = "2024-01-01")
    private LocalDate idCardIssueDate;
}
