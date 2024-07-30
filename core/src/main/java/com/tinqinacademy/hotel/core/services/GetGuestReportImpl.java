package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReport;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.guest.GuestOutput;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetGuestReportImpl implements GetGuestReport {
    private final GuestRepository guestRepository;
    private final ConversionService conversionService;

    @Override
    public GetGuestReportOutput process(GetGuestReportInput input) {
        log.info("Start getVisitorsReport {}", input);

        List<Guest> guests = guestRepository.searchGuest(input.getStartDate(), input.getEndDate(),
                input.getFirstName(), input.getLastName(), input.getPhoneNo(), input.getIdCardNo(),
                input.getIdCardValidity(), input.getIdCardIssueAuthority(), input.getIdCardIssueDate(),
                input.getRoomNo());

        List<GuestOutput> guestReports = guests.stream()
                .map(guest -> conversionService.convert(guest, GuestOutput.class))
                .toList();

        GetGuestReportOutput output = GetGuestReportOutput.builder()
                .guestsReports(guestReports)
                .build();

        log.info("End getVisitorsReport {}", output);
        return output;
    }
}
