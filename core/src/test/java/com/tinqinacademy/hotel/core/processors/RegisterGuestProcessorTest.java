package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.Messages;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.errors.Error;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.registerguest.GuestInput;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.*;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterGuestProcessorTest {

    @InjectMocks
    private RegisterGuestProcessor registerGuestServiceImpl;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private Validator validator;

    @Mock
    private ConversionService conversionService;

    @Test
    void shouldRegisterGuestAndRespondWithEmptyObject() {
        UUID bookingId = UUID.fromString("7981aebc-10d9-4202-b129-53b7ec80e06e");


        GuestInput guestInput = GuestInput
                .builder()
                .firstName("Alex")
                .lastName("Peter")
                .birthday(LocalDate.now().minusYears(18))
                .idCardNo("232 3 3232 32")
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BULGARIA")
                .idCardIssueDate(LocalDate.now().minusYears(2))
                .build();

        RegisterGuestInput input = RegisterGuestInput
                .builder()
                .bookingId(String.valueOf(bookingId))
                .guests(List.of(guestInput))
                .build();

        Guest guest = Guest
                .builder()
                .idCardNo(guestInput.getIdCardNo())
                .build();

        List<Guest> guests = new ArrayList<>(List.of(guest));

        Booking booking = Booking.builder()
                .guests(guests)
                .id(bookingId)
                .build();


        when(bookingRepository.findById(UUID.fromString(input.getBookingId()))).thenReturn(Optional.of(booking));
        when(conversionService.convert(guestInput, Guest.class)).thenReturn(guest);
        when(guestRepository.findByIdCardNo(guestInput.getIdCardNo())).thenReturn(Optional.empty());

        registerGuestServiceImpl.process(input);

        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenBookingNotFoundWhenRegisteringGuestForNonExistingBooking() {
        UUID bookingId = UUID.fromString("7981aebc-10d9-4202-b129-53b7ec80e06e");

        RegisterGuestInput input = RegisterGuestInput
                .builder()
                .bookingId(String.valueOf(bookingId))
                .guests(List.of(GuestInput.builder().build()))
                .build();

        when(bookingRepository.findById(UUID.fromString(input.getBookingId()))).thenThrow(ResourceNotFoundException.class);

        ErrorOutput expectedOutput = ErrorOutput.builder()
                .errors(List.of(Error.builder().message(String
                        .format(Messages.RESOURCE_NOT_FOUND, "room", "id", bookingId))
                        .build()))
                .statusCode(HttpStatus.NOT_FOUND)
                .build();

        Either<ErrorOutput, RegisterGuestOutput> result = registerGuestServiceImpl.process(input);

        assertEquals(expectedOutput.getStatusCode().toString(), result.getLeft().getStatusCode().toString());
    }

    @Test
    void shouldThrowGuestAlreadyRegisteredExceptionWhenRegisteringAlreadyRegisteredGuest() {
        UUID bookingId = UUID.fromString("7981aebc-10d9-4202-b129-53b7ec80e06e");


        GuestInput guestInput = GuestInput
                .builder()
                .firstName("Alex")
                .lastName("Peter")
                .birthday(LocalDate.now().minusYears(18))
                .idCardNo("232 3 3232 32")
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BULGARIA")
                .idCardIssueDate(LocalDate.now().minusYears(2))
                .build();

        RegisterGuestInput input = RegisterGuestInput
                .builder()
                .bookingId(String.valueOf(bookingId))
                .guests(List.of(guestInput))
                .build();

        Guest guest = Guest
                .builder()
                .id(UUID.randomUUID())
                .idCardNo(guestInput.getIdCardNo())
                .build();

        List<Guest> guests = new ArrayList<>(List.of(guest));

        Booking booking = Booking.builder()
                .guests(guests)
                .id(bookingId)
                .build();

        ErrorOutput expectedOutput = ErrorOutput.builder()
                        .errors(List.of(Error.builder()
                                .message(String.format(Messages.GUEST_ALREADY_REGISTERED, guest.getId(), bookingId))
                                .build()))
                .statusCode(HttpStatus.BAD_REQUEST)
                                .build();


        when(bookingRepository.findById(UUID.fromString(input.getBookingId()))).thenReturn(Optional.of(booking));
        when(conversionService.convert(guestInput, Guest.class)).thenReturn(guest);
        when(guestRepository.findByIdCardNo(guestInput.getIdCardNo())).thenReturn(Optional.of(guest));


        Either<ErrorOutput, RegisterGuestOutput> result = registerGuestServiceImpl.process(input);

        assertEquals(expectedOutput.getStatusCode().toString(), result.getLeft().getStatusCode().toString());
        assertEquals(expectedOutput.getErrors().toString(), result.getLeft().getErrors().toString());
    }


}