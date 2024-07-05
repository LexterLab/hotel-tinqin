package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.models.input.GetVisitorsReportInput;
import com.tinqinacademy.hotel.models.input.RegisterVisitorInput;
import com.tinqinacademy.hotel.models.output.GetVisitorsReportOutput;
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
    public ResponseEntity<Void> register(@RequestBody RegisterVisitorInput input) {
        systemService.registerVisitor(input);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
        return ResponseEntity.ok(systemService.getVisitorsReport(GetVisitorsReportInput.builder()
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
                .build()));
    }
}
