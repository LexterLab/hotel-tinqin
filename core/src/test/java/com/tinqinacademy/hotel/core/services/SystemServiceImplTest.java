package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.exceptions.GuestAlreadyRegisteredException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.guest.GuestInput;
import com.tinqinacademy.hotel.api.operations.guest.GuestOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemServiceImplTest {

    @InjectMocks
    private SystemServiceImpl systemService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BedRepository bedRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private GuestRepository guestRepository;

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
                .bookingId(bookingId)
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


        when(bookingRepository.findById(input.getBookingId())).thenReturn(Optional.of(booking));
        when(conversionService.convert(guestInput, Guest.class)).thenReturn(guest);
        when(guestRepository.findByIdCardNo(guestInput.getIdCardNo())).thenReturn(Optional.empty());

        systemService.registerGuest(input);

        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenBookingNotFoundWhenRegisteringGuestForNonExistingBooking() {
        UUID bookingId = UUID.fromString("7981aebc-10d9-4202-b129-53b7ec80e06e");

        RegisterGuestInput input = RegisterGuestInput
                .builder()
                .bookingId(bookingId)
                .build();

        when(bookingRepository.findById(input.getBookingId())).thenThrow(ResourceNotFoundException.class);


       assertThrows(ResourceNotFoundException.class, () -> systemService.registerGuest(input));
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
                .bookingId(bookingId)
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


        when(bookingRepository.findById(input.getBookingId())).thenReturn(Optional.of(booking));
        when(conversionService.convert(guestInput, Guest.class)).thenReturn(guest);
        when(guestRepository.findByIdCardNo(guestInput.getIdCardNo())).thenReturn(Optional.of(guest));

        assertThrows(GuestAlreadyRegisteredException.class, () -> systemService.registerGuest(input));
    }


    @Test
    void shouldReturnGuestReports() {
        GetGuestReportInput input = GetGuestReportInput
                .builder()
                .idCardNo("232 3 3232 32")
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BULGARIA")
                .idCardIssueDate(LocalDate.now().minusYears(2))
                .firstName("Alex")
                .lastName("Peter")
                .startDate(LocalDateTime.now().plusYears(1))
                .endDate(LocalDateTime.now().plusYears(1))
                .phoneNo("+3598535353453")
                .build();


        Guest guest  = Guest
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .idCardNo(input.getIdCardNo())
                .idCardValidity(input.getIdCardValidity())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .build();

        List<Guest> guests = new ArrayList<>(List.of(guest));

        GuestOutput guestOutput = GuestOutput
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .idCardNo(input.getIdCardNo())
                .idCardValidity(input.getIdCardValidity())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .build();

        GetGuestReportOutput expectedOutput = GetGuestReportOutput
                .builder()
                .guestsReports(List.of(guestOutput))
                .build();

        when(guestRepository.searchGuest(input.getStartDate(), input.getEndDate(),
                input.getFirstName(), input.getLastName(), input.getPhoneNo(), input.getIdCardNo(),
                input.getIdCardValidity(), input.getIdCardIssueAuthority(), input.getIdCardIssueDate(),
                input.getRoomNo())).thenReturn(guests);
        when(conversionService.convert(guest, GuestOutput.class)).thenReturn(guestOutput);

        GetGuestReportOutput output = systemService.getGuestReport(input);

        assertEquals(expectedOutput.toString(), output.toString());
    }

}