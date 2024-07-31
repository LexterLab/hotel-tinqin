package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.exceptions.InputValidationException;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoom;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.enumerations.BedSize;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.List;

import static io.vavr.API.*;

@Service
@Slf4j
public class UpdateRoomProcessor extends BaseProcessor implements UpdateRoom {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    public UpdateRoomProcessor(ConversionService conversionService, Validator validator, RoomRepository roomRepository, BedRepository bedRepository) {
        super(conversionService, validator);
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }


    @Override
    public Either<ErrorOutput, UpdateRoomOutput> process(UpdateRoomInput input) {
        log.info("Start updateRoom {}", input);


       return Try.of(() -> {
           validateInput(input);
           Room room = fetchRoomFromInput(input);

           validateUpdateRoom(input, room);

           List<Bed> roomBeds =  fetchBedsFromInput(input);

           Room updatedRoom = conversionService.convert(input, Room.class);
           updatedRoom.setBeds(roomBeds);
           updatedRoom.setBookings(room.getBookings());

           roomRepository.save(updatedRoom);

           UpdateRoomOutput output = UpdateRoomOutput.builder()
                   .roomId(updatedRoom.getId())
                   .build();

           log.info("End updateRoom {}", output);
           return output;

       }).toEither()
               .mapLeft(throwable -> Match(throwable).of(
                       validatorCase(throwable, InputValidationException.class),
                       customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                       customCase(throwable, HttpStatus.BAD_REQUEST, RoomNoAlreadyExistsException.class),
                       defaultCase(throwable)
                       ));
    }

    private List<Bed> fetchBedsFromInput(UpdateRoomInput input) {
        log.info("Start fetchBedsFromInput {}", input);

        List<BedSize> bedSizes = input.getBeds().stream()
                .map(bedSize -> BedSize.getByCode(bedSize.toString()))
                .toList();

        List<Bed> roomBeds = bedRepository.findAllByBedSizeIn(bedSizes);

        log.info("End fetchBedsFromInput {}", roomBeds);
        return roomBeds;
    }

    private Room fetchRoomFromInput(UpdateRoomInput input) {
        log.info("Start fetchRoom {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        log.info("End fetchRoom {}", room);
        return room;
    }

    private void validateUpdateRoom(UpdateRoomInput input, Room room) {
        log.info("Start validateUpdateRoom {}", input);

        Long existingRoomNoRooms = roomRepository.countAllByRoomNo(input.getRoomNo());
        if (existingRoomNoRooms > 0 &&  !room.getRoomNo().equals(input.getRoomNo())) {
            throw new RoomNoAlreadyExistsException(input.getRoomNo());
        }

        log.info("End validateUpdateRoom {}", existingRoomNoRooms);
    }
}
