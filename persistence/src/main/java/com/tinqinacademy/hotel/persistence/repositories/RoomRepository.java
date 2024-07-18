package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(Room room) {
        String sql = "INSERT INTO rooms (id, room_no, room_bathroom_type, floor, price) " +
                "VALUES (?, ?, CAST(? AS BATHROOM_TYPE), ?, ?)";
        jdbcTemplate.update(sql, room.getId(), room.getRoomNo(), room.getBathroomType().toString(), room.getFloor(),
                room.getPrice());
    }

    public void addBedToRoom(Bed bed, Room room) {
        String sql = "INSERT INTO room_beds (bed_id, room_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, bed.getId(), room.getId());
    }
}
