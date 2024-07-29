package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRoomServiceImplTest {

    @InjectMocks
    private CreateRoomServiceImpl createRoomServiceImpl;

    @Mock
    private RoomRepository roomRepository;


    @Mock
    private BedRepository bedRepository;


    @Mock
    private ConversionService conversionService;


    @Test
    void shouldCreateRoom() {
        CreateRoomInput input = CreateRoomInput
                .builder()
                .roomNo("201A")
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .price(BigDecimal.valueOf(200))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        List<com.tinqinacademy.hotel.persistence.enumerations.BedSize> bedSizes = input.beds().stream()
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
                .price(input.price())
                .floor(input.floor())
                .roomNo(input.roomNo())
                .id(UUID.randomUUID())
                .build();

        when(roomRepository.countAllByRoomNo(input.roomNo())).thenReturn(0L);
        when(bedRepository.findAllByBedSizeIn(bedSizes)).thenReturn(roomBeds);
        when(conversionService.convert(input, Room.class)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);

        CreateRoomOutput output = createRoomServiceImpl.createRoom(input);

        assertEquals(room.getId().toString(), output.getRoomId());
    }

    @Test
    void shouldThrowRoomNoAlreadyExistsExceptionWhenCreatingRoomWithExistingRoomNo() {
        CreateRoomInput input = CreateRoomInput
                .builder()
                .roomNo("201A")
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .price(BigDecimal.valueOf(200))

                .bathroomType(BathroomType.PRIVATE)
                .build();



        when(roomRepository.countAllByRoomNo(input.roomNo())).thenReturn(1L);


        assertThrows(RoomNoAlreadyExistsException.class, () -> createRoomServiceImpl.createRoom(input));
    }
}