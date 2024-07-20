package com.tinqinacademy.hotel.persistence.mappers;

import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class BookingRowMapper implements RowMapper<Booking> {
    @Override
    public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
        Booking booking = Booking.builder()
                .id(UUID.fromString(rs.getString("id")))
                .endDate(rs.getTimestamp("end_date").toLocalDateTime())
                .startDate(rs.getTimestamp("start_date").toLocalDateTime())
                .roomId(UUID.fromString(rs.getString("room_id")))
                .userId(UUID.fromString(rs.getString("user_id")))
                .build();

        return booking;
    }
}
