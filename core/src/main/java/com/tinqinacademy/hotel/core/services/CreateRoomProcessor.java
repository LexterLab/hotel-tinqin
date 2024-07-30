package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoom;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.errors.Error;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.persistence.enumerations.BedSize;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateRoomProcessor implements CreateRoom {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final ConversionService conversionService;

    @Override
    @Transactional
    public Either<ErrorOutput, CreateRoomOutput> process(CreateRoomInput input) {
        log.info("Start createRoom {}", input);

       return Try.of(() -> {
            validateRoomNo(input);

            List<BedSize> bedSizes = getBedSizes(input);

            List<Bed> beds = fetchRoomBeds(bedSizes);

            Room room = conversionService.convert(input, Room.class);
            room.setBeds(beds);

            roomRepository.save(room);

            CreateRoomOutput output = CreateRoomOutput.builder()
                    .roomId(room.getId().toString())
                    .build();

            log.info("End createRoom {}", output);

            return output;
        }).toEither()
               .mapLeft(throwable -> Match(throwable).of(
                       Case($(instanceOf(RoomNoAlreadyExistsException.class)), () -> ErrorOutput.builder()
                               .errors(List.of(Error.builder()
                                       .message(throwable.getMessage())
                                       .build()))
                               .build()),
                       Case($(), () -> ErrorOutput.builder()
                               .errors(List.of(Error.builder()
                                       .message("Unexpected error occurred")
                                       .build()))
                               .build())
               ));
    }

    private Long countExistingRooms(CreateRoomInput input) {
        log.info("Start countExistingRooms {}", input);

        Long existingRoomNoRooms = roomRepository.countAllByRoomNo(input.getRoomNo());

        log.info("End countExistingRooms {}", existingRoomNoRooms);
        return existingRoomNoRooms;
    }

    private void validateRoomNo(CreateRoomInput input) {
        log.info("Start validateRoomNo {}", input);

        Long existingRoomNoRooms = countExistingRooms(input);

        if (existingRoomNoRooms > 0) {
            throw new RoomNoAlreadyExistsException(input.getRoomNo());
        }

        log.info("End validateRoomNo {}", existingRoomNoRooms);
    }

    private List<BedSize> getBedSizes(CreateRoomInput input) {
        log.info("Start getBedSizes {}", input);

        List<BedSize> bedSizes = input.getBeds().stream()
                .map(bedSize -> BedSize.getByCode(bedSize.toString()))
                .toList();

        log.info("End getBedSizes {}", bedSizes);
        return bedSizes;
    }

    private List<Bed> fetchRoomBeds(List<BedSize> bedSizes) {
        log.info("Start fetchRoomBeds {}", bedSizes);

        List<Bed> roomBeds = bedRepository.findAllByBedSizeIn(bedSizes);

        log.info("End fetchRoomBeds {}", roomBeds);

        return roomBeds;
    }


}
