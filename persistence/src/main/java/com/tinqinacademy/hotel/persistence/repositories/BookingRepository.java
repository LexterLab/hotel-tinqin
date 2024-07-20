package com.tinqinacademy.hotel.persistence.repositories;


import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.persistence.mappers.BookingDatesRowMapper;
import com.tinqinacademy.hotel.persistence.mappers.BookingRowMapper;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
@Slf4j
public class BookingRepository implements AliExpressJPARepository<Booking> {
    private final JdbcTemplate jdbcTemplate;
    private final BookingRowMapper bookingRowMapper;
    private final BookingDatesRowMapper bookingDatesRowMapper;
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
        String sql = "SELECT * FROM bookings WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, bookingRowMapper, id));
    }

    @Override
    public void deleteById(UUID id) {
        jdbcTemplate.update("DELETE FROM bookings WHERE id = ?", id);
    }

    @Override
    public void updateById(UUID id, Booking booking) {

    }

    @Override
    public void saveAll(List<Booking> bookings) {

    }

    @Override
    public void patchById(UUID id, Booking booking) {

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

    public Set<LocalDateTime> findAllDatesOccupiedByRoomId(UUID roomId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT start_date, end_date FROM bookings b ");
        sql.append("WHERE b.room_id = ? ");
        sql.append("AND b.start_date >= CURRENT_DATE ");
        sql.append("ORDER BY start_date DESC ");

        List<List<LocalDateTime>> result = jdbcTemplate.query(sql.toString(), new Object[]{roomId}, bookingDatesRowMapper);

        return result.stream().flatMap(List::stream).collect(Collectors.toSet());
    }

    public Optional<Booking> findLatestByRoomId(UUID roomId, UUID userId) {
        String sql = "SELECT b.id, b.start_date, b.end_date, b.room_id, b.user_id " +
                "FROM bookings b " +
                "WHERE b.room_id = ? " +
                "AND b.user_id = ? " +
                "ORDER BY b.start_date DESC " +
                "LIMIT 1";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{roomId, userId}, bookingRowMapper));
    }

}
