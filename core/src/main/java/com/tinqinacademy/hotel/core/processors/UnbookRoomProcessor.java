package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoom;
import com.tinqinacademy.hotel.api.exceptions.AlreadyFinishedVisitException;
import com.tinqinacademy.hotel.api.exceptions.AlreadyStartedVisitException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class UnbookRoomProcessor extends BaseProcessor implements UnbookRoom {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public UnbookRoomProcessor(ConversionService conversionService, Validator validator, BookingRepository bookingRepository, RoomRepository roomRepository) {
        super(conversionService, validator);
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public Either<ErrorOutput, UnbookRoomOutput> process(UnbookRoomInput input) {
        log.info("Start unbookRoom {}", input);

       return Try.of(() -> {
            validateInput(input);

            Booking booking = findBooking(input);

            checkIfUnbookingIsPossible(booking);

            Room room = booking.getRoom();
            room.getBookings().remove(booking);
            roomRepository.save(room);

            UnbookRoomOutput output = UnbookRoomOutput.builder().build();
            log.info("End unbookRoom {}", output);
            return output;
        }).toEither()
               .mapLeft(throwable ->  Match(throwable).of(
                       validatorCase(throwable),
                       customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                       customCase(throwable, HttpStatus.CONFLICT, AlreadyFinishedVisitException.class),
                       customCase(throwable, HttpStatus.CONFLICT, AlreadyStartedVisitException.class),
                       defaultCase(throwable)
               ));

    }


    private Booking findBooking(UnbookRoomInput input) {
        log.info("Start findBooking {}", input);

        Booking booking = bookingRepository.findBookingByIdAndUserId(UUID.fromString(input.getBookingId()),
                        UUID.fromString(input.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("booking", "bookingId & userId",
                        input.getBookingId() + " - " + input.getUserId()));

        log.info("End findBooking {}", booking);
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
