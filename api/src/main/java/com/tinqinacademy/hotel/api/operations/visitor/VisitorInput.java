package com.tinqinacademy.hotel.api.operations.visitor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class VisitorInput {
    @Schema(example = "2025-01-01")
    @FutureOrPresent(message = "Field startDate must be future or present")
    @NotNull(message = "Field startDate should not be null")
    private LocalDate startDate;
    @Schema(example = "2025-01-01")
    @FutureOrPresent(message = "Field endDate must be future or present")
    @NotNull(message = "Field endDate should not be null")
    private LocalDate endDate;
    @Schema(example = "Michael")
    @NotBlank(message = "Field firstName must not be empty")
    @Size(min = 2, max = 20, message = "Field firstName must be between 2-20 characters")
    private String firstName;
    @Schema(example = "Jordan")
    @NotBlank(message = "Field lastName must not be empty")
    @Size(min = 2, max = 20, message = "Field lastName must be between 2-20 characters")
    private String lastName;
    @Schema(example = "+35984238424")
    @NotBlank(message = "Field phoneNo must not be empty")
    @Pattern(regexp = "^\\+[1-9]{1}[0-9]{3,14}$")
    private String phoneNo;
    @Schema(example = "3232 3232 3232 3232")
    @NotBlank(message = "field idCardNo must not be empty")
    private String idCardNo;
    @Schema(example = "2024-01-01")
    @Future(message = "Field idCardValidity must be valid")
    private LocalDate idCardValidity;
    @Schema(example = "Authority")
    @NotBlank(message = "Field idCardIssueAuthority must not be empty")
    @Size(min = 2, max = 100, message = "Field idCardIssueAuthority must be between 2-100 characters")
    private String idCardIssueAuthority;
    @Schema(example = "2024-01-01")
    @NotNull(message = "Field idCardIssueDate must not be null")
    @PastOrPresent(message = "Field idCardIssueDate must be past or present")
    private LocalDate idCardIssueDate;
    @Schema(example = "201A")
    @NotBlank(message = "Field roomNo must not be empty")
    @Size(min = 3, max = 5, message = "Field roomNo must be between 3-5 characters")
    private String roomNo;
}
