package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.models.RoomInput;
import com.tinqinacademy.hotel.models.RoomOutput;
import com.tinqinacademy.hotel.models.Test;
import com.tinqinacademy.hotel.models.input.BookRoomInput;
import com.tinqinacademy.hotel.models.input.GetRoomInput;
import com.tinqinacademy.hotel.models.input.SearchRoomInput;
import com.tinqinacademy.hotel.models.input.UnbookRoomInput;
import com.tinqinacademy.hotel.models.output.BookRoomOutput;
import com.tinqinacademy.hotel.models.output.GetRoomOutput;
import com.tinqinacademy.hotel.models.output.UnbookRoomOutput;
import com.tinqinacademy.hotel.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/hotel")
@RequiredArgsConstructor
@Tag(name = "Hotel REST APIs")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("{roomNumber}/book")
    @Operation(
            summary = "Book Room Rest API",
            description = "Book Room Rest API is used for booking a room"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "HTTP STATUS 201 CREATED"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    public ResponseEntity<String> bookRoom(@Schema(example = "101") @PathVariable Integer roomNumber) {
        return new ResponseEntity<>(hotelService.bookRoom(roomNumber), HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(
            summary = "Get Available Rooms Rest API",
            description = "Get Available Rooms REST API is used to retrieve currently available rooms"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    public ResponseEntity<String> getAvailableRooms() {
        return ResponseEntity.ok(hotelService.checkRoomAvailability());
    }

    @PostMapping("")
    @Operation(
            summary = "Add Room Rest API",
            description = "Add Room Rest API is used for creating a room by admin"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "HTTP STATUS 201 CREATED"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    public ResponseEntity<RoomOutput> addRoom(@RequestBody RoomInput input) {
        return new ResponseEntity<>(hotelService.addRoom(input), HttpStatus.CREATED);
    }



    @PutMapping("{roomNumber}")
    @Operation(
            summary = "Update Room Rest API",
            description = "Update Room Rest API is used for updating a room by id"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    public ResponseEntity<Test> updateRoom(@PathVariable Integer roomNumber, @RequestBody Test test) {
        return ResponseEntity.ok(hotelService.updateRoom(roomNumber, test));
    }


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
    public ResponseEntity<List<String>> searchRooms(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "4") Integer bedCount,
            @RequestParam(required = false, defaultValue = "single") String bedSize,
            @RequestParam(required = false, defaultValue = "shared") String bathroomType
            ) {
        return  ResponseEntity.ok(hotelService.searchRoom(
                SearchRoomInput.builder()
                        .bathroomType(bathroomType)
                        .bedSize(bedSize)
                        .endDate(endDate)
                        .startDate(startDate)
                        .bedCount(bedCount)
                        .build()
        ));
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
        return ResponseEntity.ok(hotelService.getRoom(new GetRoomInput(roomId)));
    }

    @Operation(
            summary = "Book Room By Id Rest API",
            description = "Book Room By Id REST API is used for booking a room by id"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    @PostMapping("{roomId}")
    public ResponseEntity<BookRoomOutput> bookRoom(@PathVariable String roomId, @RequestBody BookRoomInput input) {

        return new ResponseEntity<>(hotelService.bookRoom(BookRoomInput.builder()
                .roomId(roomId)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNo(input.getPhoneNo())
                .build()), HttpStatus.CREATED);
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
        return ResponseEntity.ok( hotelService.unbookRoom(new UnbookRoomInput(roomId)));
    }
}
