package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuest;
import com.tinqinacademy.hotel.api.exceptions.GuestAlreadyRegisteredException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class RegisterGuestProcessor extends BaseProcessor implements RegisterGuest {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;

    public RegisterGuestProcessor(ConversionService conversionService, Validator validator, BookingRepository bookingRepository, GuestRepository guestRepository) {
        super(conversionService, validator);
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
    }


    @Override
    @Transactional
    public Either<ErrorOutput, RegisterGuestOutput> process(RegisterGuestInput input) {
        log.info("Start registerVisitor {}", input);

       return Try.of(() -> {
            validateInput(input);
            Booking booking = fetchBookingFromInput(input);

            addGuestsToBooking(input, booking);

            bookingRepository.save(booking);

            RegisterGuestOutput output = RegisterGuestOutput.builder().build();
            log.info("End registerVisitor {}", output);
            return output;
        }).toEither()
               .mapLeft(throwable -> Match(throwable).of(
                       validatorCase(throwable),
                       customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                       customCase(throwable, HttpStatus.BAD_REQUEST, GuestAlreadyRegisteredException.class),
                       defaultCase(throwable)
               ));

    }

    private Booking fetchBookingFromInput(RegisterGuestInput input) {
        log.info("Start fetchBooking {}", input);

        Booking booking = bookingRepository.findById(UUID.fromString(input.getBookingId()))
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", input.getBookingId()));

        log.info("End fetchBooking {}", booking);
        return booking;
    }


    private void addGuestsToBooking(RegisterGuestInput input, Booking booking) {
        log.info("Start addGuestsToBooking {}", input);
        List<Guest> guests = input.getGuests()
                .stream()
                .map(guestInput -> conversionService.convert(guestInput, Guest.class))
                .toList();

        for (Guest guest : guests) {
            Optional<Guest> existingGuest = guestRepository.findByIdCardNo(guest.getIdCardNo());
            if (existingGuest.isPresent()) {
                if (booking.getGuests().contains(existingGuest.get())) {
                    throw new GuestAlreadyRegisteredException(existingGuest.get().getId(),
                            UUID.fromString(input.getBookingId()));
                }
                booking.getGuests().add(existingGuest.get());
            } else {
                booking.getGuests().add(guest);
            }
        }

        log.info("End addGuestsToBooking {}", guests);
    }
}
