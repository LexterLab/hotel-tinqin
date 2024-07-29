package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.contracts.RegisterGuestService;
import com.tinqinacademy.hotel.api.exceptions.GuestAlreadyRegisteredException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterGuestServiceImpl implements RegisterGuestService {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final ConversionService conversionService;

    @Override
    @Transactional
    public RegisterGuestOutput registerGuest(RegisterGuestInput input) {
        log.info("Start registerVisitor {}", input);

        Booking booking = bookingRepository.findById(input.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", input.getBookingId().toString()));

        addGuestsToBooking(input, booking);

        bookingRepository.save(booking);

        RegisterGuestOutput output = RegisterGuestOutput.builder().build();
        log.info("End registerVisitor {}", output);
        return output;
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
                    throw new GuestAlreadyRegisteredException(existingGuest.get().getId(), input.getBookingId());
                }
                booking.getGuests().add(existingGuest.get());
            } else {
                booking.getGuests().add(guest);
            }
        }

        log.info("End addGuestsToBooking {}", guests);
    }
}
