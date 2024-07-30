package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRoomImplTest {

    @InjectMocks
    private DeleteRoomImpl deleteRoomServiceImpl;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void shouldProcess() {
        DeleteRoomInput input = DeleteRoomInput
                .builder()
                .roomId(UUID.randomUUID())
                .build();

        Room room = Room
                .builder()
                .id(input.getRoomId())
                .build();

        when(roomRepository.findById(input.getRoomId())).thenReturn(Optional.of(room));
        doNothing().when(bookingRepository).deleteAll(room.getBookings());
        doNothing().when(roomRepository).delete(room);

        deleteRoomServiceImpl.process(input);

        verify(roomRepository).delete(room);
    }
}