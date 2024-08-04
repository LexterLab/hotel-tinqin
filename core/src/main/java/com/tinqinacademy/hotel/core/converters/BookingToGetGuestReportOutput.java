package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.getguestreport.BookingReport;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GuestOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingToGetGuestReportOutput extends AbstractConverter<BookingReport, GetGuestReportOutput> {
    @Override
    protected Class<GetGuestReportOutput> getTargetClass() {
        return GetGuestReportOutput.class;
    }

    @Override
    protected GetGuestReportOutput doConvert(BookingReport source) {
        List<GuestOutput> guestOutputs = source.getBookings().stream()
                .flatMap(bookingInfo -> bookingInfo.getGuests().stream()
                .map(guestInfo -> GuestOutput.builder()
                        .startDate(bookingInfo.getStartDate())
                        .endDate(bookingInfo.getEndDate())
                        .phoneNo(bookingInfo.getPhoneNo())
                        .firstName(guestInfo.getFirstName())
                        .lastName(guestInfo.getLastName())
                        .idCardNo(guestInfo.getIdCardNo())
                        .idCardValidity(guestInfo.getIdCardValidity())
                        .idCardIssueAuthority(guestInfo.getIdCardIssueAuthority())
                        .idCardIssueDate(guestInfo.getIdCardIssueDate())
                        .build()))
                .toList();

        return GetGuestReportOutput.builder()
                .guestsReports(guestOutputs)
                .build();
    }
}
