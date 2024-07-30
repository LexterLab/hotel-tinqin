package com.tinqinacademy.hotel.api.operations.bookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookRoomInput implements OperationInput {
    @JsonIgnore
    private UUID roomId;
    @Schema(example = "2025-01-01")
    @FutureOrPresent(message = "Field startDate must be future or present")
    @NotNull(message = "Field startDate should not be null")
    private LocalDateTime startDate;
    @Schema(example = "2025-01-01")
    @FutureOrPresent(message = "Field endDate must be future or present")
    @NotNull(message = "Field endDate should not be null")
    private LocalDateTime endDate;
    @Schema(example = "Michael")
    @NotBlank(message = "Field firstName must not be empty")
    @Size(min = 2, max = 20, message = "Field firstName must be between 2-20 characters")
    private String firstName;
    @Schema(example = "Jordan")
    @NotBlank(message = "Field lastName must not be empty")
    @Size(min = 2, max = 20, message = "Field lastName must be between 2-20 characters")
    private String lastName;
    @Schema(example = "+359742342342")
    @NotEmpty(message = "Field phoneNo should not be empty")
    @Pattern(regexp = "^\\+[1-9]{1}[0-9]{3,14}$")
    private String phoneNo;
    @NotNull(message = "Field userId must not be null")
    private UUID userId;
}
