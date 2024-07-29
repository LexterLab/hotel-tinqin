package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.contracts.CreateRoomService;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.enumerations.BedSize;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateRoomServiceImpl implements CreateRoomService {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final ConversionService conversionService;

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Start createRoom {}", input);

        validateCreateRoom(input);

        List<BedSize> bedSizes = input.beds().stream()
                .map(bedSize -> BedSize.getByCode(bedSize.toString()))
                .toList();

        List<Bed> roomBeds = bedRepository.findAllByBedSizeIn(bedSizes);

        Room room = conversionService.convert(input, Room.class);
        room.setBeds(roomBeds);

        roomRepository.save(room);

        CreateRoomOutput output = CreateRoomOutput.builder()
                .roomId(room.getId().toString())
                .build();

        log.info("End createRoom {}", output);
        return output;
    }

    private void validateCreateRoom(CreateRoomInput input) {
        log.info("Start validateCreateRoom {}", input);

        Long existingRoomNoRooms = roomRepository.countAllByRoomNo(input.roomNo());

        if (existingRoomNoRooms > 0) {
            throw new RoomNoAlreadyExistsException(input.roomNo());
        }

        log.info("End validateCreateRoom {}", existingRoomNoRooms);
    }
}
