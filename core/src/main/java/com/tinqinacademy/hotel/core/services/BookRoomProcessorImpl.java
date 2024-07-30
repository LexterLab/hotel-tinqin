package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomProcessor;
import com.tinqinacademy.hotel.api.exceptions.BookingDateNotAvailableException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.core.mappers.BookingMapper;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.models.user.User;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookRoomProcessorImpl implements BookRoomProcessor {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    @Override
    public BookRoomOutput process(BookRoomInput input) {
        log.info("Start bookRoom {}", input);

        User user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", input.getUserId().toString()));

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "Id", String.valueOf(input.getRoomId())));

        checkIfBookingAvailable(input, room);

        Booking booking = BookingMapper.INSTANCE.bookRoomInputToBooking(input);
        booking.setUser(user);
        booking.setRoom(room);

        bookingRepository.save(booking);

        BookRoomOutput output = BookRoomOutput.builder().build();
        log.info("End bookRoom {}", output);
        return output;
    }

    private void checkIfBookingAvailable(BookRoomInput input, Room room) {
        log.info("Start checkIfBookingAvailable {}", input);
        Long bookedByRooms =  bookingRepository
                .countByRoomAndDates(room.getId(), input.getStartDate(), input.getEndDate());

        if (bookedByRooms > 0) {
            throw new BookingDateNotAvailableException();
        }
        log.info("End checkIfBookingAvailable {}", bookedByRooms);
    }
}
