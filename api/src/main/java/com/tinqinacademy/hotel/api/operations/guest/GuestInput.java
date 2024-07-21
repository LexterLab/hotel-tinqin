package com.tinqinacademy.hotel.api.operations.guest;

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
public class GuestInput {
    @Schema(example = "Michael")
    @NotBlank(message = "Field firstName must not be empty")
    @Size(min = 2, max = 20, message = "Field firstName must be between 2-20 characters")
    private String firstName;
    @Schema(example = "Jordan")
    @NotBlank(message = "Field lastName must not be empty")
    @Size(min = 2, max = 20, message = "Field lastName must be between 2-20 characters")
    private String lastName;
    @Past(message = "Field birthDay must be a past date")
    @NotNull(message = "Field birthDay cannot be null")
    private LocalDate birthday;
    @Schema(example = "3232 3232 3232 3232")
    @NotBlank(message = "field idCardNo must not be empty")
    private String idCardNo;
    @Schema(example = "2025-01-01")
    @Future(message = "Field idCardValidity must be valid")
    @NotNull(message = "Field idCardValidity should not be null")
    private LocalDate idCardValidity;
    @Schema(example = "Authority")
    @NotBlank(message = "Field idCardIssueAuthority must not be empty")
    @Size(min = 2, max = 50, message = "Field idCardIssueAuthority must be between 2-100 characters")
    private String idCardIssueAuthority;
    @Schema(example = "2025-01-01")
    @NotNull(message = "Field idCardIssueDate must not be null")
    @PastOrPresent(message = "Field idCardIssueDate must be past or present")
    private LocalDate idCardIssueDate;
}
