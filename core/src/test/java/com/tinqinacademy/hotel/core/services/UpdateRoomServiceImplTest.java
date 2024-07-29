package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRoomServiceImplTest {

    @InjectMocks
    private UpdateRoomServiceImpl updateRoomServiceImpl;

    @Mock
    private RoomRepository roomRepository;


    @Mock
    private BedRepository bedRepository;

    @Mock
    private ConversionService conversionService;

    @Test
    void shouldUpdateRoom() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(UUID.randomUUID())
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
                .id(input.getRoomId())
                .roomNo(input.getRoomNo())
                .floor(input.getFloor())
                .beds(roomBeds)
                .price(input.getPrice())
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.getByCode(input.getBathroomType().toString()))
                .build();


        when(roomRepository.findById(input.getRoomId())).thenReturn(Optional.of(room));
        when(bedRepository.findAllByBedSizeIn(bedSizes)).thenReturn(roomBeds);
        when(conversionService.convert(input, Room.class)).thenReturn(updatedRoom);
        when(roomRepository.save(updatedRoom)).thenReturn(updatedRoom);

        UpdateRoomOutput output = updateRoomServiceImpl.updateRoom(input);

        assertEquals(updatedRoom.getId(), output.getRoomId());
    }

    @Test
    void shouldThrowResourceNotFoundWhenUpdatingUnexistingRoom() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(UUID.randomUUID())
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        when(roomRepository.findById(input.getRoomId())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> updateRoomServiceImpl.updateRoom(input));
    }

    @Test
    void shouldThrowRoomNoAlreadyExistsExceptionWhenUpdatingRoomWithExistingRoomNo() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(UUID.randomUUID())
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

        when(roomRepository.findById(input.getRoomId())).thenReturn(Optional.of(room));
        when(roomRepository.countAllByRoomNo(input.getRoomNo())).thenReturn(1L);

        assertThrows(RoomNoAlreadyExistsException.class, () -> updateRoomServiceImpl.updateRoom(input));
    }

}