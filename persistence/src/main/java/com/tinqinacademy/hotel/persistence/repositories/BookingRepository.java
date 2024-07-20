package com.tinqinacademy.hotel.persistence.repositories;


import com.tinqinacademy.hotel.persistence.mappers.BookingRowMapper;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
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

    public List<Booking> findAllByRoomId(UUID roomId) {
        String sql = "SELECT * FROM bookings WHERE room_id = ?";
        return jdbcTemplate.query(sql, bookingRowMapper, roomId);
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
