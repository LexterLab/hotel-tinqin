package com.tinqinacademy.hotel.core.mappers;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    Room createRoomInputToRoom(CreateRoomInput input);


    void partialUpdateRoom(@MappingTarget Room room, PartialUpdateRoomInput input);
    void updateRoom(@MappingTarget Room room, UpdateRoomInput input);

}
