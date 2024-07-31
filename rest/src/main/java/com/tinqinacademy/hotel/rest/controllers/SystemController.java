package com.tinqinacademy.hotel.rest.controllers;


import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoom;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoom;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReport;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoom;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuest;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoom;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.api.RestAPIRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "System REST APIs")
public class SystemController extends BaseController {
    private final RegisterGuest registerGuest;
    private final GetGuestReport getGuestReport;
    private final CreateRoom createRoom;
    private final UpdateRoom updateRoom;
    private final PartialUpdateRoom partialUpdateRoom;
    private final DeleteRoom deleteRoom;

    @Operation(
            summary = "Register Room Guest Rest API",
            description = "Register Room Guest Rest API is registering guest to a room"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "HTTP STATUS 201 CREATED"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
    })
    @PostMapping(RestAPIRoutes.REGISTER_VISITOR)
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterGuestInput input,
            @PathVariable UUID bookingId
    ) {
        Either<ErrorOutput, RegisterGuestOutput> output = registerGuest.process(RegisterGuestInput
                .builder()
                .bookingId(bookingId)
                .guests(input.getGuests())
                .build());
        return handleOutput(output, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Guest  report Rest API",
            description = "Get Guest report Rest API is for searching guest registrations"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
    })
    @GetMapping(RestAPIRoutes.GET_VISITORS_REPORT)
    public ResponseEntity<?> getGuestReport(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNo,
            @RequestParam(required = false) String idCardNo,
            @RequestParam(required = false) LocalDate idCardValidity,
            @RequestParam(required = false) String idCardAuthority,
            @RequestParam(required = false) LocalDate idCardIssueDate,
            @RequestParam(required = false) String roomNo

            ) {
       Either<ErrorOutput,GetGuestReportOutput> output = getGuestReport.process(GetGuestReportInput.builder()
                .idCardIssueAuthority(idCardAuthority)
                .idCardIssueDate(idCardIssueDate)
                .idCardNo(idCardNo)
                .idCardValidity(idCardValidity)
                .roomNo(roomNo)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .startDate(startDate)
                .build());
        return handleOutput(output, HttpStatus.OK);
    }


    @Operation(
            summary = "Create Room Rest API",
            description = "Create Room Rest API is for searching visitor registrations"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "HTTP STATUS 201 CREATED"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
    })
    @PostMapping(RestAPIRoutes.CREATE_ROOM)
    public ResponseEntity<?> createRoom(@Valid @RequestBody CreateRoomInput input) {
        Either<ErrorOutput, CreateRoomOutput> result = createRoom.process(input);
        return handleOutput(result, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Room Rest API",
            description = "Update Room Rest API is for updating rooms"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
    })
    @PutMapping(RestAPIRoutes.UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@PathVariable UUID roomId, @Valid @RequestBody UpdateRoomInput input) {
        Either<ErrorOutput, UpdateRoomOutput> output = updateRoom.process(UpdateRoomInput.builder()
                .roomId(roomId)
                .bathroomType(input.getBathroomType())
                .floor(input.getFloor())
                .beds(input.getBeds())
                .roomNo(input.getRoomNo())
                .price(input.getPrice())
                .build());
        return handleOutput(output, HttpStatus.OK);
    }

    @Operation(
            summary = "Partial Update Room Rest API",
            description = "Partial Update Room Rest API is for partially updating rooms"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
    })
    @PatchMapping(RestAPIRoutes.PARTIAL_UPDATE_ROOM)
    public ResponseEntity<?> partialUpdateRoom(@PathVariable UUID roomId,
                                                                     @RequestBody @Valid PartialUpdateRoomInput input) {
        Either<ErrorOutput,PartialUpdateRoomOutput> output = partialUpdateRoom.process(PartialUpdateRoomInput.builder()
                .roomId(roomId)
                .beds(input.getBeds())
                .bathroomType(input.getBathroomType())
                .floor(input.getFloor())
                .roomNo(input.getRoomNo())
                .price(input.getPrice())
                .build());
        return handleOutput(output, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Room Rest API",
            description = "Delete Room Rest API is for deleting rooms"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND")
    })
    @DeleteMapping(RestAPIRoutes.DELETE_ROOM)
    public ResponseEntity<?> deleteRoom(@PathVariable UUID roomId) {
        Either<ErrorOutput, DeleteRoomOutput> output = deleteRoom.process(DeleteRoomInput.builder().roomId(roomId).build());
        return handleOutput(output, HttpStatus.OK);
    }
}
