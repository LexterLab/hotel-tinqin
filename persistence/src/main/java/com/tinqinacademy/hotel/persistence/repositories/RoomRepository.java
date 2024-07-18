package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.mappers.RoomRowMapper;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoomRepository implements AliExpressJPARepository<Room>{
    private final JdbcTemplate jdbcTemplate;
    private final RoomRowMapper roomRowMapper;

    public void save(Room room) {
        String sql = "INSERT INTO rooms (id, room_no, room_bathroom_type, floor, price) " +
                "VALUES (?, ?, CAST(? AS BATHROOM_TYPE), ?, ?)";
        jdbcTemplate.update(sql, room.getId(), room.getRoomNo(), room.getBathroomType().toString(), room.getFloor(),
                room.getPrice());
    }

    @Override
    public List<Room> findAll() {
        return List.of();
    }

    @Override
    public Optional<Room> findById(UUID id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
//        List<Room> rooms = jdbcTemplate.query(sql, roomRowMapper, id);
//
//        if (!rooms.isEmpty()) {
//            return Optional.of(rooms.getFirst());
//        }
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, roomRowMapper, id));
    }

    @Override
    public void deleteById(UUID id) {
        String deleteBookingRoomSql = "DELETE FROM bookings WHERE room_id = ?";
        String deleteRoomBedsSql = "DELETE FROM room_beds WHERE room_id = ?";
        String deleteRoomSql = "DELETE FROM rooms WHERE id = ?";

        jdbcTemplate.update(deleteBookingRoomSql, id);
        jdbcTemplate.update(deleteRoomBedsSql, id);
        jdbcTemplate.update(deleteRoomSql, id);
    }


    public void saveRoomBeds(List<Bed> beds, Room room) {
        for (Bed bed : beds) {
            String sql = "INSERT INTO room_beds (bed_id, room_id) " +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, bed.getId(), room.getId());
        }
    }

}
