package com.tinqinacademy.hotel.api.operations.updateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class UpdateRoomInput implements OperationInput {
    @JsonIgnore
    @UUID(message = "Field roomId must be UUID")
    @NotBlank(message = "Field roomId must not be blank")
    private String roomId;
    private List<BedSize> beds;
    @Schema(example = "private")
    @NotNull(message = "Field bathroomType must not be null")
    private BathroomType bathroomType;

    @Schema(example = "4")
    @Min(value = 1, message = "Field floor must be minimum 1")
    @Max(value = 10, message = "Field floor must be maximum 12")
    @NotNull(message = "Field floor cannot be null")
    private Integer floor;

    @Schema(example = "201A")
    @NotBlank(message = "Field roomNo must not be empty")
    @Size(min = 4, max = 4, message = "Field roomNo must be 4 characters")
    private String roomNo;

    @Schema(example = "20000")
    @PositiveOrZero(message = "Field price must be min 0")
    @NotNull(message = "Field price cannot be null")
    private BigDecimal price;
}
