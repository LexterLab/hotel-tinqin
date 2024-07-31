package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoom;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.persistence.enumerations.BedSize;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;

import static io.vavr.API.*;

import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
public class CreateRoomProcessor extends BaseProcessor implements CreateRoom {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    public CreateRoomProcessor(ConversionService conversionService, Validator validator, RoomRepository roomRepository, BedRepository bedRepository) {
        super(conversionService, validator);
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }


    @Override
    @Transactional
    public Either<ErrorOutput, CreateRoomOutput> process(CreateRoomInput input) {
        log.info("Start createRoom {}", input);

       return Try.of(() -> {
            validateInput(input);

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
                       validatorCase(throwable),
                       customCase(throwable, HttpStatus.BAD_REQUEST, RoomNoAlreadyExistsException.class ),
                       defaultCase(throwable)
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
