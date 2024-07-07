package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.operations.getvisitorreport.GetVisitorsReportInput;
import com.tinqinacademy.hotel.operations.getvisitorreport.GetVisitorsReportOutput;
import com.tinqinacademy.hotel.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.services.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/system")
@Tag(name = "System REST APIs")
public class SystemController {
    private final SystemService systemService;

    @Operation(
            summary = "Register Room Visitor Rest API",
            description = "Register Room Visitor Rest API is registering visitor to a room"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "HTTP STATUS 201 CREATED"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    })
    @PostMapping("register")
    public ResponseEntity<RegisterVisitorOutput> register(@RequestBody RegisterVisitorInput input) {
        RegisterVisitorOutput output = systemService.registerVisitor(input);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Visitors register report Rest API",
            description = "UGet Visitors register report Rest API is for searching visitor registrations"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    })
    @GetMapping("register")
    public ResponseEntity<GetVisitorsReportOutput> getVisitorsReport(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNo,
            @RequestParam(required = false) String idCardNo,
            @RequestParam(required = false) LocalDate idCardValidity,
            @RequestParam(required = false) String idCardAuthority,
            @RequestParam(required = false) LocalDate idCardIssueDate,
            @RequestParam(required = false) String roomNo

            ) {
        GetVisitorsReportOutput output = systemService.getVisitorsReport(GetVisitorsReportInput.builder()
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
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    })
    @PostMapping("room")
    public ResponseEntity<CreateRoomOutput> createRoom(@RequestBody CreateRoomInput input) {
        CreateRoomOutput output = systemService.createRoom(input);
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
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    })
    @PutMapping("room/{roomId}")
    public ResponseEntity<UpdateRoomOutput> updateRoom(@PathVariable String roomId, @RequestBody UpdateRoomInput input) {
        UpdateRoomOutput output = systemService.updateRoom(UpdateRoomInput.builder()
                .roomId(roomId)
                .bedCount(input.getBedCount())
                .bathroomType(input.getBathroomType())
                .floor(input.getFloor())
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
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    })
    @PatchMapping("room/{roomId}")
    public ResponseEntity<PartialUpdateRoomOutput> partialUpdateRoom(@PathVariable String roomId,
                                                                     @RequestBody PartialUpdateRoomInput input) {
        PartialUpdateRoomOutput output = systemService.partialUpdateRoom(PartialUpdateRoomInput.builder()
                .roomId(roomId)
                .bedCount(input.getBedCount())
                .bathroomType(input.getBathroomType())
                .floor(input.getFloor())
                .roomNo(input.getRoomNo())
                .price(input.getPrice())
                .build());
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
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    })
    @DeleteMapping("room/{roomId}")
    public ResponseEntity<DeleteRoomOutput> deleteRoom(@PathVariable String roomId) {
        DeleteRoomOutput output = systemService.deleteRoom(DeleteRoomInput.builder().roomId(roomId).build());
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
