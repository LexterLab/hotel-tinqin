package com.tinqinacademy.hotel.core.mappers;

import com.tinqinacademy.hotel.api.operations.visitor.GuestInput;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GuestMapper {
    GuestMapper INSTANCE = Mappers.getMapper(GuestMapper.class);

    Guest guestInputToGuest(GuestInput guestInput);
    List<Guest> guestInputToGuestList(List<GuestInput> guestInputList);

}
