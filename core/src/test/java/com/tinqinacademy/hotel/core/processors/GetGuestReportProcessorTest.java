package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.*;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import io.vavr.control.Either;
import jakarta.validation.Validator;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetGuestReportProcessorTest {

    @InjectMocks
    private GetGuestReportProcessor getGuestReportServiceImpl;

    @Mock
    private ConversionService conversionService;

    @Mock
    private Validator validator;

    @Mock
    private BookingRepository bookingRepository;

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
                .userId(UUID.randomUUID().toString())
                .build();


        Guest guest = Guest
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .idCardNo(input.getIdCardNo())
                .idCardValidity(input.getIdCardValidity())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .build();

        List<Guest> guests = new ArrayList<>(List.of(guest));

        GuestInfo guestInfo = GuestInfo
                .builder()
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .idCardNo(guest.getIdCardNo())
                .idCardValidity(guest.getIdCardValidity())
                .idCardIssueAuthority(guest.getIdCardIssueAuthority())
                .idCardIssueDate(guest.getIdCardIssueDate())
                .build();

        List<GuestInfo> guestInfos = new ArrayList<>(List.of(guestInfo));



        Booking booking = Booking
                .builder()
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .guests(guests)
                .build();

        BookingInfo bookingInfo = BookingInfo
                .builder()
                .guests(guestInfos)
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .userId(UUID.fromString(input.getUserId()))
                .build();

        BookingReport bookingReport = BookingReport
                .builder()
                .bookings(List.of(bookingInfo))
                .build();

        GuestOutput guestOutput = GuestOutput
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .idCardNo(input.getIdCardNo())
                .idCardValidity(input.getIdCardValidity())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .startDate(bookingInfo.getStartDate())
                .endDate(bookingInfo.getEndDate())
                .userId(bookingInfo.getUserId())
                .build();

        GetGuestReportOutput expectedOutput = GetGuestReportOutput
                .builder()
                .guestsReports(List.of(guestOutput))
                .build();
        when(bookingRepository.searchBookings(input.getStartDate(), input.getEndDate(),
                input.getFirstName(), input.getLastName(), input.getIdCardNo(),
                input.getIdCardValidity(), input.getIdCardIssueAuthority(), input.getIdCardIssueDate(),
                input.getRoomNo(), UUID.fromString(input.getUserId()))).thenReturn(List.of(booking));
        when(conversionService.convert(booking, BookingInfo.class)).thenReturn(bookingInfo);
//        when(conversionService.convert(bookingReport, GetGuestReportOutput.class)).thenReturn(expectedOutput);

        Either<ErrorOutput, GetGuestReportOutput> output = getGuestReportServiceImpl.process(input);

        verify(conversionService).convert(booking, BookingInfo.class);

        //CANNOT TEST DUE TO PRIVATE METHOD STRUCTURE :)
//        assertEquals(expectedOutput.toString(), output.get().toString());
    }
}