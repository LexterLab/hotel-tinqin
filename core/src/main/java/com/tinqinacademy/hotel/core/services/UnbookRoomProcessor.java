package com.tinqinacademy.hotel.core.services;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

import static io.vavr.API.*;

@Service
@Slf4j
public class UnbookRoomProcessor extends BaseProcessor implements UnbookRoom {
    private final BookingRepository bookingRepository;

    public UnbookRoomProcessor(ConversionService conversionService, Validator validator, BookingRepository bookingRepository) {
        super(conversionService, validator);
        this.bookingRepository = bookingRepository;
    }

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
                       customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                       customCase(throwable, HttpStatus.CONFLICT, AlreadyFinishedVisitException.class),
                       customCase(throwable, HttpStatus.CONFLICT, AlreadyStartedVisitException.class),
                       defaultCase(throwable)
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
