package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoom;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class DeleteRoomProcessor extends BaseProcessor implements DeleteRoom {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public DeleteRoomProcessor(ConversionService conversionService, Validator validator, RoomRepository roomRepository, BookingRepository bookingRepository) {
        super(conversionService, validator);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }


    @Override
    public Either<ErrorOutput,DeleteRoomOutput> process(DeleteRoomInput input) {
        log.info("Start deleteRoom {}", input);

       return Try.of(() -> {
           validateInput(input);
           Room room = fetchRoomFromInput(input);

           bookingRepository.deleteAll(room.getBookings());

           roomRepository.delete(room);

           DeleteRoomOutput output = DeleteRoomOutput.builder().build();

           log.info("End deleteRoom {}", output);
           return output;
        }).toEither()
               .mapLeft(throwable -> Match(throwable).of(
                      validatorCase(throwable),
                      customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                      defaultCase(throwable)
               ));

    }

    private Room fetchRoomFromInput(DeleteRoomInput input) {
        log.info("Start fetchRoom {}", input);
        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId()));

        log.info("End fetchRoom {}", room);
        return room;
    }
}
