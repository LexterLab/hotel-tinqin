package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoom;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.vavr.API.*;

@Service
@Slf4j
public class GetRoomProcessor extends BaseProcessor implements GetRoom {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public GetRoomProcessor(ConversionService conversionService, RoomRepository roomRepository, BookingRepository bookingRepository) {
        super(conversionService);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, GetRoomOutput> process(GetRoomInput input) {
        log.info("Start getRoom {}", input);

        return Try.of(() -> {
            Room room = fetchRoomFromInput(input);

            List<Booking> roomBookings =  bookingRepository.findBookingsByRoomIdAndCurrentDate(room.getId());
            room.setBookings(roomBookings);

            GetRoomOutput output = conversionService.convert(room, GetRoomOutput.class);

            log.info("End getRoom {}", output);
            return output;
        }) .toEither()
                .mapLeft(throwable ->  Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                        defaultCase(throwable)
                ));
    }

    private Room fetchRoomFromInput(GetRoomInput input) {
        log.info("Start fetchRoom {}", input);
        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("room", "id", input.getRoomId().toString()));

        log.info("End fetchRoom {}", room);
        return room;
    }
}
