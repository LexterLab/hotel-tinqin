package com.tinqinacademy.hotel.core.services;


import com.tinqinacademy.hotel.api.exceptions.BookingDateNotAvailableException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.contracts.HotelService;

import com.tinqinacademy.hotel.core.mappers.BedMapper;
import com.tinqinacademy.hotel.core.mappers.BookingMapper;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.room.Room;

import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final BedRepository bedRepository;

    @Override
    public SearchRoomOutput searchRoom(SearchRoomInput input) {
        log.info("Start searchRoom {}", input);

        List<UUID> availableRoomIds = bookingRepository.searchForAvailableRooms(input);


        SearchRoomOutput searchRoomOutput = SearchRoomOutput.builder()
                .roomIds(availableRoomIds).build();
        log.info("End searchRoom {}", searchRoomOutput);
        return searchRoomOutput;
    }

    @Override
    @Transactional
    public GetRoomOutput getRoom(GetRoomInput input) {
        log.info("Start getRoom {}", input);
        Room room = roomRepository.findById(input.getRoomId())
                        .orElseThrow(() -> new ResourceNotFoundException("room", "id", input.getRoomId().toString()));

        List<UUID> roomBedsIds = roomRepository.findRoomBedIds(room);

        List<Bed> beds = new ArrayList<>();

        for (UUID id : roomBedsIds) {
            Bed bed = bedRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("bed", "id", id.toString()));
            beds.add(bed);
        }

        List<LocalDateTime> datesOccupied = bookingRepository.findAllDatesOccupiedByRoomId(room.getId())
                .stream().toList();


        GetRoomOutput output = GetRoomOutput.builder()
                .id(room.getId())
                .price(room.getPrice())
                .floor(room.getFloor())
                .beds(BedMapper.INSTANCE.bedsToBedOutputs(beds))
                .bathroomType(room.getBathroomType())
                .datesOccupied(datesOccupied)
                .build();

        log.info("End getRoom {}", output);
        return output;
    }

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Start bookRoom {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "Id", String.valueOf(input.getRoomId())));

        if (bookingRepository.existByDates(input.getStartDate(), input.getEndDate(), room.getId())) {
            throw new BookingDateNotAvailableException();
        }

        Booking booking = BookingMapper.INSTANCE.bookRoomInputToBooking(input);

        bookingRepository.save(booking);

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
