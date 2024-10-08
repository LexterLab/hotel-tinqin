package com.tinqinacademy.hotel.core.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoom;
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
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class PartialUpdateRoomProcessor extends BaseProcessor implements PartialUpdateRoom {
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;
    private final BedRepository bedRepository;

    public PartialUpdateRoomProcessor(ConversionService conversionService, Validator validator, RoomRepository roomRepository, ObjectMapper objectMapper, BedRepository bedRepository) {
        super(conversionService, validator);
        this.roomRepository = roomRepository;
        this.objectMapper = objectMapper;
        this.bedRepository = bedRepository;
    }


    @Override
    public Either<ErrorOutput, PartialUpdateRoomOutput> process(PartialUpdateRoomInput input) {
        log.info("Start partialUpdateRoom {}", input);

        return Try.of(() -> {
            Room room = fetchRoomFromInput(input);

            validateInput(input);

            validatePartialUpdate(input, room);

            Room patchedRoom = applyPartialUpdate(input, room);
            patchedRoom.setId(room.getId());

            updateRoomBeds(input, patchedRoom, room);

            roomRepository.save(patchedRoom);

            PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                    .roomId(UUID.fromString(input.getRoomId()))
                    .build();

            log.info("End partialUpdateRoom {}", output);
            return output;
        }).toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validatorCase(throwable),
                        customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                        customCase(throwable, HttpStatus.BAD_REQUEST, RoomNoAlreadyExistsException.class),
                        customCase(throwable, HttpStatus.INTERNAL_SERVER_ERROR, JsonPatchException.class),
                        customCase(throwable, HttpStatus.INTERNAL_SERVER_ERROR, JsonProcessingException.class),
                        defaultCase(throwable)
                ));
    }

    private Room fetchRoomFromInput(PartialUpdateRoomInput input) {
        log.info("Start fetchRoomFromInput {}", input);

        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId()));

        log.info("End fetchRoomFromInput {}", room);
        return room;
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


    private void updateRoomBeds(PartialUpdateRoomInput input, Room patchedRoom, Room room) {
        log.info("Start updateRoomBeds {}", input);
        patchedRoom.setBeds(room.getBeds());

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
        PartialRoomUpdate roomData = conversionService.convert(room, PartialRoomUpdate.class);

        JsonNode roomNode = objectMapper.convertValue(roomData, JsonNode.class);
   
        PartialRoomUpdate partialRoomUpdate = conversionService.convert(input, PartialRoomUpdate.class);
        JsonNode inputNode = objectMapper.convertValue(partialRoomUpdate, JsonNode.class);

        JsonMergePatch mergePatch = JsonMergePatch.fromJson(inputNode);

        JsonNode patchedRoomNode = mergePatch.apply(roomNode);

        Room patchedRoom = objectMapper.treeToValue(patchedRoomNode, Room.class);
        log.info("End applyPartialUpdate {}", patchedRoom);
        return patchedRoom;
    }
}
