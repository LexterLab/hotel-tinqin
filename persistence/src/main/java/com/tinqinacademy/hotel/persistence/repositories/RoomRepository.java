package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.mappers.RoomRowMapper;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
            try {
                Room room = jdbcTemplate.queryForObject(sql, new Object[]{id}, roomRowMapper);
                return Optional.ofNullable(room);
            } catch (EmptyResultDataAccessException e) {
                return Optional.empty();
            }
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

    @Override
    public void updateById(UUID id, Room room) {
        String sql = "UPDATE rooms SET room_no = ?, room_bathroom_type = CAST(? AS BATHROOM_TYPE), floor = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, room.getRoomNo(), room.getBathroomType().toString(), room.getFloor(),
                room.getPrice(), id);
    }

    @Override
    public void saveAll(List<Room> rooms) {}

    @Override
    public void patchById(UUID id, Room room) {
        StringBuilder sql = new StringBuilder("UPDATE rooms SET ");
        List<Object> params = new ArrayList<>();

        boolean firstField = true;

        if (room.getRoomNo() != null) {
            sql.append(firstField ? "" : ", ").append("room_no = ?");
            params.add(room.getRoomNo());
            firstField = false;
        }

        if (room.getBathroomType() != null) {
            sql.append(firstField ? "" : ", ").append("room_bathroom_type = CAST(? AS BATHROOM_TYPE)");
            params.add(room.getBathroomType().toString());
            firstField = false;
        }

        if (room.getFloor() != null) {
            sql.append(firstField ? "" : ", ").append("floor = ?");
            params.add(room.getFloor());
            firstField = false;
        }

        if (room.getPrice() != null) {
            sql.append(firstField ? "" : ", ").append("price = ?");
            params.add(room.getPrice());
            firstField = false;
        }

        sql.append(" WHERE id = ?");
        params.add(id);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    public void saveRoomBeds(List<Bed> beds, Room room) {
        for (Bed bed : beds) {
            String sql = "INSERT INTO room_beds (bed_id, room_id) " +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, bed.getId(), room.getId());
        }
    }

    public void updateRoomBeds(List<Bed> beds, Room room) {
        String deleteSql = "DELETE FROM room_beds WHERE room_id = ?";
        jdbcTemplate.update(deleteSql, room.getId());
        for (Bed bed : beds) {
            String sql = "INSERT INTO room_beds (bed_id, room_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, bed.getId(), room.getId());
        }
    }

    //refactor
    private final RowMapper<UUID> uuidRowMapper = (rs, rowNum) -> UUID.fromString(rs.getString("bed_id"));

    public List<UUID> findRoomBedIds(Room room) {
        String sql = "SELECT bed_id FROM room_beds WHERE room_id = ?";
        return jdbcTemplate.query(sql, new Object[]{room.getId()}, uuidRowMapper);
    }

    public boolean existsRoomNo(String roomNo) {
        String sql = "SELECT COUNT(*) FROM rooms WHERE room_no = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomNo}, Integer.class) > 0;
    }

}
