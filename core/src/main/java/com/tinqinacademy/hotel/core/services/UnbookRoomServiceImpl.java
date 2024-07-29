package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.contracts.UnbookRoomService;
import com.tinqinacademy.hotel.api.exceptions.AlreadyFinishedVisitException;
import com.tinqinacademy.hotel.api.exceptions.AlreadyStartedVisitException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnbookRoomServiceImpl implements UnbookRoomService {
    private final BookingRepository bookingRepository;

    @Override
    public UnbookRoomOutput unbookRoom(UnbookRoomInput input) {
        log.info("Start unbookRoom {}", input);

        Booking booking = bookingRepository.findLatestByRoomIdAndUserId(input.getRoomId(), input.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("booking", "roomId & userId",
                        input.getRoomId().toString()));

        checkIfUnbookingIsPossible(booking);

        bookingRepository.delete(booking);

        UnbookRoomOutput output = UnbookRoomOutput.builder().build();
        log.info("End unbookRoom {}", output);
        return output;
    }

    private void checkIfUnbookingIsPossible(Booking booking) {
        log.info("Start checkIfUnbookingIsPossible {}", booking.getId());
        if (LocalDateTime.now().isAfter(booking.getEndDate())) {
            throw new AlreadyFinishedVisitException();
        } else if (LocalDateTime.now().isAfter(booking.getStartDate())) {
            throw new AlreadyStartedVisitException();
        }
        log.info("End checkIfUnbookingIsPossible {}", booking.getId());
    }
}
