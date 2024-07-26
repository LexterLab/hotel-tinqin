package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomInputToRoom extends AbstractConverter<UpdateRoomInput, Room> {
    @Override
    protected Class<Room> getTargetClass() {
        return Room.class;
    }


    @Override
    protected Room doConvert(UpdateRoomInput source) {
        Room room = Room.builder()
                .id(source.getRoomId())
                .floor(source.getFloor())
                .price(source.getPrice())
                .bathroomType(source.getBathroomType())
                .roomNo(source.getRoomNo())
                .build();

        return room;
    }
}
