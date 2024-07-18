package com.tinqinacademy.hotel.persistence.mappers;

import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class RoomRowMapper implements RowMapper<Room> {
    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        Room room = Room.builder()
                .id(UUID.fromString(rs.getString("id")))
                .roomNo(rs.getString("roomNo"))
                .bathroomType(BathroomType.getByCode(rs.getString("room_bathroom_type")))
                .floor(rs.getInt("floor"))
                .price(rs.getBigDecimal("price"))
                .build();

        return room;
    }
}
