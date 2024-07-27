package com.tinqinacademy.hotel.core.services;


import com.tinqinacademy.hotel.api.exceptions.AlreadyFinishedVisitException;
import com.tinqinacademy.hotel.api.exceptions.AlreadyStartedVisitException;
import com.tinqinacademy.hotel.api.exceptions.BookingDateNotAvailableException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.contracts.HotelService;

import com.tinqinacademy.hotel.core.mappers.BookingMapper;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.room.Room;

import com.tinqinacademy.hotel.persistence.models.user.User;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import com.tinqinacademy.hotel.persistence.specifications.RoomSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final RoomSpecification roomSpecification;

    @Override
    public SearchRoomOutput searchRoom(SearchRoomInput input) {
        log.info("Start searchRoom {}", input);

        List<UUID> availableRoomIds = roomRepository.findAll(roomSpecification.searchForAvailableRooms(input.getStartDate(),
                input.getEndDate(), input.getBedCount(), input.getBedSize(), input.getBathroomType())).stream()
                .map(Room::getId).toList();

        SearchRoomOutput searchRoomOutput = SearchRoomOutput.builder()
                .roomIds(availableRoomIds).build();
        log.info("End searchRoom {}", searchRoomOutput);
        return searchRoomOutput;
    }

    @Override
    public GetRoomOutput getRoom(GetRoomInput input) {
        log.info("Start getRoom {}", input);
        Room room = roomRepository.findById(input.getRoomId())
                        .orElseThrow(() -> new ResourceNotFoundException("room", "id", input.getRoomId().toString()));

        List<Booking> roomBookings =  bookingRepository.findBookingsByRoomIdAndCurrentDate(room.getId());
        room.setBookings(roomBookings);

        GetRoomOutput output = conversionService.convert(room, GetRoomOutput.class);

        log.info("End getRoom {}", output);
        return output;
    }

    @Override
    @Transactional
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Start bookRoom {}", input);

        User user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", input.getUserId().toString()));

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "Id", String.valueOf(input.getRoomId())));

        if (bookingRepository.countByRoomAndDates(room.getId(), input.getStartDate(), input.getEndDate()) > 0) {
            throw new BookingDateNotAvailableException();
        }

        Booking booking = BookingMapper.INSTANCE.bookRoomInputToBooking(input);
        booking.setUser(user);
        booking.setRoom(room);

        bookingRepository.save(booking);

        BookRoomOutput output = BookRoomOutput.builder().build();
        log.info("End bookRoom {}", output);
        return output;
    }

    @Override
    @Transactional
    public UnbookRoomOutput unbookRoom(UnbookRoomInput input) {
        log.info("Start unbookRoom {}", input);

        Booking booking = bookingRepository.findLatestByRoomIdAndUserId(input.getRoomId(), input.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("booking", "roomId & userId",
                                input.getRoomId().toString()));

        if (LocalDateTime.now().isAfter(booking.getEndDate())) {
            throw new AlreadyFinishedVisitException();
        } else if (LocalDateTime.now().isAfter(booking.getStartDate())) {
            throw new AlreadyStartedVisitException();
        }

        bookingRepository.delete(booking);

        UnbookRoomOutput output = UnbookRoomOutput.builder().build();
        log.info("End unbookRoom {}", output);
        return output;
    }
}
