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

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    Room createRoomInputToRoom(CreateRoomInput input);

    @Mapping(target = "id", source = "roomId")
    Room updateRoomInputToRoom(UpdateRoomInput input);

    void partialUpdateRoom(@MappingTarget Room room, PartialUpdateRoomInput input);

    PartialUpdateRoomInput roomToPartialUpdateRoom(Room room);
}
