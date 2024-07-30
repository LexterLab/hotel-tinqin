package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.getroom.GetRoomProcessor;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetRoomProcessorImpl implements GetRoomProcessor {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final ConversionService conversionService;

    @Override
    public GetRoomOutput process(GetRoomInput input) {
        log.info("Start getRoom {}", input);
        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("room", "id", input.getRoomId().toString()));

        List<Booking> roomBookings =  bookingRepository.findBookingsByRoomIdAndCurrentDate(room.getId());
        room.setBookings(roomBookings);

        GetRoomOutput output = conversionService.convert(room, GetRoomOutput.class);

        log.info("End getRoom {}", output);
        return output;
    }
}
