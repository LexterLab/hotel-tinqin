package com.tinqinacademy.hotel.persistence.repositories;


import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.persistence.mappers.BookingRowMapper;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
@Slf4j
public class BookingRepository implements AliExpressJPARepository<Booking> {
    private final JdbcTemplate jdbcTemplate;
    private final BookingRowMapper bookingRowMapper;
    @Override
    public void save(Booking booking) {
        String sql = "INSERT INTO bookings (id, start_date, end_date, room_id, user_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, booking.getId(), booking.getStartDate(), booking.getEndDate(), booking.getRoomId(),
                booking.getUserId());
    }

    @Override
    public List<Booking> findAll() {
        return List.of();
    }

    @Override
    public Optional<Booking> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void updateById(UUID id, Booking booking) {

    }

    @Override
    public void saveAll(List<Booking> bookings) {

    }

    public boolean existByDates(LocalDateTime startDate, LocalDateTime endDate, UUID roomId) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE room_id = ? AND ((start_date = ? AND end_date = ?)" +
                " OR (start_date BETWEEN ? AND ?)"
                + " OR (end_date BETWEEN ? AND ?))";

        Long count = jdbcTemplate.queryForObject(sql, Long.class, roomId, startDate, endDate, startDate, endDate,
                startDate, endDate);
        return count > 0;
    }

    public List<UUID> searchForAvailableRooms(SearchRoomInput input) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT r.id FROM rooms r ")
                .append("JOIN room_beds rb ON r.id = rb.room_id ")
                .append("JOIN beds b ON rb.bed_id = b.id ")
                .append("WHERE r.id NOT IN (")
                .append("    SELECT room_id FROM bookings ")
                .append("    WHERE (start_date < ? AND end_date > ?)")
                .append(")");

        if (input.getBathroomType() != null && !input.getBathroomType().toString().isEmpty()) {
            sql.append(" AND r.room_bathroom_type = CAST(? AS BATHROOM_TYPE) ");
        }
        if (input.getBedSize() != null && !input.getBedSize().toString().isEmpty()) {
            sql.append(" AND b.bed_size = CAST(? AS BED) ");
        }
        if (input.getBedCount() != null && !input.getBedCount().toString().isEmpty()) {
            sql.append(" AND (SELECT COUNT(*) FROM room_beds rb2 WHERE rb2.room_id = r.id) = ?");
        }

        List<Object> params = new ArrayList<>();
        params.add(input.getStartDate());
        params.add(input.getEndDate());

        if (input.getBathroomType() != null && !input.getBathroomType().toString().isEmpty()) {
            params.add(input.getBathroomType().toString());
        }
        if (input.getBedSize() != null && !input.getBedSize().toString().isEmpty()) {
            params.add(input.getBedSize().toString());
        }
        if (input.getBedCount() != null && !input.getBedCount().toString().isEmpty()) {
            params.add(input.getBedCount());
        }

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> UUID.fromString(rs.getString("id")), params.toArray());
    }

//    public List<LocalDate> getRoomDatesOccupied(Room room, LocalDate startDate, LocalDate endDate) {
//        String sql = "SELECT start_date, end_date FROM room_bookings WHERE room_id = ? " +
//                "AND (start_date BETWEEN ? AND ? OR end_date BETWEEN ? AND ? " +
//                "OR ? BETWEEN start_date AND end_date OR ? BETWEEN start_date AND end_date) " +
//                "ORDER BY start_date DESC";
//        return jdbcTemplate.query(sql, new Object[]{room.getId(), startDate, endDate, startDate, endDate, startDate, endDate},
//                roomBookingRowMappers);
//    }

}
