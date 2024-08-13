package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.Messages;
import com.tinqinacademy.hotel.api.errors.Error;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNoInput;
import com.tinqinacademy.hotel.api.operations.findroombyroomno.FindRoomByRoomNoOutput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindRoomByRoomNoProcessorTest {

    @InjectMocks
    private FindRoomByRoomNoProcessor processor;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private Validator validator;


    @Test
    void shouldFindRoomByRoomNo() {
        FindRoomByRoomNoInput input = FindRoomByRoomNoInput
                .builder()
                .roomNo("201A")
                .build();

        Room room = Room
                .builder()
                .roomNo(input.getRoomNo())
                .id(UUID.randomUUID())
                .build();

        FindRoomByRoomNoOutput expectedOutput = FindRoomByRoomNoOutput
                .builder()
                .roomNo(room.getRoomNo())
                .id(room.getId())
                .build();

        when(roomRepository.findByRoomNo(input.getRoomNo())).thenReturn(Optional.of(room));


        Either<ErrorOutput,FindRoomByRoomNoOutput> output = processor.process(input);

        assertTrue(output.isRight());
        assertEquals(expectedOutput.toString(), output.get().toString());

    }

    @Test
    void shouldReturnResourceNotFoundExceptionWhenRoomNotFoundByRoomNo() {
        FindRoomByRoomNoInput input = FindRoomByRoomNoInput
                .builder()
                .roomNo("201A")
                .build();

        ErrorOutput errorOutput = ErrorOutput
                .builder()
                .errors(List.of(Error
                        .builder()
                        .build()))
                .statusCode(HttpStatus.NOT_FOUND)
                .build();

        when(roomRepository.findByRoomNo(input.getRoomNo())).thenThrow(ResourceNotFoundException.class);

        Either<ErrorOutput,FindRoomByRoomNoOutput> output = processor.process(input);

        assertTrue(output.isLeft());
        assertEquals(errorOutput.getStatusCode(), output.getLeft().getStatusCode());
    }
}