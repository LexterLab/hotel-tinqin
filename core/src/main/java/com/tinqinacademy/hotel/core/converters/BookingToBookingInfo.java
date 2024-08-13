package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.getguestreport.BookingInfo;
import com.tinqinacademy.hotel.api.operations.getguestreport.GuestInfo;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingToBookingInfo  extends AbstractConverter<Booking, BookingInfo> {
    @Override
    protected Class<BookingInfo> getTargetClass() {
        return BookingInfo.class;
    }

    @Override
    protected BookingInfo doConvert(Booking source) {
        BookingInfo bookingInfo = BookingInfo
                .builder()
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .guests(convertGuests(source))
                .build();

        return bookingInfo;
    }

    private List<GuestInfo> convertGuests(Booking booking) {
        List<GuestInfo> guests = new ArrayList<>();
        booking.getGuests().forEach(guest -> guests.add(GuestInfo
                .builder()
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .idCardIssueAuthority(guest.getIdCardIssueAuthority())
                .idCardIssueDate(guest.getIdCardIssueDate())
                .idCardNo(guest.getIdCardNo())
                .idCardValidity(guest.getIdCardValidity())
                .build()));

        return guests;
    }
}
