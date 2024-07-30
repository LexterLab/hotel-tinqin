package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomProcessor;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteRoomProcessorImpl implements DeleteRoomProcessor {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Override
    public DeleteRoomOutput process(DeleteRoomInput input) {
        log.info("Start deleteRoom {}", input);
        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        bookingRepository.deleteAll(room.getBookings());

        roomRepository.delete(room);
        DeleteRoomOutput output = DeleteRoomOutput.builder().build();
        log.info("End deleteRoom {}", output);
        return output;
    }
}
