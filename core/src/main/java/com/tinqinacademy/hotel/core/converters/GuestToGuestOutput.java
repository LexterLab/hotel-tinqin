package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.getguestreport.GuestOutput;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import org.springframework.stereotype.Component;

@Component
public class GuestToGuestOutput extends AbstractConverter<Guest, GuestOutput> {
    @Override
    protected Class<GuestOutput> getTargetClass() {
        return GuestOutput.class;
    }

    @Override
    protected GuestOutput doConvert(Guest source) {
        GuestOutput guestOutput = GuestOutput
                .builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .idCardNo(source.getIdCardNo())
                .idCardValidity(source.getIdCardValidity())
                .idCardIssueAuthority(source.getIdCardIssueAuthority())
                .idCardIssueDate(source.getIdCardIssueDate())
                .build();

        return guestOutput;
    }
}
