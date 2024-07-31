package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.exceptions.InputValidationException;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoom;
import com.tinqinacademy.hotel.api.exceptions.BookingDateNotAvailableException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.core.mappers.BookingMapper;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.models.user.User;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static io.vavr.API.*;

@Service
@Slf4j
public class BookRomProcessor extends BaseProcessor implements BookRoom {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookRomProcessor(ConversionService conversionService, Validator validator, UserRepository userRepository, BookingRepository bookingRepository, RoomRepository roomRepository) {
        super(conversionService, validator);
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }


    @Override
    @Transactional
    public Either<ErrorOutput, BookRoomOutput> process(BookRoomInput input) {
        log.info("Start bookRoom {}", input);

        return Try.of(() ->{

            validateInput(input);

            User user = fetchUserFromInput(input);

            Room room = fetchRoomFromInput(input);

            checkIfBookingAvailable(input, room);

            Booking booking = BookingMapper.INSTANCE.bookRoomInputToBooking(input);
            booking.setUser(user);
            booking.setRoom(room);

            bookingRepository.save(booking);

            BookRoomOutput output = BookRoomOutput.builder().build();
            log.info("End bookRoom {}", output);
            return output;
        }).toEither()
                .mapLeft(throwable ->  Match(throwable).of(
                        validatorCase(throwable, InputValidationException.class),
                        customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                        customCase(throwable, HttpStatus.BAD_REQUEST, BookingDateNotAvailableException.class),
                        defaultCase(throwable)
                ));

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

    private User fetchUserFromInput(BookRoomInput input) {
        log.info("Start fetchUserFromInput {}", input);

        User user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", input.getUserId().toString()));

        log.info("End fetchUserFromInput {}", user);

        return user;
    }

    private Room fetchRoomFromInput(BookRoomInput input) {
        log.info("Start fetchRoomFromInput {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "Id", String.valueOf(input.getRoomId())));

        log.info("End fetchRoomFromInput {}", room);

        return room;
    }
}
