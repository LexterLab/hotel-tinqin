package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.Messages;
import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.errors.Error;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRoomProcessorTest {

    @InjectMocks
    private CreateRoomProcessor createRoomServiceImpl;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private Validator validator;

    @Mock
    private BedRepository bedRepository;


    @Mock
    private ConversionService conversionService;


    @Test
    void shouldProcess() {
        CreateRoomInput input = CreateRoomInput
                .builder()
                .roomNo("201A")
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .price(BigDecimal.valueOf(200))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        List<com.tinqinacademy.hotel.persistence.enumerations.BedSize> bedSizes = input.getBeds().stream()
                .map(bedSize -> com.tinqinacademy.hotel.persistence.enumerations.BedSize
                        .getByCode(bedSize.toString()))
                .toList();

        Bed bed = Bed
                .builder()
                .bedCapacity(2)
                .bedSize(com.tinqinacademy.hotel.persistence.enumerations.BedSize.KING_SIZE)
                .build();

        List<Bed> roomBeds = List.of(bed);

        Room room = Room.builder()
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.PRIVATE)
                .beds(roomBeds)
                .price(input.getPrice())
                .floor(input.getFloor())
                .roomNo(input.getRoomNo())
                .id(UUID.randomUUID())
                .build();

        when(roomRepository.countAllByRoomNo(input.getRoomNo())).thenReturn(0L);
        when(bedRepository.findAllByBedSizeIn(bedSizes)).thenReturn(roomBeds);
        when(conversionService.convert(input, Room.class)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);

        Either<ErrorOutput, CreateRoomOutput> output = createRoomServiceImpl.process(input);

        assertEquals(room.getId().toString(), output.get().getRoomId());
    }

    @Test
    void shouldReturnRoomNoAlreadyExistsExceptionWhenCreatingRoomWithExistingRoomNo() {
        CreateRoomInput input = CreateRoomInput
                .builder()
                .roomNo("201A")
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .price(BigDecimal.valueOf(200))

                .bathroomType(BathroomType.PRIVATE)
                .build();



        when(roomRepository.countAllByRoomNo(input.getRoomNo())).thenReturn(1L);

        Either<ErrorOutput, CreateRoomOutput> output = createRoomServiceImpl.process(input);

        ErrorOutput expectedOutput = ErrorOutput.builder()
                        .errors(List.of(Error.builder()
                                        .message(String.format(Messages.ROOM_NO_ALREADY_EXISTS, input.getRoomNo()))
                                .build()))
                                .build();

        assertNotNull(output.getLeft());
        assertEquals(expectedOutput.getErrors().toString(), output.getLeft().getErrors().toString());
    }

}