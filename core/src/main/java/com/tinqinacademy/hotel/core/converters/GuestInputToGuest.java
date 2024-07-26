package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.guest.GuestInput;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import org.springframework.stereotype.Component;

@Component
public class GuestInputToGuest extends AbstractConverter<GuestInput, Guest> {
    @Override
    protected Class<Guest> getTargetClass() {
        return Guest.class;
    }

    @Override
    protected Guest doConvert(GuestInput source) {
        Guest guest = Guest
                .builder()
                .birthday(source.getBirthday())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .idCardIssueAuthority(source.getIdCardIssueAuthority())
                .idCardIssueDate(source.getIdCardIssueDate())
                .idCardValidity(source.getIdCardValidity())
                .idCardNo(source.getIdCardNo())
                .build();

        return guest;
    }
}
