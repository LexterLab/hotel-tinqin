package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoom;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.errors.Error;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteRoomProcessor implements DeleteRoom {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Either<ErrorOutput,DeleteRoomOutput> process(DeleteRoomInput input) {
        log.info("Start deleteRoom {}", input);

       return Try.of(() -> {
           Room room = fetchRoomFromInput(input);

           bookingRepository.deleteAll(room.getBookings());

           roomRepository.delete(room);
           DeleteRoomOutput output = DeleteRoomOutput.builder().build();

           log.info("End deleteRoom {}", output);
           return output;
        }).toEither()
               .mapLeft(throwable -> Match(throwable).of(
                       Case($(instanceOf(ResourceNotFoundException.class)), () -> ErrorOutput.builder()
                               .errors(List.of(Error.builder()
                                       .message(throwable.getMessage())
                                       .build()))
                               .build())
               ));

    }

    private Room fetchRoomFromInput(DeleteRoomInput input) {
        log.info("Start fetchRoom {}", input);
        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        log.info("End fetchRoom {}", room);
        return room;
    }
}