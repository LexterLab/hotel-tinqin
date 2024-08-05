package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialRoomUpdate;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomToPartialRoomUpdate extends AbstractConverter<Room, PartialRoomUpdate> {
    @Override
    protected Class<PartialRoomUpdate> getTargetClass() {
        return PartialRoomUpdate.class;
    }

    @Override
    protected PartialRoomUpdate doConvert(Room source) {
        PartialRoomUpdate target = PartialRoomUpdate
                .builder()
                .roomNo(source.getRoomNo())
                .price(source.getPrice())
                .floor(source.getFloor())
                .bathroomType(BathroomType.getByCode(source.getBathroomType().toString()))
                .build();
        return target;
    }
}
