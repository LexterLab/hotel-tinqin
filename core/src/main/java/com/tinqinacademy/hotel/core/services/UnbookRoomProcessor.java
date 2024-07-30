package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.errors.Error;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoom;
import com.tinqinacademy.hotel.api.exceptions.AlreadyFinishedVisitException;
import com.tinqinacademy.hotel.api.exceptions.AlreadyStartedVisitException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnbookRoomProcessor implements UnbookRoom {
    private final BookingRepository bookingRepository;

    @Override
    public Either<ErrorOutput, UnbookRoomOutput> process(UnbookRoomInput input) {
        log.info("Start unbookRoom {}", input);

       return Try.of(() -> {

            Booking booking = findLatestBooking(input);

            checkIfUnbookingIsPossible(booking);

            bookingRepository.delete(booking);

            UnbookRoomOutput output = UnbookRoomOutput.builder().build();
            log.info("End unbookRoom {}", output);
            return output;
        }).toEither()
               .mapLeft(throwable ->  Match(throwable).of(
                       Case($(instanceOf(ResourceNotFoundException.class)), () -> ErrorOutput.builder()
                               .errors(List.of(Error.builder()
                                       .message(throwable.getMessage())
                                       .build()))
                               .build()
                       ),
                       Case($(instanceOf(AlreadyFinishedVisitException.class)), () -> ErrorOutput.builder()
                               .errors(List.of(Error.builder()
                                       .message(throwable.getMessage())
                                       .build()))
                               .build()
                       ),
                       Case($(instanceOf(AlreadyStartedVisitException.class)), () -> ErrorOutput.builder()
                               .errors(List.of(Error.builder()
                                       .message(throwable.getMessage())
                                       .build()))
                               .build()
                       ),
                       Case($(), () -> ErrorOutput.builder()
                               .errors(List.of(Error.builder()
                                       .message(throwable.getMessage())
                                       .build()))
                               .build())
               ));

    }


    private Booking findLatestBooking(UnbookRoomInput input) {
        log.info("Start findLatestBooking {}", input);

        Booking booking = bookingRepository.findLatestByRoomIdAndUserId(input.getRoomId(), input.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("booking", "roomId & userId",
                        input.getRoomId().toString()));

        log.info("End findLatestBooking {}", booking);
        return booking;
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
