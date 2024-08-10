package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNo;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNoInput;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNoOutput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class FindRoomByRoomNoProcessor extends BaseProcessor implements FindRoomByRoomNo {
    private final RoomRepository roomRepository;

    public FindRoomByRoomNoProcessor(ConversionService conversionService, Validator validator, RoomRepository roomRepository) {
        super(conversionService, validator);
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorOutput, FindRoomByRoomNoOutput> process(FindRoomByRoomNoInput input) {
        log.info("Start findRoomByRoomNo {}", input);
        return Try.of(() -> {
            validateInput(input);

            Room room = fetchRoomFromInput(input);
            FindRoomByRoomNoOutput output = FindRoomByRoomNoOutput
                    .builder()
                    .roomNo(room.getRoomNo())
                    .id(room.getId())
                    .build();
            log.info("End findRoomByRoomNo {}", output);
            return output;
        }).toEither()
                .mapLeft(throwable ->  Match(throwable).of(
                        validatorCase(throwable),
                        customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                        defaultCase(throwable)
                ));
    }

    private Room fetchRoomFromInput(FindRoomByRoomNoInput input) {
        log.info("Start fetchRoomFromInput {}", input);

        Room room = roomRepository.findByRoomNo(input.getRoomNo())
                .orElseThrow(() -> new ResourceNotFoundException("room", "roomNo", input.getRoomNo()));

        log.info("End fetchRoomFromInput {}", room);
        return room;
    }
}
