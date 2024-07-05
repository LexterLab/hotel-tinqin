package com.tinqinacademy.hotel.services.impl;

import com.tinqinacademy.hotel.models.ReserveRoom;
import com.tinqinacademy.hotel.models.RoomInput;
import com.tinqinacademy.hotel.models.RoomOutput;
import com.tinqinacademy.hotel.models.Test;
import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;
import com.tinqinacademy.hotel.models.input.BookRoomInput;
import com.tinqinacademy.hotel.models.input.GetRoomInput;
import com.tinqinacademy.hotel.models.input.SearchRoomInput;
import com.tinqinacademy.hotel.models.input.UnbookRoomInput;
import com.tinqinacademy.hotel.models.output.GetRoomOutput;
import com.tinqinacademy.hotel.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {
    @Override
    public String bookRoom(Integer roomNumber) {
        log.info("Booking room {}", roomNumber);
        return "book room number " + roomNumber;
    }

    @Override
    public String checkRoomAvailability() {
        log.info("Checking room availability");
        return "Hey bro available rooms: 403, 202, 101";
    }

    @Override
    public RoomOutput addRoom(RoomInput input) {
        log.info("Start addRoom {}", input);
        RoomOutput output = RoomOutput.builder().id(input.getId())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .bedCount(input.getBedCount())
                .bedSize(BedSize.getByCode(input.getBedSize()))
                .roomNumber(input.getRoomNumber())
                .build();
        log.info("End addRoom {}", output);
        return output;
    }

    @Override
    public String deleteRoom(Integer roomNumber) {
        log.info("Deleting room {}", roomNumber);
        return "Deleted room with: " + roomNumber;
    }

    @Override
    public Test updateRoom(Integer roomNumber, Test test) {
        log.info("Updating room {}", test);
        return test;
    }

    @Override
    public RoomOutput getRoom(ReserveRoom input) {
        log.info("Start getRoom {}", input);
        RoomOutput output = RoomOutput.builder()
                .roomNumber("403")
               .bathroomType(BathroomType.PRIVATE)
               .price(BigDecimal.valueOf(220))
               .bedSize(BedSize.getByCode(input.getBedSize()))
               .floor(input.getFloor())
               .id("dadasda")
               .bedCount(3)
               .build();
        log.info("End getRoom {}", output);
        return output;
    }


    @Override
    public List<String> searchRoom(SearchRoomInput input) {
        log.info("Start searchRoom {}", input);
        List<String> roomIds = List.of("2", "3", "4", "5", "6", "7", "8", "9");
        log.info("End searchRoom {}", roomIds);
        return roomIds;
    }

    @Override
    public GetRoomOutput getRoom(GetRoomInput input) {
        log.info("Start getRoom {}", input);
        GetRoomOutput output = GetRoomOutput.builder()
                .id(input.getRoomId())
                .price(BigDecimal.valueOf(23232))
                .floor(4)
                .bedSize(BedSize.KING_SIZE)
                .bathroomType(BathroomType.PRIVATE)
                .datesOccupied(List.of(LocalDate.now()))
                .build();
        log.info("End getRoom {}", output);
        return output;
    }

    @Override
    public void bookRoom(BookRoomInput input) {
        log.info("Start bookRoom {}", input);
        log.info("End bookRoom");
    }

    @Override
    public void unbookRoom(UnbookRoomInput input) {
        log.info("Start unbookRoom {}", input);
        log.info("End unbookRoom");
    }


}
