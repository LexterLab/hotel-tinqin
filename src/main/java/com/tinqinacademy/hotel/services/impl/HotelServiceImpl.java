package com.tinqinacademy.hotel.services.impl;

import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;
import com.tinqinacademy.hotel.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.operations.unbookroom.UnbookRoomOutput;
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
    public SearchRoomOutput searchRoom(SearchRoomInput input) {
        log.info("Start searchRoom {}", input);
        List<String> roomIds = List.of("2", "3", "4", "5", "6", "7", "8", "9");
        SearchRoomOutput searchRoomOutput = SearchRoomOutput.builder()
                .roomIds(roomIds).build();
        log.info("End searchRoom {}", searchRoomOutput);
        return searchRoomOutput;
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
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Start bookRoom {}", input);
        BookRoomOutput output = BookRoomOutput.builder().build();
        log.info("End bookRoom {}", output);
        return output;
    }

    @Override
    public UnbookRoomOutput unbookRoom(UnbookRoomInput input) {
        log.info("Start unbookRoom {}", input);
        UnbookRoomOutput output = UnbookRoomOutput.builder().build();
        log.info("End unbookRoom {}", output);
        return output;
    }
}
