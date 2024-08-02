package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.guest.GuestOutput;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGuestReportProcessorTest {

    @InjectMocks
    private GetGuestReportProcessor getGuestReportServiceImpl;

    @Mock
    private ConversionService conversionService;

    @Mock
    private Validator validator;

    @Mock
    private GuestRepository guestRepository;

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

        Either<ErrorOutput, GetGuestReportOutput> output = getGuestReportServiceImpl.process(input);

        assertEquals(expectedOutput.toString(), output.get().toString());
    }
}