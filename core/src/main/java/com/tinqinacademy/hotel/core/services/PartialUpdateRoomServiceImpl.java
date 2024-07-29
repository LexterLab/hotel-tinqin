package com.tinqinacademy.hotel.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.contracts.PartialUpdateRoomService;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialRoomUpdate;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
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
public class PartialUpdateRoomServiceImpl implements PartialUpdateRoomService {
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;
    private final BedRepository bedRepository;
    private final ConversionService conversionService;

    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) throws JsonPatchException, JsonProcessingException {
        log.info("Start partialUpdateRoom {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        validatePartialUpdate(input, room);

        Room patchedRoom = applyPartialUpdate(input, room);
        patchedRoom.setId(room.getId());

        updateRoomBeds(input, patchedRoom);

        roomRepository.save(patchedRoom);

        PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                .roomId(input.getRoomId())
                .build();

        log.info("End partialUpdateRoom {}", output);
        return output;
    }

    private void validatePartialUpdate(PartialUpdateRoomInput input, Room room) {
        log.info("Start validatePartialUpdate {}", input);
        Long existingRoomNoCount = roomRepository.countAllByRoomNo(input.getRoomNo());

        if (input.getRoomNo() != null) {
            if ( existingRoomNoCount> 0 && !room.getRoomNo().equals(input.getRoomNo())) {
                throw new RoomNoAlreadyExistsException(input.getRoomNo());
            }
        }

        log.info("End validatePartialUpdate {}", input);
    }

    private void updateRoomBeds(PartialUpdateRoomInput input, Room patchedRoom) {
        log.info("Start updateRoomBeds {}", input);
        if (input.getBeds() != null) {
            List<BedSize> bedSizes = input.getBeds().stream()
                    .map(bedSize -> BedSize.getByCode(bedSize.toString()))
                    .toList();

            List<Bed> roomBeds = bedRepository.findAllByBedSizeIn(bedSizes);
            patchedRoom.setBeds(roomBeds);
        }
        log.info("End updateRoomBeds {}", patchedRoom);
    }

    private Room applyPartialUpdate(PartialUpdateRoomInput input, Room room) throws JsonPatchException, JsonProcessingException {
        log.info("Start applyPartialUpdate {}", input);
        JsonNode roomNode = objectMapper.convertValue(room, JsonNode.class);

        PartialRoomUpdate partialRoomUpdate = conversionService.convert(input, PartialRoomUpdate.class);
        JsonNode inputNode = objectMapper.convertValue(partialRoomUpdate, JsonNode.class);

        JsonMergePatch mergePatch = JsonMergePatch.fromJson(inputNode);

        JsonNode patchedRoomNode = mergePatch.apply(roomNode);

        Room patchedRoom = objectMapper.treeToValue(patchedRoomNode, Room.class);
        log.info("End applyPartialUpdate {}", patchedRoom);
        return patchedRoom;
    }
}
