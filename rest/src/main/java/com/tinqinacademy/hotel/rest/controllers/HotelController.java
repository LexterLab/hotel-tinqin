package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoom;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNo;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNoInput;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNoOutput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoom;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoom;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.RestAPIRoutes;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoom;
import com.tinqinacademy.restexportprocessor.main.RestExport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Tag(name = "Hotel REST APIs")
public class HotelController extends BaseController {
    private final SearchRoom searchRoom;
    private final GetRoom getRoom;
    private final BookRoom bookRoom;
    private final UnbookRoom unbookRoom;
    private final FindRoomByRoomNo findRoomByRoomNo;

    public HotelController(SearchRoom searchRoom, GetRoom getRoom, BookRoom bookRoom, UnbookRoom unbookRoom, FindRoomByRoomNo findRoomByRoomNo) {
        this.searchRoom = searchRoom;
        this.getRoom = getRoom;
        this.bookRoom = bookRoom;
        this.unbookRoom = unbookRoom;
        this.findRoomByRoomNo = findRoomByRoomNo;
    }

    @Operation(
            summary = "Search Rooms Rest API",
            description = "Search Rooms Rest API is used for searching rooms"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 SUCCESS", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SearchRoomOutput.class))
            ),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST")
    })
    @RestExport
    @GetMapping(RestAPIRoutes.SEARCH_ROOMS)
    public ResponseEntity<?> searchRooms(

            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            @RequestParam(required = false) Integer bedCount,
            @RequestParam(required = false) String bedSize,
            @RequestParam(required = false) String bathroomType
            ) {
        Either<ErrorOutput, SearchRoomOutput> output = searchRoom.process(
                SearchRoomInput.builder()
                        .bathroomType(BathroomType.getByCode(bathroomType))
                        .bedSize(BedSize.getByCode(bedSize))
                        .endDate(endDate)
                        .startDate(startDate)
                        .bedCount(bedCount)
                        .build());
        return handleOutput(output, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Room By Id Rest API",
            description = "Get Room By Id REST API is used for retrieving a room by id"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 SUCCESS", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetRoomOutput.class))
            ),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND")
    }
    )
    @RestExport
    @GetMapping(RestAPIRoutes.GET_ROOM_DETAILS)
    public ResponseEntity<?> getRoomById(@PathVariable String roomId) {
        Either<ErrorOutput, GetRoomOutput> output = getRoom.process(GetRoomInput.builder()
                .roomId(roomId).build());
        return handleOutput(output, HttpStatus.OK);
    }

    @Operation(
            summary = "Book Room By Id Rest API",
            description = "Book Room By Id REST API is used for booking a room by id"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP STATUS 201 CREATED", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookRoomOutput.class))
            ),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND")
    }
    )
    @RestExport
    @PostMapping(RestAPIRoutes.BOOK_ROOM)
    public ResponseEntity<?> bookRoom(@PathVariable String roomId , @RequestBody BookRoomInput input) {
        Either<ErrorOutput,BookRoomOutput> output = bookRoom.process(BookRoomInput.builder()
                .roomId(roomId)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNo(input.getPhoneNo())
                .userId(input.getUserId())
                .build());

       return handleOutput(output, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Unbook Room By Id Rest API",
            description = "Unbook Room By Id REST API is used for unbooking a room by id"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 SUCCESS", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UnbookRoomOutput.class))
            ),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND")
    }
    )
    @RestExport
    @DeleteMapping(RestAPIRoutes.UNBOOK_ROOM)
    public ResponseEntity<?> unbookRoom(@PathVariable String bookingId, @RequestBody UnbookRoomInput input) {
       Either<ErrorOutput, UnbookRoomOutput>  output = unbookRoom.process(UnbookRoomInput
                .builder()
                .bookingId(bookingId)
                .userId(input.getUserId())
                .build());
        return handleOutput(output, HttpStatus.OK);
    }


    @Operation(
            summary = "Find Room By Room No Rest API",
            description = "Find Room By Room No REST API is used for finding  a room by room number"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 SUCCESS", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FindRoomByRoomNoOutput.class))
            ),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND")
    })
    @RestExport
    @GetMapping(RestAPIRoutes.FIND_ROOM)
    public ResponseEntity<?> findRoom(@PathVariable String roomNo) {
        Either<ErrorOutput, FindRoomByRoomNoOutput> output = findRoomByRoomNo.process(FindRoomByRoomNoInput
                .builder()
                .roomNo(roomNo)
                .build());

        return handleOutput(output, HttpStatus.OK);
    }
}
