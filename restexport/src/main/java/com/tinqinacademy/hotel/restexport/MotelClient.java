package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNoOutput;
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
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Headers("Content-Type: application/json")
public interface MotelClient {
  @RequestLine("GET /api/v1/hotel/rooms?startDate={startDate}&endDate={endDate}&bedCount={bedCount}&bedSize={bedSize}&bathroomType={bathroomType}")
  SearchRoomOutput searchRooms(@Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate, @Param("bedCount") Integer bedCount,
      @Param("bedSize") String bedSize, @Param("bathroomType") String bathroomType);

  @RequestLine("GET /api/v1/hotel/{roomId}")
  GetRoomOutput getRoomById(@Param("roomId") String roomId);

  @RequestLine("POST /api/v1/hotel/{roomId}")
  BookRoomOutput bookRoom(@Param("roomId") String roomId, BookRoomInput input);

  @RequestLine("DELETE /api/v1/hotel/{bookingId}")
  UnbookRoomOutput unbookRoom(@Param("bookingId") String bookingId, UnbookRoomInput input);

  @RequestLine("GET /api/v1/hotel/room/{roomNo}")
  FindRoomByRoomNoOutput findRoom(@Param("roomNo") String roomNo);

  @RequestLine("POST /api/v1/system/{bookingId}/register")
  RegisterGuestOutput register(RegisterGuestInput input, @Param("bookingId") String bookingId);

  @RequestLine("GET /api/v1/system/register?startDate={startDate}&endDate={endDate}&firstName={firstName}&lastName={lastName}&userId={userId}&idCardNo={idCardNo}&idCardValidity={idCardValidity}&idCardAuthority={idCardAuthority}&idCardIssueDate={idCardIssueDate}&roomNo={roomNo}")
  GetGuestReportOutput getGuestReport(@Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate, @Param("firstName") String firstName,
      @Param("lastName") String lastName, @Param("userId") String userId,
      @Param("idCardNo") String idCardNo, @Param("idCardValidity") LocalDate idCardValidity,
      @Param("idCardAuthority") String idCardAuthority,
      @Param("idCardIssueDate") LocalDate idCardIssueDate, @Param("roomNo") String roomNo);

  @RequestLine("POST /api/v1/system/room")
  CreateRoomOutput createRoom(CreateRoomInput input);

  @RequestLine("PUT /api/v1/system/room/{roomId}")
  UpdateRoomOutput updateRoom(@Param("roomId") String roomId, UpdateRoomInput input);

  @RequestLine("PATCH /api/v1/system/room/{roomId}")
  PartialUpdateRoomOutput partialUpdateRoom(@Param("roomId") String roomId,
      PartialUpdateRoomInput input);

  @RequestLine("DELETE /api/v1/system/room/{roomId}")
  DeleteRoomOutput deleteRoom(@Param("roomId") String roomId);
}
