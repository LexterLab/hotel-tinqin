package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRoomProcessorTest {

    @InjectMocks
    private DeleteRoomProcessor deleteRoomServiceImpl;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private Validator validator;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void shouldProcess() {
        DeleteRoomInput input = DeleteRoomInput
                .builder()
                .roomId(String.valueOf(UUID.randomUUID()))
                .build();

        Room room = Room
                .builder()
                .id(UUID.fromString(input.getRoomId()))
                .build();

        when(roomRepository.findById(UUID.fromString(input.getRoomId()))).thenReturn(Optional.of(room));
        doNothing().when(bookingRepository).deleteAll(room.getBookings());
        doNothing().when(roomRepository).delete(room);

        deleteRoomServiceImpl.process(input);

        verify(roomRepository).delete(room);
    }
}