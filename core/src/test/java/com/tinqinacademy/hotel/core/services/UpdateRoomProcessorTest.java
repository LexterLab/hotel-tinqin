package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.Messages;
import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.errors.Error;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRoomProcessorTest {

    @InjectMocks
    private UpdateRoomProcessor updateRoomProcessor;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private Validator validator;

    @Mock
    private BedRepository bedRepository;

    @Mock
    private ConversionService conversionService;

    @Test
    void shouldUpdateRoom() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(String.valueOf(UUID.randomUUID()))
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        Bed bed = Bed
                .builder()
                .bedSize(com.tinqinacademy.hotel.persistence.enumerations.BedSize.KING_SIZE)
                .bedCapacity(20)
                .build();

        List<Bed> roomBeds = List.of(bed);

        List<com.tinqinacademy.hotel.persistence.enumerations.BedSize> bedSizes = input.getBeds().stream()
                .map(bedSize -> com.tinqinacademy.hotel.persistence.enumerations.BedSize.getByCode(bedSize.toString()))
                .toList();

        Room room = Room
                .builder()
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.SHARED)
                .roomNo("201A")
                .floor(4)
                .beds(List.of())
                .price(BigDecimal.valueOf(200))
                .build();

        Room updatedRoom = Room
                .builder()
                .id(UUID.fromString(input.getRoomId()))
                .roomNo(input.getRoomNo())
                .floor(input.getFloor())
                .beds(roomBeds)
                .price(input.getPrice())
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.getByCode(input.getBathroomType().toString()))
                .build();


        when(roomRepository.findById(UUID.fromString(input.getRoomId()))).thenReturn(Optional.of(room));
        when(bedRepository.findAllByBedSizeIn(bedSizes)).thenReturn(roomBeds);
        when(conversionService.convert(input, Room.class)).thenReturn(updatedRoom);
        when(roomRepository.save(updatedRoom)).thenReturn(updatedRoom);

        Either<ErrorOutput, UpdateRoomOutput> output = updateRoomProcessor.process(input);

        assertEquals(updatedRoom.getId(), output.get().getRoomId());
    }

    @Test
    void shouldThrowResourceNotFoundWhenUpdatingUnexistingRoom() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(String.valueOf(UUID.randomUUID()))
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        ErrorOutput expectedOutput = ErrorOutput.builder()
                        .statusCode(HttpStatus.NOT_FOUND)
                                .build();

        when(roomRepository.findById(UUID.fromString(input.getRoomId()))).thenThrow(ResourceNotFoundException.class);

        Either<ErrorOutput, UpdateRoomOutput> output = updateRoomProcessor.process(input);

        assertEquals(expectedOutput.getStatusCode(), output.getLeft().getStatusCode());
    }

    @Test
    void shouldThrowRoomNoAlreadyExistsExceptionWhenUpdatingRoomWithExistingRoomNo() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(String.valueOf(UUID.randomUUID()))
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        Room room = Room
                .builder()
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.SHARED)
                .roomNo("201A")
                .floor(4)
                .beds(List.of())
                .price(BigDecimal.valueOf(200))
                .build();

        ErrorOutput expectedOutput = ErrorOutput.builder()
                .errors(List.of(Error.builder()
                        .message(String.format(Messages.ROOM_NO_ALREADY_EXISTS, input.getRoomNo()))
                        .build()))
                .statusCode(HttpStatus.BAD_REQUEST)
                .build();

        when(roomRepository.findById(UUID.fromString(input.getRoomId()))).thenReturn(Optional.of(room));
        when(roomRepository.countAllByRoomNo(input.getRoomNo())).thenReturn(1L);

        Either<ErrorOutput, UpdateRoomOutput> output = updateRoomProcessor.process(input);
        assertEquals(expectedOutput.toString(), output.getLeft().toString());
    }

}