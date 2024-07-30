package com.tinqinacademy.hotel.rest.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tinqinacademy.hotel.api.contracts.*;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomProcessor;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.api.RestAPIRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
public class SystemController {
    private final RegisterGuestService registerGuestService;
    private final GetGuestReportService getGuestReportService;
    private final CreateRoomProcessor createRoomProcessor;
    private final UpdateRoomService updateRoomService;
    private final PartialUpdateRoomService partialUpdateRoomService;
    private final DeleteRoomService deleteRoomService;

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
    public ResponseEntity<RegisterGuestOutput> register(
            @Valid @RequestBody RegisterGuestInput input,
            @PathVariable UUID bookingId
    ) {
        RegisterGuestOutput output = registerGuestService.registerGuest(RegisterGuestInput
                .builder()
                .bookingId(bookingId)
                .guests(input.getGuests())
                .build());
        return new ResponseEntity<>(output, HttpStatus.CREATED);
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
    public ResponseEntity<GetGuestReportOutput> getGuestReport(
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
        GetGuestReportOutput output = getGuestReportService.getGuestReport(GetGuestReportInput.builder()
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
        return new ResponseEntity<>(output, HttpStatus.OK);
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
    public ResponseEntity<CreateRoomOutput> createRoom(@Valid @RequestBody CreateRoomInput input) {
        CreateRoomOutput output = createRoomProcessor.process(input);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
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
    public ResponseEntity<UpdateRoomOutput> updateRoom(@PathVariable UUID roomId, @Valid @RequestBody UpdateRoomInput input) {
        UpdateRoomOutput output = updateRoomService.updateRoom(UpdateRoomInput.builder()
                .roomId(roomId)
                .bathroomType(input.getBathroomType())
                .floor(input.getFloor())
                .beds(input.getBeds())
                .roomNo(input.getRoomNo())
                .price(input.getPrice())
                .build());
        return new ResponseEntity<>(output, HttpStatus.OK);
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
    public ResponseEntity<PartialUpdateRoomOutput> partialUpdateRoom(@PathVariable UUID roomId,
                                                                     @RequestBody @Valid PartialUpdateRoomInput input)
            throws JsonPatchException, JsonProcessingException {
        PartialUpdateRoomOutput output = partialUpdateRoomService.partialUpdateRoom(PartialUpdateRoomInput.builder()
                .roomId(roomId)
                .beds(input.getBeds())
                .bathroomType(input.getBathroomType())
                .floor(input.getFloor())
                .roomNo(input.getRoomNo())
                .price(input.getPrice())
                .build()); ;
        return new ResponseEntity<>(output, HttpStatus.OK);
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
    public ResponseEntity<DeleteRoomOutput> deleteRoom(@PathVariable UUID roomId) {
        DeleteRoomOutput output = deleteRoomService.deleteRoom(DeleteRoomInput.builder().roomId(roomId).build());
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
