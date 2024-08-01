package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.RouteExports;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({"Content-Type: application/json"})
public interface HotelClient {

    @RequestLine(RouteExports.GET_ROOM)
    GetRoomOutput getRoomById(@Param String roomId);
}
