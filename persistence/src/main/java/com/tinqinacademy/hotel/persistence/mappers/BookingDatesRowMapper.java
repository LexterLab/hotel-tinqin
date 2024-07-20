package com.tinqinacademy.hotel.persistence.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookingDatesRowMapper implements RowMapper {
    @Override
    public List<LocalDateTime> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<LocalDateTime> dates = new ArrayList<>();
        dates.add(rs.getTimestamp("start_date").toLocalDateTime());
        dates.add(rs.getTimestamp("end_date").toLocalDateTime());
        return dates;
    }
}
