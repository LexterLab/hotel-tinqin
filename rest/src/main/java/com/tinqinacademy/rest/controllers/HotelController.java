package com.tinqinacademy.rest.controllers;

import com.tinqinacademy.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.api.contracts.HotelService;
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

@RestController
@RequestMapping("api/v1/hotel")
@RequiredArgsConstructor
@Tag(name = "Hotel REST APIs")
public class HotelController {

    private final HotelService hotelService;


    @Operation(
            summary = "Search Rooms Rest API",
            description = "Search Rooms Rest API is used for searching rooms"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    @GetMapping("rooms")
    public ResponseEntity<SearchRoomOutput> searchRooms(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "4") Integer bedCount,
            @RequestParam(required = false, defaultValue = "single") String bedSize,
            @RequestParam(required = false, defaultValue = "shared") String bathroomType
            ) {
        SearchRoomOutput output = hotelService.searchRoom(
                SearchRoomInput.builder()
                        .bathroomType(bathroomType)
                        .bedSize(bedSize)
                        .endDate(endDate)
                        .startDate(startDate)
                        .bedCount(bedCount)
                        .build());
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Room By Id Rest API",
            description = "Get Room By Id REST API is used for retrieving a room by id"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    @GetMapping("{roomId}")
    public ResponseEntity<GetRoomOutput> getRoomById(@PathVariable String roomId) {
        GetRoomOutput output = hotelService.getRoom(GetRoomInput.builder()
                .roomId(roomId).build());
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(
            summary = "Book Room By Id Rest API",
            description = "Book Room By Id REST API is used for booking a room by id"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "HTTP STATUS 201 CREATED"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    @PostMapping("{roomId}")
    public ResponseEntity<BookRoomOutput> bookRoom(@PathVariable String roomId , @Valid @RequestBody BookRoomInput input) {
        BookRoomOutput output = hotelService.bookRoom(BookRoomInput.builder()
                .roomId(roomId)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNo(input.getPhoneNo())
                .build());

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Unbook Room By Id Rest API",
            description = "Unbook Room By Id REST API is used for unbooking a room by id"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    @DeleteMapping("{roomId}")
    public ResponseEntity<UnbookRoomOutput> unbookRoom(@PathVariable String roomId) {
        UnbookRoomOutput output = hotelService.unbookRoom(UnbookRoomInput.builder().roomId(roomId).build());
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
