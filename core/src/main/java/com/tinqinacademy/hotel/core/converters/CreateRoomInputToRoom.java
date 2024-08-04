package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.persistence.enumerations.BathroomType;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomInputToRoom extends AbstractConverter<CreateRoomInput, Room> {
    @Override
    protected Class<Room> getTargetClass() {
        return Room.class;
    }

    @Override
    protected Room doConvert(CreateRoomInput source) {
        Room room = Room.builder()
                .roomNo(source.getRoomNo())
                .price(source.getPrice())
                .floor(source.getFloor())
                .bathroomType(BathroomType.getByCode(source.getBathroomType().toString()))
                .build();

        return room;
    }
}
