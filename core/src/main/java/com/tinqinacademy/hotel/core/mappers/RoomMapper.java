package com.tinqinacademy.hotel.core.mappers;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    Room createRoomInputToRoom(CreateRoomInput input);

    @Mapping(target = "id", source = "roomId")
    Room updateRoomInputToRoom(UpdateRoomInput input);
}
