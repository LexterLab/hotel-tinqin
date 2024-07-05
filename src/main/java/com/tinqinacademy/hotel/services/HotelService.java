package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.models.ReserveRoom;
import com.tinqinacademy.hotel.models.RoomInput;
import com.tinqinacademy.hotel.models.RoomOutput;
import com.tinqinacademy.hotel.models.Test;
import com.tinqinacademy.hotel.models.input.BookRoomInput;
import com.tinqinacademy.hotel.models.input.GetRoomInput;
import com.tinqinacademy.hotel.models.input.SearchRoomInput;
import com.tinqinacademy.hotel.models.input.UnbookRoomInput;
import com.tinqinacademy.hotel.models.output.GetRoomOutput;

import java.util.List;

public interface HotelService {
    String bookRoom(Integer roomNumber);
    String checkRoomAvailability();
    RoomOutput addRoom(RoomInput input);
    String deleteRoom(Integer roomNumber);
    Test updateRoom(Integer roomNumber, Test test);
    RoomOutput getRoom(ReserveRoom input);
    List<String> searchRoom(SearchRoomInput input);
    GetRoomOutput getRoom(GetRoomInput input);
    void bookRoom(BookRoomInput input);
    void unbookRoom(UnbookRoomInput input);
}
