package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.RouteExports;
import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;

import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Headers({"Content-Type: application/json"})
public interface HotelClient {

    @RequestLine(RouteExports.GET_ROOM)
    GetRoomOutput getRoomById(@Param String roomId);

    @RequestLine(RouteExports.SEARCH_ROOM)
    SearchRoomOutput searchAvailableRooms(@Param LocalDateTime startDate, @Param LocalDateTime endDate,
                                          @Param Integer bedCount, @Param BedSize bedSize,
                                          @Param BathroomType bathroomType);

    @RequestLine(RouteExports.BOOK_ROOM)
    BookRoomOutput bookRoom(@Param String roomId, BookRoomInput bookRoomInput);

    @RequestLine(RouteExports.UNBOOK_ROOM)

    UnbookRoomOutput unbookRoom(@Param String roomId, UnbookRoomInput unbookRoomInput);

    @RequestLine(RouteExports.REGISTER_GUESTS)
    RegisterGuestOutput registerGuest(RegisterGuestInput registerGuestInput, @Param String bookingId);

    @RequestLine(RouteExports.GET_GUEST_REPORTS)
    GetGuestReportOutput getGuestReport(@Param LocalDateTime startDate, @Param LocalDateTime endDate,
                                        @Param String firstName, @Param String lastName, @Param String phoneNo,
                                        @Param String idCardNo, @Param LocalDate idCardValidity,
                                        @Param String idCardIssueAuthority, @Param LocalDate idCardIssueDate,

                                        @Param String roomNo);

    @RequestLine(RouteExports.CREATE_ROOM)
    CreateRoomOutput createRoom(CreateRoomInput createRoomInput);

    @RequestLine(RouteExports.UPDATE_ROOM)
    UpdateRoomOutput updateRoom(@Param String roomId, UpdateRoomInput updateRoomInput);

    @RequestLine(RouteExports.PARTIAL_UPDATE_ROOM)
    PartialUpdateRoomOutput partialUpdateRoom(@Param String roomId, PartialUpdateRoomInput partialUpdateRoomInput);

    @RequestLine(RouteExports.DELETE_ROOM)
    DeleteRoomOutput deleteRoom(@Param String roomId);
}

