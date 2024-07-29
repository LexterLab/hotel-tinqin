package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.contracts.UpdateRoomService;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
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
public class UpdateRoomServiceImpl implements UpdateRoomService {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final ConversionService conversionService;

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Start updateRoom {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        validateUpdateRoom(input, room);

        List<BedSize> bedSizes = input.getBeds().stream()
                .map(bedSize -> BedSize.getByCode(bedSize.toString()))
                .toList();

        List<Bed> roomBeds = bedRepository.findAllByBedSizeIn(bedSizes);

        Room updatedRoom = conversionService.convert(input, Room.class);
        updatedRoom.setBeds(roomBeds);
        updatedRoom.setBookings(room.getBookings());

        roomRepository.save(updatedRoom);

        UpdateRoomOutput output = UpdateRoomOutput.builder()
                .roomId(updatedRoom.getId())
                .build();

        log.info("End updateRoom {}", output);
        return output;
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
